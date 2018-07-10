package transitNetwork;

import user.Card;

public class BusStop extends Stop{
  
  private Station connectedStation = null;

    public BusStop(String name){
        super(name);
    }

    //Return true if successful, false otherwise.
    public boolean tapOn(Route route, Card card, int timestamp) {
        if(card.getCurrentTrip() == null){
            if (card.getBalance() > 0) {
                card.newTrip(this,timestamp);
                card.charge(2);
                card.getCurrentTrip().addStop(this);
                return true;
            }
        }
        else if(card.getCurrentTrip().getLastStop().getName().equals(this.getName())){
            if(card.getCurrentTrip().getValue()<6){
                if(card.getBalance()>0){
                    card.getCurrentTrip().addStop(this);
                    card.charge(2);
                    return true;
                }
            }
            else if(card.getCurrentTrip().getInitialTime() - timestamp < 120){
                card.getCurrentTrip().addStop(this);
                card.charge(0); //Free Trip
                return true;
            }
        }
        else if (card.getBalance() > 0) {
                card.newTrip(this, timestamp);
                card.charge(2);
                card.getCurrentTrip().addStop(this);
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
