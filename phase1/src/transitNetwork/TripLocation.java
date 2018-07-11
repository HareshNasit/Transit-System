package transitNetwork;

public class TripLocation {
    private boolean tapType;
    private Stop stop;
    private Route route;
    private long timestamp;

    TripLocation(long timestamp, boolean tapType, BusStop stop, Route route) {
        this.tapType = tapType;
        this.stop = stop;
        this.route = route;
        this.timestamp = timestamp;
    }

    TripLocation(long timestamp, boolean tapType, Station station) {
        this.tapType = tapType;
        this.stop = station;
        this.route = null;
        this.timestamp = timestamp;
    }

    public boolean getTapType() {
        return this.tapType;
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
