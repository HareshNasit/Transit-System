package transitNetwork;

import user.Card;

public abstract class Stop {
  
  private final String id;
  private String name;
  
  public Stop(String id, String name) {
    this.id = id;
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getID() {
    return id;
  }

  public abstract boolean tapOff(Route route, Card card, int timestamp);
  public abstract boolean tapOn(Route route, Card card, int timestamp);
}
