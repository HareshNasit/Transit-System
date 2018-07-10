package transitNetwork;

import main.IDManager;

import java.util.ArrayList;
import java.util.HashMap;

public class RouteManager {
    private IDManager idManager;
    private HashMap<String, BusStop> stops;
    private HashMap<String, Route> routes;
    private HashMap<String, Station> stations;
    private Station lastStation;

    public RouteManager(IDManager idManager) {
        this.idManager = idManager;
        stops = new HashMap<>();
        routes = new HashMap<>();
        stations = new HashMap<>();
    }

    public void addStop(String id, String name) {
        idManager.addId(id);
        stops.put(id, new BusStop(id, name));
    }

    public void addStation(String id, String name) {
        idManager.addId(id);
        Station station = new Station(id, name);
        if (lastStation == null) {
            lastStation = station;
        } else {
            lastStation.connectStation(station);
            lastStation = station;
        }
        stations.put(id, station);
    }

    public void addStation(String id, String name, BusStop connectedBusStop) {
        idManager.addId(id);
        Station station = new Station(id, name, connectedBusStop);
        if (lastStation == null) {
            lastStation = station;
        } else {
            lastStation.connectStation(station);
            lastStation = station;
        }
        stations.put(id, station);
    }

    public void addRoute(String id) {
        idManager.addId(id);
        routes.put(id, new Route(id));
    }

    public void addRoute(String id, ArrayList<BusStop> stops) {
        idManager.addId(id);
        routes.put(id, new Route(id, stops));
    }

    public BusStop getStop(String id) {
        return stops.get(id);
    }

    public Route getRoute(String id) {
        return routes.get(id);
    }

    public Station getStation(String id) {
        return stations.get(id);
    }

    public boolean hasStop(String id) {
        return stops.containsKey(id);
    }

    public boolean hasRoute(String id) {
        return routes.containsKey(id);
    }

    public boolean hasStation(String id) {
        return stations.containsKey(id);
    }
}
