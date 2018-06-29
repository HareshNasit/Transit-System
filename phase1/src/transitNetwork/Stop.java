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
  
  public boolean tap(Card card) {
    //TODO: implement tap function
    return false;
  }
  
}
