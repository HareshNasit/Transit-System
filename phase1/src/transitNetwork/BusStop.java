package transitNetwork;

import user.Card;

public class BusStop extends Stop{

    BusStop(String name){
        super(name);
    }

    //Return true if successful, false otherwise.
    public boolean tapOn(Card card) {
        //TODO: Add detection for disjointed trips
        if (card.getBalance() >= 0) {
            card.charge(2);
            card.getCurrentTrip().addStop(this);
            return true;
        }
        return false;
    }

    public boolean tapOff(Card card) {
        //TODO: Add detection for disjointed trips
        card.getCurrentTrip().addStop(this);
        return true;
    }
}
