package transitNetwork;

import user.Card;

public class BusStop extends Stop{
  
  private Station connectedStation = null;

    public BusStop(String name){
        super(name);
    }

    //Return true if successful, false otherwise.
    public boolean tapOn(Route route, Card card) {
        //TODO: Add detection for disjointed trips
        if (card.getBalance() >= 0) {
            card.charge(2);
            card.getCurrentTrip().addStop(this);
            return true;
        }
        return false;
    }

    public boolean tapOff(Route route, Card card) {
        //TODO: Add detection for disjointed trips
        card.getCurrentTrip().addStop(this);
        return true;
    }
    
    protected void connectStation(Station station) {
      connectedStation = station;
    }
    
    public Station getConnectedStation() {
      return connectedStation;
    }
}
