package user;

import transitNetwork.Route;
import transitNetwork.Trip;
import java.util.ArrayList;
import main.Logger;
import transitNetwork.Stop;

public class Card {
  private double balance;
  private boolean suspended;
  private double totalSpending;
  private double totalFines;
  
  private String id;
  private User user;

  private Trip[] trips;
  
  //TODO: Track last known route user was on
  private Route lastRoute;

  public Card(String id, User user) {
    balance = 19;
    suspended = false;
    trips = new Trip[3];
    this.user = user;
    this.id = id;
  }

    public Trip[] getTrips() {
        return trips;
    }

    protected void suspend() {
    suspended = true;
  }
  
  public boolean isSuspended() {
    return suspended;
  }
  
  protected void addBalance(int amount) {
      balance += amount;
  }
  
  /* Returns the available balance on the card. */
  public double getBalance() {
    if (trips[0] != null) return balance - trips[0].getValue();
    else return balance;
  }
  
  /* Ensures that when a card is charged, the value is added to the current trip's total. */
  public boolean charge(double amount) {
    if (!suspended && getBalance() > 0) {
      trips[0].charge(amount);
      Logger.log(toString() + " charged $" + amount + ".");
      return true;
    }
    return false;
  }
  
  public void chargeFine(double amount) {
    balance -= amount;
    totalFines += amount;
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
      Logger.log(toString() + " started a new trip!");
    }
  }
  
  public Trip getCurrentTrip() {
    return trips[0];
  }

  // TODO: tap functionality
  // Return true if successful, false otherwise.
  public boolean tapOn(Route route, Stop stop, int timestamp) {
    return stop.tapOn(route, this, timestamp);
  }
  
  public boolean tapOff(Route route, Stop stop, int timestamp) {
    return stop.tapOff(route, this, timestamp);
  }

  protected double getTotalSpending(){
      return totalSpending;
  }
  
  protected double getTotalFines() {
      return totalFines;
  }

  public String getId() {
    return id;
  }
  public String toString() {
    return "Card " + id + " owned by user " + user.getName();
  }
}
