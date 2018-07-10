package transitNetwork;

import java.util.ArrayList;

public class Route {
    private String id;
    private ArrayList<BusStop> route;

    Route(String id){
        this.id = id;
    }

    Route(String id, ArrayList<BusStop> stops) {
        this.id = id;
        this.route = stops;
    }

    public void addStops(ArrayList<BusStop> stops){
        this.route.addAll(stops);
    }

    public void addStops(ArrayList<BusStop> stops, int index) {
        this.route.addAll(index, stops);
    }

    public String getId() {
        return id;
    }
    
    public boolean contains(BusStop stop) {
      return route.contains(stop);
    }

    public int getDistance(BusStop initialStop, BusStop finalStop){
        int start = route.indexOf(initialStop);
        int end = route.indexOf(finalStop);
        return Math.abs(end - start);
    }

}
