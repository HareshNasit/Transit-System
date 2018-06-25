package user;

import java.util.ArrayList;

public class Admin {
  
  //There should only be one admin instance, so everything here can be static.
  
  private static ArrayList<Card> cardList = new ArrayList<Card>();
  private static ArrayList<User> userList = new ArrayList<User>();
  
  public static void addCard(Card card) {
    cardList.add(card);
  }
  
  public static void addUser(User user) {
    userList.add(user);
  }

}
