package transitNetwork;

import java.util.ArrayList;

public class Route {
    private String id;
    private Stop initialStop;
    private Stop finalStop;

    Route(String id){
        this.id = id;
    }

    public void createRoute(ArrayList<Stop> stops){
        this.initialStop = stops.get(0);
        Stop curr = initialStop;
        Stop previous = null;
        this.finalStop = stops.get(stops.size() -1);
        for(int i=1;i<stops.size();i++){
            curr.setNext(stops.get(i));
            curr.setPrevious(previous);
            previous = curr;
            curr = curr.getNext();
        }
        finalStop.setPrevious(previous);
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
        trial.createRoute(stops);
        System.out.println(station2.getNext().getName());
        System.out.println(station4.getPrevious().getName());
    }
}
