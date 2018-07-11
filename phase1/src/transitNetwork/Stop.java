package transitNetwork;

import user.Card;

public abstract class Stop {
  
  private final String id;
  private String name;
  private Stop connectedStop = null;
  
  Stop(String id, String name) {
    this.id = id;
    this.name = name;
  }

  Stop(String id, String name, Stop stop) {
    this.id = id;
    this.name = name;
    this.connectedStop = stop;
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

  void connectStop(Stop stop) {
    connectedStop = stop;
  }

  Stop getConnectedStop() {
    return connectedStop;
  }

  public abstract boolean tapOn(Card card, int timestamp);
  public abstract boolean tapOff(Card card, int timestamp);
  public abstract boolean tapOn(Card card, int timestamp, Route route);
  public abstract boolean tapOff(Card card, int timestamp, Route route);
}
