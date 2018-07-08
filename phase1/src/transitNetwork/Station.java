package transitNetwork;

import user.Card;

import java.util.ArrayList;

public class Station extends Stop{

    private BusStop connectedBusStop = null;
    private ArrayList<Station> connectingStations;

    /* Create a station with a pre-connected bus stop */
    public Station(String name, BusStop connectingBusStop){
        super(name);
        this.connectedBusStop = connectingBusStop;
        connectedBusStop.connectStation(this);
    }
    
    public Station(String name){
      super(name);
    }
    
    /* Connect a single station to this one */
    public void connectStation(Station station) {
      connectingStations.add(station);
      if (!station.getConnectedStations().contains(this)) {
        station.connectStation(this);
      }
    }
    
    /* Connect an arraylist of stations to this station */
    public void connectStation(ArrayList<Station> stations) {
      connectingStations.addAll(stations);
      for (Station station : stations) {
        if (!station.getConnectedStations().contains(this)) {
          station.connectStation(this);
        }
      }
    }
    
    public void connectBusStop(BusStop stop) {
      connectedBusStop = stop;
      connectedBusStop.connectStation(this);
    }

    public BusStop getConnectBusStop() {
        return connectedBusStop;
    }
    
    public ArrayList<Station> getConnectedStations() {
      return connectingStations;
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
