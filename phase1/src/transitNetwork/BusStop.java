package transitNetwork;

import main.Logger;
import user.Card;

public class BusStop extends Stop{
  
  private Station connectedStation = null;

    public BusStop(String name){
        super(name);
    }

    //Return true if successful, false otherwise.
    public boolean tapOn(Route route, Card card, int timestamp) {
        //Test for conditions to start a new trip
        if ((card.getCurrentTrip() == null || !card.getCurrentTrip().getLastStop().getName().equals(getName()) 
            || timestamp - card.getCurrentTrip().getInitialTime() > 120 ) && card.getBalance() > 0) {
          card.newTrip(this, timestamp);
        }
        //If the card can be charged, finish tapping onto the stop
        //Under the conditions where a new trip has been created, it is always possible to tap onto at least one stop
        if (card.charge(2)) {
          card.getCurrentTrip().addStop(this);
          Logger.log(card.toString() + " tapped on to bus stop " + getName() + " at timestamp " + timestamp);
          return true;
        }
        Logger.log(card.toString() + " failed to tap on to bus stop " + getName() + " at timestamp " + timestamp);
        return false;
    }

    public boolean tapOff(Route route, Card card, int timestamp) {
        //TODO: Add detection for disjointed trips
      Logger.log(card.toString() + " tapped off of bus stop " + getName() + " at timestamp " + timestamp);
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
