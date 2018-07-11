package transitNetwork;

import user.Card;
import java.util.Queue;
import main.Logger;
import java.util.ArrayList;
import java.util.LinkedList;
/**
 * @author
 * @version 1.0
 */
public class Station extends Stop{

    private ArrayList<Station> connectingStations;
    private static int stationCount;
  /**
   * The constructor of the Station that initializes the unique id and the name of the Station.
   * The id must be unique to all Stops, including BusStops and Stations, but the name may be
   * shared.
   *
   * @param id            the identification of the stop that is unique that cannot be changed
   * @param name          the name of the station
   * @param stationCount  the total number of stations in the transit network
   * @see                 Stop
   */
    public Station(String id, String name, int stationCount){
        super(id, name);
        this.connectingStations = new ArrayList<>();
        this.stationCount = stationCount;
    }
  /**
   * The constructor of the Station that initializes the unique id and the name of the Station.
   * The id must be unique to all Stops, including BusStops and Stations, but the name may be
   * shared. Takes in a BusStop that connects to this Station.
   *
   * @param id                  the identification of the stop that is unique that cannot be changed
   * @param name                the name of the station
   * @param connectingBusStop   the BusStop that this Station is connected to
   * @see                       Stop
   */
    public Station(String id, String name, BusStop connectingBusStop){
        super(id, name);
        this.connectingStations = new ArrayList<>();
        this.connectStop(connectingBusStop);
        connectingBusStop.connectStation(this);
    }
  /**
   * Connects an immediate station to this Station.
   *
   *
   *  @param station  connects a station to this Station
   */
    public void connectStation(Station station) {
      connectingStations.add(station);
      if (!station.getConnectedStations().contains(this)) {
        station.connectStation(this);
      }
    }
  /**
   * Connects a list of stations to this station.
   *
   * @param stations an arraylist of stations to connect this one to
   *
   */
    /* Connect an arrayList of stations to this station */
    public void connectStations(ArrayList<Station> stations) {
      connectingStations.addAll(stations);
      for (Station station : stations) {
        if (!station.getConnectedStations().contains(this)) {
          station.connectStation(this);
        }
      }
    }
  /**
   * Connects a BusStop to this Station so that they can be used for jointed trips, throws exception
   * otherwise.
   *
   * @param stop    a stop that connects to this Station
   */
    @Override
    void connectStop(Stop stop) {
        if (!(stop instanceof BusStop)) throw new RuntimeException("Station connected with Station when supposed to be BusStop");
        BusStop busStop = (BusStop) stop;
        super.connectStop(busStop);
        busStop.connectStop(this);
    }
  /**
   * Connects a BusStop to this Station so that they can be used for jointed trips.
   *
   * @param busStop    a BusStop that connects to this Station
   */
    void connectBusStop(BusStop busStop) {
        connectStop(busStop);
    }
  /**
   * Returns a BusStop that this Station is connected to.
   * @return  a BusStop
   *
   */
    BusStop getConnectedBusStop() {
        return (BusStop) this.getConnectedStop();
    }
  /**
   * Returns a list of Stations that are connected to this station.
   * @return a list of Stations
   */
    public ArrayList<Station> getConnectedStations() {
      return connectingStations;
    }
  /**
   * Returns true if the tap on function of the Station was successful by the card.
   * Starts the trip if trip was not already active, otherwise continues trip.
   *
   * If card balance is less than or equal to $0.00, then the tap on function returns false and
   * reports that the tap on has failed.
   *
   * @param route     the route that the user is taking at this Station location
   * @param card      the card that is being used to tap onto the Station
   * @param timestamp the time recorded  at which the card taps onto the Station
   * @return          true if successful, false otherwise
   */
    public boolean tapOn(Route route, Card card, int timestamp) {
      if (card.getBalance() > 0) {
          Stop lastStop = card.getCurrentTrip().getLastStop();
          boolean isNew = card.getCurrentTrip() == null || card.getCurrentTrip().isEnded(); //If the user starts a new trip or no.
          // If the trip is not continuous, start a new trip.
          boolean isNotContinuousTrip = lastStop != this && lastStop != this.getConnectedStop();
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
  /**
   * Returns true if the tap off function of the Station was successful by the card.
   * The tap off function will charge the card if it was a legal trip by the shortest distance
   * between the initial Station at tap on and this Station. If the card was illegally entered and
   * taps off, then the system will charge the maximum $6.00.
   *
   * @param route     the route that the user was taking before tapping off the Station
   * @param card      the card that is being used to tap off the Station
   * @param timestamp the time recorded at which the card taps off the Station
   * @return          true if successful tap off or if user illegally exited the transit without tapping
   */
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
  /**
   * Returns an integer that is the shortest distance between the initial Station and the last
   * Station at tap off.
   *
   * @param lastStop the Station that was initially tapped onto
   * @return         an integer of the distance between initial Station and this Station
   *
   */
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
