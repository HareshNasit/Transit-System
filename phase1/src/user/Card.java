package user;

import transitNetwork.Stop;

public class Card {
  private double balance;
  private double charged;
  private boolean suspended;
  //TODO: trip tracking
  private Object currentTrip;
  
  public Card() {
    balance = 19;
    suspended = false;
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
    //currentTrip.empty();
  }
  
  // TODO: tap functionality
  // Return true if successful, false otherwise.
  public boolean tapOn(Stop stop) {
    //currentTrip.add(stop);
    return stop.tapOn(this);
  }
  
  public boolean tapOff(Stop stop) {
    //currentTrip.add(stop);
    return stop.tapOff(this);
  }
}
