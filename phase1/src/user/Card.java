package user;

import transitNetwork.Trip;
import java.util.ArrayList;
import transitNetwork.Stop;

public class Card {
  private double balance;
  private boolean suspended;
  private double totalSpending;
  
  private Trip[] trips;
  
  //TODO: Track last known route user was on
  
  public Card() {
    balance = 19;
    suspended = false;
    trips = new Trip[3];
  }
  
  protected void suspend() {
    suspended = true;
  }
  
  public boolean isSuspended() {
    return suspended;
  }
  
  /* Returns true if the add balance action was successful and false otherwise.*/
  protected boolean addBalance(int amount) {
    if ((amount == 10 || amount == 20 || amount == 50) && !suspended) {
      balance += amount;
      return true;
    }
    return false;
  }
  
  /* Returns the available balance on the card. */
  public double getBalance() {
    return balance - trips[0].getValue();
  }
  
  /* Ensures that when a card is charged, the value is added to the current trip's total. */
  public void charge(double amount) {
    if (!suspended) trips[0].charge(amount);
  }
  
  public void newTrip(Stop initialStop, int timestamp) {
    if (!suspended) {
      if (trips[0] != null) {
          balance -= trips[0].getValue();
          totalSpending += trips[0].getValue();
      }
      trips[2] = trips[1];
      trips[1] = trips[0];
      trips[0] = new Trip(initialStop, timestamp);
    }
  }
  
  public Trip getCurrentTrip() {
    return trips[0];
  }

  // TODO: tap functionality
  // Return true if successful, false otherwise.
  public boolean tapOn(Stop stop) {
    return stop.tapOn(this);
  }
  
  public boolean tapOff(Stop stop) {
    return stop.tapOff(this);
  }

  protected double getTotalSpending(){
      return totalSpending;
  }
}
