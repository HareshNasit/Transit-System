package transitNetwork;

import user.Card;

public class Station extends Stop{

    private BusStop connectBusStop;

    public Station(String name, BusStop connectingBusStop){
        super(name);
        this.connectBusStop = connectingBusStop;
    }

    public BusStop getConnectBusStop() {
        return connectBusStop;
    }

    public boolean tapOn(Route route, Card card) {
        //TODO: Add detection for disjointed trips
        if (card.getBalance() > 0) card.getCurrentTrip().addStop(this);
        return true;
    }
    
    public boolean tapOff(Route route, Card card) {
      //TODO: Use route.getDistance() to calculate value of trip
      //card.charge(route.getDistance() * 0.50)
        card.getCurrentTrip().addStop(this);
        int distance = route.getDistance(card.getCurrentTrip().getInitialStop(),this);
        card.charge(distance*0.5);
      return true;
    }
}
