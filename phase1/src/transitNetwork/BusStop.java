package transitNetwork;

import user.Card;

public class BusStop extends Stop{

    BusStop(String name){
        super(name);
    }

    //Return true if successful, false otherwise.
    public boolean tapOn(Card card) {
        //TODO: implement tap functionality
        if (card.getBalance() >= 0) {
            card.charge(2);
            return true;
        }
        return false;
    }

    public boolean tapOff(Card card) {
        return true;
    }
}
