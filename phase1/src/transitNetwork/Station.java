package transitNetwork;

import user.Card;

import java.util.ArrayList;

public class Station extends Stop{

    private BusStop connectBusStop;
    private ArrayList<Stop> connectingStations;

    public Station(String name, BusStop connectingBusStop, ArrayList<Stop> connectingStations){
        super(name);
        this.connectBusStop = connectingBusStop;
        this.connectingStations = connectingStations;
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
      //TODO: Use initialStation.getDistance()*0.5 to calculate value of trip
        card.getCurrentTrip().addStop(this);
        int distance = route.getDistance(card.getCurrentTrip().getInitialStop(),this);
        card.charge(distance*0.5);
      return true;
    }

    int getDistance(Stop finalStop){
        //TODO: Add a recursive method to get the distance between initial and final Stations.
        return 0;
    }
}
