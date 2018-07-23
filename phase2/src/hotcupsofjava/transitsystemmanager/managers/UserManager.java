package hotcupsofjava.transitsystemmanager.managers;

import hotcupsofjava.transitsystemmanager.Logger;
import hotcupsofjava.transitsystemmanager.objects.userobjects.Card;
import hotcupsofjava.transitsystemmanager.objects.userobjects.User;

import java.util.HashMap;

public class UserManager {
    private HashMap<String, User> users;
    private HashMap<String, Card> cards;

    public UserManager() {
        users = new HashMap<>();
        cards = new HashMap<>();
    }

    public double calculateRevenue() {
        double revenue = 0;
        for (Card card : cards.values()) {
            revenue += card.getTotalSpending();
        }
        Logger.logRevenue(revenue,0);
        return revenue;
    }

    public double calculateFines() {
        int fines = 0;
        for (Card card : cards.values()) {
            fines += card.getTotalFines();
        }
        return fines;
    }

    public void addUser(String id, String name, String email) {
        User user = new User(id, name, email);
        users.put(user.getId(), user);
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
}
