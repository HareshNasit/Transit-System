package transitNetwork;

public class TripLocation {
    // true: tap on, false: tap off
    private boolean tappingOn;
    private Stop stop;
    private Route route;
    private long timestamp;

    TripLocation(long timestamp, boolean tappingOn, BusStop stop, Route route) {
        this.tappingOn = tappingOn;
        this.stop = stop;
        this.route = route;
        this.timestamp = timestamp;
    }

    TripLocation(long timestamp, boolean tappingOn, Station station) {
        this.tappingOn = tappingOn;
        this.stop = station;
        this.route = null;
        this.timestamp = timestamp;
    }

    public boolean isTapOn() {
        return this.tappingOn;
    }

    public Stop getStop() {
        return stop;
    }

    public boolean hasRoute() {
        return route != null;
    }

    public Route getRoute() {
        return route;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isBusStop() {
        return stop instanceof BusStop;
    }

    public boolean isStation() {
        return stop instanceof Station;
    }
}
