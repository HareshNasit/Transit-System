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

}
