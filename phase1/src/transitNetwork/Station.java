package transitNetwork;

import user.Card;

public class Station extends Stop{

    Station(String name){
        super(name);
    }
    
    public boolean tapOn(Card card) {
      return true;
    }
    
    public boolean tapOff(Card card) {
      //TODO: Use route.getDistance() to calculate value of trip
      //card.charge(route.getDistance() * 0.50)
      return true;
    }
}
