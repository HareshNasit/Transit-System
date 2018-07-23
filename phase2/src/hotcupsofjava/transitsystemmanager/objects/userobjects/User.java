package hotcupsofjava.transitsystemmanager.objects.userobjects;


import hotcupsofjava.transitsystemmanager.Logger;
import hotcupsofjava.transitsystemmanager.objects.TransitSystemObject;

import java.util.ArrayList;

public class User extends TransitSystemObject {
    private String name;
    private String email;
    private ArrayList<Card> cards;

    public User(String id, String name, String email) {
        super(id);
        this.name = name;
        this.email = email;
        this.cards = new ArrayList<>();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public boolean removeCard(Card card) {
        if (cards.contains(card)) {
            cards.remove(card);
            Logger.log("Removed " + card.toString());
            return true;
        }
        return false;
    }

    public boolean suspendCard(Card card) {
        if (cards.contains(card)) {
            card.suspend();
            Logger.log("Suspended the " + card.toString());
            return true;
        }
        Logger.log("Failed to suspend the " + card.toString());
        return false;
    }

    public boolean loadCard(Card card, int value) {
        if (cards.contains(card) && (value == 10 || value == 20 || value == 50) && !card.isSuspended()) {
            card.addBalance(value);
            Logger.log("Successfully added $" + value + " to the " + card.toString());
            return true;
        }
        Logger.log("Failed to load $" + value + " to the " + card.toString());
        return false;
    }

    public Trip[] viewTrips(Card card) {
        return card.getTrips();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String newName) {
        name = newName;
    }

}
