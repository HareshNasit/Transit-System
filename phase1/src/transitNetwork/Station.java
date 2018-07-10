package transitNetwork;

import user.Card;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;

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
    
    /* Connect an arrayList of stations to this station */
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

    public boolean tapOn(Route route, Card card, int timestamp) {
        //TODO: Add detection for disjointed trips
        if(card.getCurrentTrip() == null) {
            if (card.getBalance() > 0) {
                card.newTrip(this, timestamp);
                card.getCurrentTrip().addStop(this);
                return true;
            }
        }
        else if(card.getCurrentTrip().getValue() < 6){
            if(card.getBalance()>0) {
                card.getCurrentTrip().addStop(this);
                return true;
            }
        }
        return false;
    }
    
    public boolean tapOff(Route route, Card card, int timestamp) {
        Station tripStart = card.getCurrentTrip().getLastSubwayTap();
        card.getCurrentTrip().addStop(this);
        int distance = getDistance(tripStart);
        card.charge(distance*0.5);
      return true;
    }

    /* Breadth-first search
     * null values in the queue separate depth values
     * max out at depth of 12 ($6 charge) 
     * ArrayList prevents re-visiting nodes */
    private int getDistance(Stop lastStop){
        ArrayList<Station> visitedNodes = new ArrayList<Station>();
        Queue<Station> queue = new LinkedList<Station>();
        Station currentNode = null;
        int depth = 0;
        queue.add(this);
        queue.add(null);
        
        while (currentNode != lastStop && depth < 12) {
          currentNode = queue.remove();
          if (currentNode == null) {
            depth++;
            queue.add(null);
          }
          else if (!visitedNodes.contains(currentNode)) {
            visitedNodes.add(currentNode);
            queue.addAll(currentNode.getConnectedStations());
          }
        }
        return depth;
    }
}
