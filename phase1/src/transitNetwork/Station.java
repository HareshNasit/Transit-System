package transitNetwork;

import user.Card;

public class Station extends Stop{

    public Station(String name){
        super(name);
    }
    
    public boolean tapOn(Route route, Card card) {
        //TODO: Add detection for disjointed trips
        if (card.getBalance() > 0) card.getCurrentTrip().addStop(this);
        return true;
    }
    
    public boolean tapOff(Route route, Card card) {
      //TODO: Use route.getDistance() to calculate value of trip
      //card.charge(route.getDistance() * 0.50)
      return true;
    }
}
