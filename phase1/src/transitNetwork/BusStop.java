package transitNetwork;

import main.Logger;
import user.Card;

public class BusStop extends Stop{
  
  private Station connectedStop = null;

    public BusStop(String id, String name){
        super(id, name);
    }

    //Return true if successful, false otherwise.
    public boolean tapOn(Route route, Card card, int timestamp) {
        //Test for conditions to start a new trip
        Trip trip = card.getCurrentTrip();
        Stop lastStop = trip.getLastStop();
        if ((trip == null || (lastStop != this && lastStop != this.connectedStop) || trip.isEnded()
            || timestamp - trip.getInitialTime() > 120) && card.getBalance() > 0) {
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
        Stop lastStop = card.getCurrentTrip().getLastStop();
        if (!(lastStop instanceof BusStop) || !route.contains((BusStop)lastStop) || card.getCurrentTrip().isEnded()) {
          //Illegal exit!
          card.chargeFine(6);
          card.getCurrentTrip().endTrip();
          Logger.log(card.toString() + " charged for illegal exit of bus stop " + getName() + " at timestamp " + timestamp);
        }
        return true;
    }
    
    protected void connectStation(Station station) {
      connectedStop = station;
    }
    
    public Station getConnectedStation() {
      return connectedStop;
    }
}
