package hotcupsofjava.transitsystemmanager.objects.transitobjects;

import hotcupsofjava.transitsystemmanager.Logger;
import hotcupsofjava.transitsystemmanager.objects.userobjects.Card;
import hotcupsofjava.transitsystemmanager.objects.userobjects.Trip;
import hotcupsofjava.transitsystemmanager.objects.userobjects.TripLocation;

class Tap {

    boolean tapOnHandler(Card card, long timestamp, Stop stop) {
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
    boolean tapOffHandler(Stop stop,Card card) {
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
        } else if (chargeBus) {
            card.charge(2);
        }

        return chargeBus || chargeStation;
    }

}
