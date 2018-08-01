package hotcupsofjava.transitsystemmanager.objects.transitobjects;

import hotcupsofjava.transitsystemmanager.Logger;
import hotcupsofjava.transitsystemmanager.objects.userobjects.Card;
import hotcupsofjava.transitsystemmanager.objects.userobjects.Trip;


public class TapBus extends Tap{
    private static TapBus ourInstance = new TapBus();

    public static TapBus getInstance() {
        return ourInstance;
    }

    private TapBus() {
    }

    public void tapOn(long timestamp, Stop busStop, Card card, Route route){
        boolean continousTrip = tapOnHandler(card,timestamp, busStop);
        if (card.getBalance() > 0 && !card.isSuspended()) {
            if (!continousTrip) card.newTrip(new Trip(timestamp, (BusStop) busStop, route));
            else card.getCurrentTrip().addLocation(timestamp, true, (BusStop) busStop, route, false);
            Logger.log(String.format("%s tapped on at bus stop %s on route %s at %d",
                    toString(), busStop.getName(), route.getId(), timestamp));
            card.charge(2);
        } else {
            Logger.log(String.format("%s failed to tap on at bus stop %s on route %s at %d",
                    card.toString(), busStop.getName(), route.getId(), timestamp));
        }
    }

    public void tapOff(long timestamp, Stop busStop, Card card, Route route){
        boolean abnormalTap = tapOffHandler(busStop,card);
        Logger.log(String.format("%s tapped off at bus stop %s on route %s at %d",
                card.toString(), busStop.getName(), route.getId(), timestamp));
        card.getCurrentTrip().addLocation(timestamp, false, (BusStop) busStop, route, abnormalTap);
    }
}
