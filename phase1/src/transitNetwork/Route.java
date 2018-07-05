package transitNetwork;

import java.util.ArrayList;

public class Route {
    private String id;
    private ArrayList<Stop> route;

    Route(String id){
        this.id = id;
    }

    public void createRoute(ArrayList<Stop> stops){
        this.route = stops;
    }

    public String getId() {
        return id;
    }

    public int getDistance(Stop initialStop, Stop finalStop){
        int start = route.indexOf(initialStop);
        int end = route.indexOf(finalStop);
        return Math.abs(end - start);
    }

    public static void main(String[] args) {
        Route trial = new Route("NorthBound");
        ArrayList<Stop> stops = new ArrayList<>();
        Station station = new Station("Eglington West");
        Station station1 = new Station("St. Clair West");
        Station station2 = new Station("Dupont");
        Station station3 = new Station("Spadina");
        Station station4 = new Station("St. George");
        stops.add(station);
        stops.add(station1);
        stops.add(station2);
        stops.add(station3);
        stops.add(station4);
    }
}
