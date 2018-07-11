package transitNetwork;

import java.util.ArrayList;

public class Trip {
    private ArrayList<TripLocation> locations;
    private Station lastSubwayTap = null;

    private int distanceTravelled;
    private boolean tripEnded;

    private long initialTime;

    private double tripValue;
    private double trueValue = 0;

    public Trip(long timestamp, BusStop busStop, Route route) {
        locations = new ArrayList<>();
        addLocation(timestamp, true, busStop, route, false);
        initialTime = timestamp;
        tripEnded = false;
        tripValue = 0;
        distanceTravelled = 0;
    }

    public Trip(long timestamp, Station initialStation) {
        locations = new ArrayList<>();
        addLocation(timestamp, true, initialStation, false);
        initialTime = timestamp;
        tripEnded = false;
        tripValue = 0;
        distanceTravelled = 0;
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

    public Stop getInitialStop() {
        return locations.get(0).getStop();
    }

    public Stop getLastStop() {
        return getLastLocation().getStop();
    }

    public Route getLastRoute() {
        return getLastLocation().getRoute();
    }

    public boolean lastWasTapOn() {
        return getLastLocation().isTapOn();
    }

    public TripLocation getLastLocation() {
        return locations.get(locations.size() - 1);
    }

    public double getValue() {
        return tripValue;
    }

    public double getTrueValue() {
        return trueValue;
    }

    public Station getLastSubwayTap() {
        return lastSubwayTap;
    }

    public void addLocation(long timestamp, boolean tappingOn, BusStop stop, Route route, boolean fined) {
        if (!tappingOn && !fined) {
            distanceTravelled += route.getDistance((BusStop) getLastStop(), stop);
        }
        locations.add(new TripLocation(timestamp, tappingOn, stop, route));
    }

    public void addLocation(long timestamp, boolean tappingOn, Station station, boolean fined) {
        if (!tappingOn && !fined) {
            distanceTravelled += station.getDistance((Station) getLastStop());
        }
        locations.add(new TripLocation(timestamp, tappingOn, station));
        lastSubwayTap = station;
    }

    public String toString() {
        StringBuilder details = new StringBuilder();
        details.append("Trip Details\n");
        details.append("==================\n");
        details.append("Started at:  ").append(initialTime);
        details.append("\nActive:      ").append(!tripEnded);
        details.append(String.format("\nCost:        $%.2f", tripValue));
        details.append("\n\nLocation History:\n==================\n");
        for (TripLocation location : locations) {
            details.append(String.format("[%d] %s ",
                    location.getTimestamp(), (location.isTapOn()) ? "tap on " : "tap off "));
            if (location.isBusStop()) {
                details.append(String.format("on bus at stop %s on route %s",
                        location.getStop().getName(), location.getRoute().getId()));
            } else if (location.isStation()) {
                details.append("at station ").append(location.getStop().getName());
            }
        }
        return details.toString();
    }
}
