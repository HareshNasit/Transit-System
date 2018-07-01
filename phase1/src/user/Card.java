package user;

import java.util.ArrayList;
import transitNetwork.Stop;

public class Card {
  private double balance;
  private double charged;
  private boolean suspended;
  
  private Stop firstTap;
  private ArrayList<Stop>[] lastThreeTrips;
  private ArrayList<Stop> currentTrip;
  
  //TODO: Track last known route user was on
  
  public Card() {
    balance = 19;
    suspended = false;
    currentTrip = new ArrayList<Stop>();
    lastThreeTrips = new ArrayList[3];
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
  
  public void endTrip() {
    balance -= charged;
    addTrip(currentTrip);
    currentTrip.clear();
  }
  
  private void addTrip(ArrayList<Stop> trip) {
    lastThreeTrips[2] = lastThreeTrips[1];
    lastThreeTrips[1] = lastThreeTrips[0];
    lastThreeTrips[0] = trip;
  }
  
  public ArrayList<Stop> getTrip(int trip){
    if (trip <= 2 && trip >= 0) return lastThreeTrips[trip];
    return null;
  }
  
  // TODO: tap functionality
  // Return true if successful, false otherwise.
  public boolean tapOn(Stop stop) {
    currentTrip.add(stop);
    return stop.tapOn(this);
  }
  
  public boolean tapOff(Stop stop) {
    currentTrip.add(stop);
    return stop.tapOff(this);
  }
}
