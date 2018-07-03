package user;

import java.util.ArrayList;

public class User {
  private String name;
  private String email;
  private ArrayList<Card> cards;

  public User(String name, String email) {
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
  
  public boolean loadCard(Card card, int value) {
    if (cards.contains(card)) {
      return card.addBalance(value);
    }
    return false;
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
