package transitNetwork;

import user.Card;

public class Stop {
  
  private static int counter = 0;
  private final int id;
  private String name;
  
  public Stop(String name) {
    id = counter++;
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public int getID() {
    return id;
  }
  
  //Return true if successful, false otherwise.
  public boolean tapOn(Card card) {
    //TODO: implement tap functionality
    if (card.getBalance() >= 0) {
      card.charge(2);
      return true;
    }
    return false;
  }
  
  public boolean tapOff(Card card) {
    return true;
  }
}
