package user;

import transitNetwork.Trip;

import java.util.ArrayList;
import java.util.Iterator;

public class Admin {
  
  //There should only be one admin instance.
  
  private final ArrayList<Card> cardList;
  private final ArrayList<User> userList;
  
  public Admin() {
	  cardList = new ArrayList<Card>();
	  userList = new ArrayList<User>();
  }
  
  public void addCard(User user) {
	Card card = new Card();
	user.addCard(card);
    cardList.add(card);
  }
  
  public void addUser(String name, String email) {
	User user = new User(name, email);
    userList.add(user);
  }

  public double calculateRevenue(){
      Iterator<Card> cardIterator = cardList.iterator();
      int revenue = 0;
      while (cardIterator.hasNext()){
          revenue += cardIterator.next().getTotalSpending();
      }
  }

}
