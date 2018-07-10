package user;

import java.util.ArrayList;

public class User {
  private String id;
  private String name;
  private String email;
  private ArrayList<Card> cards;

  public User(String id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }
  
  public void addCard(Card card) {
    cards.add(card);
  }
  
  public boolean removeCard(Card card) {
    if (cards.contains(card)){
      cards.remove(card);
      return true;
    }
    return false;
  }

  public boolean suspendCard(Card card) {
    if (cards.contains(card)) {
      card.suspend();
      return true;
    }
    return false;
  }
  
  public boolean loadCard(Card card, int value) {
    if (cards.contains(card) && (value == 10 || value == 20 || value == 50) && !card.isSuspended()) {
      card.addBalance(value);
      return true;
    }
    return false;
  }

  public String getId() {
    return id;
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
