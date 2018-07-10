package transitNetwork;

import user.Card;
import java.util.Queue;
import main.Logger;
import java.util.ArrayList;
import java.util.LinkedList;

public class Station extends Stop{

    private BusStop connectedStop = null;
    private ArrayList<Station> connectingStations;
    private static int stationCount;

    public Station(String id, String name, int stationCount){
        super(id, name);
        this.connectingStations = new ArrayList<>();
        this.stationCount = stationCount;
    }

    /* Create a station with a pre-connected bus stop */
    public Station(String id, String name, BusStop connectingBusStop){
        super(id, name);
        this.connectingStations = new ArrayList<>();
        this.connectedStop = connectingBusStop;
        connectedStop.connectStation(this);
    }
    
    /* Connect a single station to this one */
    public void connectStation(Station station) {
      connectingStations.add(station);
      if (!station.getConnectedStations().contains(this)) {
        station.connectStation(this);
      }
    }
    
    /* Connect an arrayList of stations to this station */
    public void connectStations(ArrayList<Station> stations) {
      connectingStations.addAll(stations);
      for (Station station : stations) {
        if (!station.getConnectedStations().contains(this)) {
          station.connectStation(this);
        }
      }
    }
    
    public void connectBusStop(BusStop stop) {
      connectedStop = stop;
      connectedStop.connectStation(this);
    }

    public BusStop getConnectedBusStop() {
        return connectedStop;
    }
    
    public ArrayList<Station> getConnectedStations() {
      return connectingStations;
    }

    public boolean tapOn(Route route, Card card, int timestamp) {
      if (card.getBalance() > 0) {
          Stop lastStop = card.getCurrentTrip().getLastStop();
          boolean isNew = card.getCurrentTrip() == null || card.getCurrentTrip().isEnded(); //If the user starts a new trip or no.
          // If the trip is not continuous, start a new trip.
          boolean isNotContinuousTrip = lastStop != this && lastStop != this.connectedStop;
          // If the the continuous trip is over 2 hrs, start a new trip.
          boolean isNotWithinTwoHrs = timestamp - card.getCurrentTrip().getInitialTime() > 120;
          if (( isNew || isNotContinuousTrip || isNotWithinTwoHrs )) {
            card.newTrip(this, timestamp);
          }
          card.getCurrentTrip().addStop(this);
          Logger.log(card.toString() + " tapped on to subway station " + getName() + " at timestamp " + timestamp);
          return true;
      }
      Logger.log(card.toString() + " failed to tap on to subway station " + getName() + " at timestamp " + timestamp);
      return false;
    }
    
    public boolean tapOff(Route route, Card card, int timestamp) {
        // If the user had entered illegally and jst taps off without tapping on, the user is charged
        // max $6.
        if(card.getCurrentTrip() == null || card.getCurrentTrip().isEnded()){
            card.chargeFine(6);
            Logger.log(card.toString() + " charged for illegal exit of subway station " + getName() + " at timestamp " + timestamp);
        }
        else {
            Station tripStart = card.getCurrentTrip().getLastSubwayTap();
            card.getCurrentTrip().addStop(this);
            int distance = getDistance(tripStart);
            if (distance > 0) {
              card.charge(distance * 0.5);
              Logger.log(card.toString() + " tapped off of subway station " + getName() + " at timestamp " + timestamp);
            }
            else {
              card.chargeFine(6);
              card.getCurrentTrip().endTrip();
              Logger.log(card.toString() + " charged for illegal exit of subway station " + getName() + "at timestamp " + timestamp);
            }
        }
      return true;
    }

    /* Breadth-first search
     * null values in the queue separate depth values
     * max depth is the number of existing stations
     * ArrayList prevents re-visiting nodes */
    private int getDistance(Stop lastStop){
        ArrayList<Station> visitedNodes = new ArrayList<Station>();
        Queue<Station> queue = new LinkedList<Station>();
        Station currentNode = null;
        int depth = 0;
        queue.add(this);
        queue.add(null);
        
        while (currentNode != lastStop && depth < stationCount) {
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
        if (currentNode != lastStop) {
          //Illegal exit!
          return -1;
        }
        return depth;
    }
}
