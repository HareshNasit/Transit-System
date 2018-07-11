package transitNetwork;

import java.util.ArrayList;

public class Trip {
    private ArrayList<Stop> stops;
    private Station lastSubwayTap = null;
    
    private boolean tripEnded = false;
    
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
    
    public void endTrip() {
      tripEnded = true;
    }
    
    public boolean isEnded() {
      return tripEnded;
    }

    public long getInitialTime() {
        return initialTime;
    }

    public Stop getInitialStop(){
        return stops.get(0);
    }

    public double getValue() {
      return tripValue;
    }
    
    public Stop getLastStop() {
      return stops.get(stops.size());
    }
    
    public Station getLastSubwayTap() {
      return lastSubwayTap;
    }
    
    public void addStop(Stop stop) {
      stops.add(stop);
      if (stop instanceof Station) lastSubwayTap = (Station) stop;
    }
}
