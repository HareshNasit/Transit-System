package transitNetwork;

import java.util.ArrayList;

public class Stop {
  
  private static int counter = 0;
  private final int id;
  private String name;
  private ArrayList<Stop> adjacentStops = new ArrayList<Stop>();
  
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
  
  public ArrayList<Stop> getAdjacentStops(){
    return adjacentStops;
  }
  
  public void addAdjacentStop(Stop stop) {
    adjacentStops.add(stop);
  }
  
  public void removeAdjacentStop(Stop stop) {
    if (adjacentStops.contains(stop)) adjacentStops.remove(stop);
  }

}
