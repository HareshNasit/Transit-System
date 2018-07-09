package transitNetwork;

import user.Card;

public class BusStop extends Stop{
  
  private Station connectedStation = null;

    public BusStop(String name){
        super(name);
    }

    //Return true if successful, false otherwise.
    public boolean tapOn(Route route, Card card, int timestamp) {
        //TODO: Add detection for disjointed trips
        if(card.getCurrentTrip() == null){
            if (card.getBalance() > 0) {
                card.newTrip(this,0); //TODO: NEED TO REPLACE 0 WITH THE TIME STAMP.
                card.charge(2);
                card.getCurrentTrip().addStop(this);
                return true;
            }
        }
        else if(card.getCurrentTrip().getInitialTime() < 120){ //TODO: Implement - current time.
            if(card.getCurrentTrip().getLastStop().getName().equals(this.getName())){
                card.getCurrentTrip().addStop(this);
                card.charge(0); // Free ride.
                return true;
            }
        }
        else if(card.getBalance() >0) {
            card.newTrip(this, 0); //TODO: REPLACE 0 WITH THE TIME STAMP.
            card.getCurrentTrip().addStop(this);
            card.charge(2);
            return true;
        }
        return false;
    }

    public boolean tapOff(Route route, Card card, int timestamp) {
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
