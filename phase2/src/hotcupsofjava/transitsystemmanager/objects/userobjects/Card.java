package hotcupsofjava.transitsystemmanager.objects.userobjects;

import hotcupsofjava.transitsystemmanager.managers.RouteManager;
import hotcupsofjava.transitsystemmanager.managers.UserManager;
import hotcupsofjava.transitsystemmanager.objects.TransitSystemObject;
import hotcupsofjava.transitsystemmanager.objects.transitobjects.Stop;
import hotcupsofjava.transitsystemmanager.objects.transitobjects.BusStop;
import hotcupsofjava.transitsystemmanager.objects.transitobjects.Station;
import hotcupsofjava.transitsystemmanager.objects.transitobjects.Route;
import hotcupsofjava.transitsystemmanager.Logger;

public class Card extends TransitSystemObject {

    private String cardName;
    private double balance;
    private boolean suspended;
    private double totalSpending;
    private double totalFines;

    private User user;

    private Trip[] trips;

    public Card(String id, User user) {
        //TODO: Assign cardName.
        super(id);
        balance = 19;
        suspended = false;
        trips = new Trip[3];
        this.user = user;
    }

    public Card(String cardName,String id, User user){
        this(id,user);
        this.cardName = cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }

    public User getUser() {
        return user;
    }

    public void setTotalSpending(double totalSpending) {
        this.totalSpending = totalSpending;
    }

    public void setBalance(double balance) {
        this.balance = balance;
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

    public void addBalance(double amount) {
        balance += amount;
    }

    /* Returns the available balance on the card. */
    public double getBalance() {
        return balance;
    }

    /* Ensures that when a card is charged, the value is added to the current trip's total. */
    public boolean charge(double amount) {
        if (!suspended && getBalance() > 0) {
            trips[0].charge(amount,this);
            return true;
        }
        return false;
    }

    public void chargeFine(double amount) {
        balance -= amount;
        totalFines += amount;
    }

    public void newTrip(Trip trip) {
        if (!suspended) {
            Trip previousTrip = getCurrentTrip();
            if (previousTrip != null) {
                previousTrip.endTrip();
            }
            trips[2] = trips[1];
            trips[1] = trips[0];
            trips[0] = trip;
            UserManager.getInstance().addTrip(trip);
        }
    }

    public Trip getCurrentTrip() {
        return trips[0];
    }

    public double getTotalSpending() {
        return totalSpending;
    }

    public double getTotalFines() {
        return totalFines;
    }

    public String toString() {
        return "Card " + this.getId() + " owned by user " + user.getName();
    }

}
