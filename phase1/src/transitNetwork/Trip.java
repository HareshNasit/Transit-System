package transitNetwork;

import main.Logger;

import java.util.ArrayList;

/**
 * @author
 * @version 1.0
 */
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

    /**
     * Tracks the total fare for this Trip. Once it reaches $6.00, the trip no longer charges
     * additionally.
     *
     * @param amount The amount charge added onto this Trip.
     */
    public void charge(double amount) {
        tripValue += amount;
        trueValue += amount;
        if (tripValue > 6) {
            tripValue = 6;
        }
    }

    public void endTrip() {
        tripEnded = true;
        Logger.logStops(distanceTravelled);
    }

    /**
     * Returns true if the Trip has ended, otherwise false.
     *
     * @return If trip is ended, otherwise false.
     */
    public boolean isEnded() {
        return tripEnded;
    }

    /**
     * Returns long of the time of the initial tap on of the first stop.
     *
     * @return initial time of the first tap on of the first stop of this Trip
     */
    public long getInitialTime() {
        return initialTime;
    }

    /**
     * Returns the initial Stop that the card user first tapped onto in this Trip.
     *
     * @return the first Stop that was tapped onto this Trip
     */
    public Stop getInitialStop() {
        return locations.get(0).getStop();
    }

    /**
     * Returns the last Stop of this Trip that the card user last tapped off in this Trip.
     *
     * @return the last Stop tapped off in this Trip
     */
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

    /**
     * Returns the amount in double that this Trip is being charged for.
     *
     * @return the charge value of this Trip
     */
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
