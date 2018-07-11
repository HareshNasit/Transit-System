package transitNetwork;

import user.Card;

public abstract class Stop {

    private final String id;
    private String name;
    private Stop connectedStop;

    Stop(String id, String name) {
        this.id = id;
        this.name = name;
        this.connectedStop = null;
    }

    Stop(String id, String name, Stop stop) {
        this.id = id;
        this.name = name;
        this.connectedStop = stop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return id;
    }

    void connectStop(Stop stop) {
        connectedStop = stop;
    }

    public Stop getConnectedStop() {
        return connectedStop;
    }
}
