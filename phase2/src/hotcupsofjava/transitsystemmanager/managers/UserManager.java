package hotcupsofjava.transitsystemmanager.managers;

import hotcupsofjava.transitsystemmanager.Logger;
import hotcupsofjava.transitsystemmanager.objects.userobjects.Card;
import hotcupsofjava.transitsystemmanager.objects.userobjects.Trip;
import hotcupsofjava.transitsystemmanager.objects.userobjects.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class UserManager implements Serializable {
    private static UserManager instance;

    public static UserManager getInstance() {
        return instance;
    }
    
    public static void setInstance(UserManager m) {
        instance = m;
    }

    private HashMap<String, User> users;
    private HashMap<String, Card> cards;
    private TapManager tapManager;
    private ArrayList<Trip> trips;

    public UserManager(double busCost, double subwayCost) {
        users = new HashMap<>();
        cards = new HashMap<>();
        UserManager.setInstance(this);
        tapManager = new TapManager(busCost,subwayCost);
        trips = new ArrayList<>();
    }

    public void addTrip(Trip trip){
        trips.add(trip);
    }

    public double calculateRevenue() {
        double revenue = 0;
        for (Card card : cards.values()) {
            revenue += card.getTotalSpending();
        }
        Logger.logRevenue(revenue,calculateTrueRevenue());
        return revenue;
    }

    public double calculateFines() {
        int fines = 0;
        for (Card card : cards.values()) {
            fines += card.getTotalFines();
        }
        return fines;
    }

    public int calculateDistanceTravelled(){
        int distance=0;
        for(Trip trip: trips){
            distance += trip.getDistanceTravelled();
        }
        return distance;
    }

    public double calculateTrueRevenue(){
        double value = 0;
        for(Trip trip: trips){
            value += trip.getTrueValue();
        }
        return value;
    }

    public void addUser(String id, String name, String email) {
        User user = new User(id, name, email);
        users.put(user.getId(), user);
    }

    public void removeCard(Card card, User user){
        cards.remove(card.getId());
        user.removeCard(card);
    }

    public void addCard(User user, String cardId) {
        Card card = new Card(cardId, user);
        user.addCard(card);
        cards.put(card.getId(), card);
    }

    public User getUser(String id) {
        return users.get(id);
    }

    public Card getCard(String id) {
        return cards.get(id);
    }

    public boolean hasUser(String id) {
        return users.containsKey(id);
    }

    public boolean hasCard(String id) {
        return cards.containsKey(id);
    }

    public boolean hasUser(String name, String email){
        for(User user: users.values()){
            if(user.getName().equals(name)&&user.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    public TapManager getTapManager() {
        return tapManager;
    }
}
