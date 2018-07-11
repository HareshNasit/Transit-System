package transitNetwork;

import java.util.ArrayList;

public class Trip {
    private ArrayList<TripLocation> stops;
    private Station lastSubwayTap = null;
    
    private boolean tripEnded;
    
    private long initialTime;
    
    private double tripValue;
    private double trueValue = 0;

    public Trip(long timestamp, BusStop busStop, Route route) {
        stops = new ArrayList<>();
        addStop(timestamp, true, busStop, route);
        initialTime = timestamp;
        tripEnded = false;
        tripValue = 0;
    }

    public Trip(long timestamp, Station initialStation) {
      stops = new ArrayList<>();
      addStop(timestamp, true, initialStation);
      initialTime = timestamp;
        tripEnded = false;
        tripValue = 0;
    }
    
    public void charge(double amount) {
      tripValue += amount;
      trueValue += amount;
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
        return stops.get(0).getStop();
    }

    public double getValue() {
      return tripValue;
    }

    public double getTrueValue() {
        return trueValue;
    }
    
    public Stop getLastStop() {
      return stops.get(stops.size() - 1).getStop();
    }
    
    public Station getLastSubwayTap() {
      return lastSubwayTap;
    }
    
    public void addStop(long timestamp, boolean tapType, BusStop stop, Route route) {
      stops.add(new TripLocation(timestamp, tapType, stop, route));
    }

    public void addStop(long timestamp, boolean tapType, Station station) {
        stops.add(new TripLocation(timestamp, tapType, station));
        lastSubwayTap = station;
    }

    public String toString() {
        StringBuilder details = new StringBuilder();
        return details.toString();
    }
}
