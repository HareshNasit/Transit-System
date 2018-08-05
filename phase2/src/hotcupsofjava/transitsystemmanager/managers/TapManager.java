package hotcupsofjava.transitsystemmanager.managers;

import hotcupsofjava.transitsystemmanager.Logger;
import hotcupsofjava.transitsystemmanager.objects.transitobjects.BusStop;
import hotcupsofjava.transitsystemmanager.objects.transitobjects.Route;
import hotcupsofjava.transitsystemmanager.objects.transitobjects.Station;
import hotcupsofjava.transitsystemmanager.objects.transitobjects.Stop;
import hotcupsofjava.transitsystemmanager.objects.userobjects.Card;
import hotcupsofjava.transitsystemmanager.objects.userobjects.Trip;
import hotcupsofjava.transitsystemmanager.objects.userobjects.TripLocation;

import java.io.Serializable;

public class TapManager implements Serializable{
    private static TapManager instance;

    public static TapManager getInstance() {
        return instance;
    }

    public static void setInstance(TapManager i) {
        instance = i;
    }

    private double busCost;
    private double subwayCost;

    public TapManager(double busCost, double subwayCost) {
        this.busCost = busCost;
        this.subwayCost = subwayCost;
        TapManager.setInstance(this);
    }

    public void updateBusCost(int cost) {
        this.busCost = cost;
    }

    public void updateSubwayCost(int cost) {
        this.subwayCost = cost;
    }

    public double getBusCost() {
        return busCost;
    }

    public double getSubwayCost() {
        return subwayCost;
    }

    private boolean tapOnHandler(Card card, long timestamp, Stop stop) {
        Trip trip = card.getCurrentTrip();
        boolean disconnectedTrip = false;
        if (trip != null) {
            // if the last location was a tap on and a station, charge fine
            // of $6 and end the previous trip as it is now invalid.
            TripLocation lastLocation = trip.getLastLocation();
            if (lastLocation.isTapOn() && lastLocation.isStation()) {
                Logger.log("Illegally tried to tap on at with out tapping off");
                card.chargeFine(6);
                trip.endTrip();
            }
            disconnectedTrip = trip.getLastStop() != stop && trip.getLastStop().getConnectedStop() != stop;
        }
        boolean activeTrip = trip != null && !(trip.isEnded() || timestamp - trip.getInitialTime() > 120);
        return activeTrip && !disconnectedTrip;
    }

    /**
     * General things that need to be done for tap off.
     *
     * @param stop transit system stop
     * @return true if there was abnormal tapping, false otherwise
     */
    private boolean tapOffHandler(Stop stop,Card card) {
        Trip trip = card.getCurrentTrip();
        boolean isStation = stop instanceof Station;
        boolean chargeStation = false;
        boolean chargeBus = false;
        if (trip == null || trip.isEnded()) {
            if (isStation) chargeStation = true;
            else chargeBus = true;
        } else if (trip.getLastStop() instanceof BusStop &&
                !trip.getLastRoute().containsBoth((BusStop) trip.getLastStop(), (BusStop) stop)) {
            chargeBus = true;
        }
        if (chargeStation) {
            card.chargeFine(6);
            stop.addFine(6);
        } else if (chargeBus) {
            stop.addFine(2);
            card.chargeFine(2);
        }

        return chargeBus || chargeStation;
    }

    // Bus tapping
    public void tapOn(long timestamp, Stop busStop, Card card, Route route){
        boolean continuousTrip = tapOnHandler(card,timestamp, busStop);
        if (card.getBalance() > 0 && !card.isSuspended()) {
            if (!continuousTrip) card.newTrip(new Trip(timestamp, (BusStop) busStop, route));
            else card.getCurrentTrip().addLocation(timestamp, true, (BusStop) busStop, route, false);
            Logger.log(String.format("%s tapped on at bus stop %s on route %s at %d",
                    card.toString(), busStop.getName(), route.getId(), timestamp));
            card.charge(busCost);
            busStop.addTap();
            busStop.addRevenue(busCost);
        } else {
            Logger.log(String.format("%s failed to tap on at bus stop %s on route %s at %d",
                    card.toString(), busStop.getName(), route.getId(), timestamp));
        }
    }

    public void tapOff(long timestamp, Stop busStop, Card card, Route route){
        boolean abnormalTap = tapOffHandler(busStop,card);
        if (abnormalTap) {
            Logger.log("Illegally tried to tap off at" + busStop.getName() + "on route" + route.getId());
        }
        busStop.addTap();
        Logger.log(String.format("%s tapped off at bus stop %s on route %s at %d",
                card.toString(), busStop.getName(), route.getId(), timestamp));
        card.getCurrentTrip().addLocation(timestamp, false, (BusStop) busStop, route, abnormalTap);
    }

    // Subway tapping
    public void tapOn(long timestamp, Stop station, Card card) {
        boolean continuousTrip = tapOnHandler(card,timestamp, station);
        if (card.getBalance() > 0 && !card.isSuspended()) {
            if (!continuousTrip) card.newTrip(new Trip(timestamp, (Station) station));
            else card.getCurrentTrip().addLocation(timestamp, true,(Station) station, false);
            Logger.log(String.format("%s tapped on at subway station %s at %d",
                    card.toString(), station.getName(), timestamp));
            station.addTap();
        } else {
            Logger.log(String.format("%s failed to tap on at subway station %s at %d",
                    card.toString(), station.getName(), timestamp));
        }
    }

    public void tapOff(long timestamp, Stop station, Card card) {
        boolean chargedFine = tapOffHandler(station,card);
        if (chargedFine) {
            if (card.getCurrentTrip() != null) {
                card.getCurrentTrip().addLocation(timestamp, false,(Station) station, chargedFine);
                card.getCurrentTrip().endTrip();
            }
            Logger.log(String.format("%s tapped off illegally at subway station %s at %d",
                    card.toString(), station.getName(), timestamp));
        } else {
            double cost = subwayCost * ((Station)station).getDistance(card.getCurrentTrip().getLastSubwayTap());
            card.charge(cost);
            station.addRevenue(cost);
            card.getCurrentTrip().addLocation(timestamp, false, (Station) station, chargedFine);
            if (timestamp - card.getCurrentTrip().getInitialTime() > 120) {
                card.getCurrentTrip().endTrip();
            }
            station.addTap();
            Logger.log(String.format("%s tapped off at subway station %s at %d",
                    card.toString(), station.getName(), timestamp));
        }
    }
}
