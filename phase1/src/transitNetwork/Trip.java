package transitNetwork;

import java.util.ArrayList;

/**
 * @author
 * @version 1.0
 */
public class Trip {
    private ArrayList<Stop> stops;
    private Station lastSubwayTap = null;

  private boolean tripEnded = false;
    
    private long initialTime;
    
    private double tripValue = 0;
  /**
   * Constructor for the Trip of the card. The main function for this class is to track the
   * trips made by the card user. This is to ensure that the correct fare is charged and that
   * any illegal trips made by the user is dealt by the maximum charge as a penalty.
   *
   * @param initialStop the initial stop that the user tapped onto using their card
   * @param timestamp   the time at which the initial stop was tapped onto by the user
   */
    public Trip(Stop initialStop, int timestamp) {
      stops = new ArrayList<Stop>();
      stops.add(initialStop);
      initialTime = timestamp;
    }

  /**
   * Tracks the total fare for this Trip. Once it reaches $6.00, the trip no longer charges
   * additionally.
   *
   * @param amount  The amount charge added onto this Trip.
   */
  public void charge(double amount) {
      tripValue += amount;
      if (tripValue > 6) {
        tripValue = 6;
      }
    }

  /**
   * Closes this Trip so that the card user must start a new Trip.
   */
  public void endTrip() {
      tripEnded = true;
    }

  /**
   * Returns true if the Trip has ended, otherwise false.
   *
   * @return If trip is ended, otherwise false.
   */
  public boolean isEnded() {
      return tripEnded;
    }

  /**
   * Returns long of the time of the initial tap on of the first stop.
   *
   * @return initial time of the first tap on of the first stop of this Trip
   */
  public long getInitialTime() {
        return initialTime;
    }

  /**
   * Returns the initial Stop that the card user first tapped onto in this Trip.
   *
   * @return the first Stop that was tapped onto this Trip
   */
  public Stop getInitialStop(){
        return stops.get(0);
    }

  /**
   * Returns the amount in double that this Trip is being charged for.
   *
   * @return the charge value of this Trip
   */
    public double getValue() {
      return tripValue;
    }

  /**
   * Returns the last Stop of this Trip that the card user last tapped off in this Trip.
   *
   * @return the last Stop tapped off in this Trip
   */
  public Stop getLastStop() {
      return stops.get(stops.size());
    }

  /**
   * Returns the last Station that the card user tapped off in this Trip.
   * This function will ignore the last BusStop tap off.
   *
   * @return the last Station tapped off by the card user in this Trip.
   */
  public Station getLastSubwayTap() {
      return lastSubwayTap;
    }

  /**
   * Adds a Stop to track on this Trip.
   * @param stop To add on this Trip to track the card users tap on/off location
   */
  public void addStop(Stop stop) {
      stops.add(stop);
      if (stop instanceof Station) lastSubwayTap = (Station) stop;
    }
}
