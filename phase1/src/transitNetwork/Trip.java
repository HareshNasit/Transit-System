package transitNetwork;

import java.util.ArrayList;

public class Trip {
    private ArrayList<Stop> stops;
    
    //TODO: time tracking
    private long initialTime;
    
    private double tripValue = 0;
    
    public Trip(Stop initialStop, int timestamp) {
      stops = new ArrayList<Stop>();
      stops.add(initialStop);
      initialTime = timestamp;
    }
    
    public void charge(double amount) {
      tripValue += amount;
      if (tripValue > 6) {
        tripValue = 6;
      }
    }
    
    public double getValue() {
      return tripValue;
    }
    
    public Stop getLastStop() {
      return stops.get(stops.size());
    }
    
    public void addStop(Stop stop) {
      stops.add(stop);
    }
}
