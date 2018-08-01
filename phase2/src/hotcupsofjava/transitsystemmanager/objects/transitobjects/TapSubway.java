package hotcupsofjava.transitsystemmanager.objects.transitobjects;

import hotcupsofjava.transitsystemmanager.Logger;
import hotcupsofjava.transitsystemmanager.objects.userobjects.Card;
import hotcupsofjava.transitsystemmanager.objects.userobjects.Trip;

public class TapSubway extends Tap{
    private static TapSubway ourInstance = new TapSubway();

    public static TapSubway getInstance() {
        return ourInstance;
    }

    private TapSubway() {
    }

    public void tapOn(long timestamp, Stop station, Card card) {
        boolean continuousTrip = tapOnHandler(card,timestamp, station);
        if (card.getBalance() > 0 && !card.isSuspended()) {
            if (!continuousTrip) card.newTrip(new Trip(timestamp, (Station) station));
            else card.getCurrentTrip().addLocation(timestamp, true,(Station) station, false);
            Logger.log(String.format("%s tapped on at subway station %s at %d",
                    card.toString(), station.getName(), timestamp));
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
            card.charge(.5 * ((Station)station).getDistance(card.getCurrentTrip().getLastSubwayTap()));
            card.getCurrentTrip().addLocation(timestamp, false, (Station) station, chargedFine);
            if (timestamp - card.getCurrentTrip().getInitialTime() > 120) {
                card.getCurrentTrip().endTrip();
            }
            Logger.log(String.format("%s tapped off at subway station %s at %d",
                    card.toString(), station.getName(), timestamp));
        }
    }
}
