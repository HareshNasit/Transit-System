package user;

import transitNetwork.Trip;
import java.util.ArrayList;
import transitNetwork.Stop;

public class Card {
  private double balance;
  private double charged;
  private boolean suspended;
  
  private Stop firstTap;
  private ArrayList<Trip> trips;
  private Trip currentTrip;
  
  //TODO: Track last known route user was on
  
  public Card() {
    balance = 19;
    suspended = false;
    trips = new ArrayList<Trip>();
  }
  
  protected void suspend() {
    suspended = true;
  }
  
  public boolean isSuspended() {
    return suspended;
  }
  
  /* Returns true if the add balance action was successful and false otherwise.*/
  protected boolean addBalance(int amount) {
    if (amount == 10 || amount == 20 || amount == 50) {
      balance += amount;
      return true;
    }
    return false;
  }
  
  public double getBalance() {
    return balance;
  }
  
  public void charge(double amt) {
    if (balance - charged > 0) charged += amt;
    if (charged > 6) charged = 6;
  }

  
  private void addTrip(Trip trip) {
    trips.add(trip);
  }
  

  // TODO: tap functionality
  // Return true if successful, false otherwise.
  public boolean tapOn(Stop stop) {
    return stop.tapOn(this);
  }
  
  public boolean tapOff(Stop stop) {
    return stop.tapOff(this);
  }
}
