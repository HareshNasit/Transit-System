package transitNetwork;

import main.Logger;
import user.Card;
/**
 * @author
 * @version 1.0
 */
public class BusStop extends Stop{
    /**
     * The constructor of the BusStop that initializes the unique id and the name of the BusStop.
     * The id must be unique to all Stops, including BusStops and Stations, but the name may be
     * shared.
     *
     * @param id    the identification of the stop that is unique that cannot be changed
     * @param name  the name of the bus stop
     * @see         Stop
     */
    BusStop(String id, String name){
        super(id, name);
    }

    //Return true if successful, false otherwise.
    /**
     * Returns true if the tap on function of the BusStop was successful by the card.
     * The tap on function for BusStop charges the card of the user $2.00 when successful, and
     * the user may travel at any distance so long as they do not tap off.
     * If the card has less than $0 in its balance, then will tap unsuccessfully.
     * In both cases, timestamp is recorded.
     *
     * @param route     the route that the user is taking at this BusStop location
     * @param card      the card that is being used to tap onto the BusStop
     * @param timestamp the time recorded  at which the card taps onto the BusStop
     * @return          true if successful, false otherwise
     */
    public boolean tapOn(Route route, Card card, int timestamp) {
        //Test for conditions to start a new trip
        Trip trip = card.getCurrentTrip();
        Stop lastStop = trip.getLastStop();
        if ((trip == null || (lastStop != this && lastStop != this.getConnectedStop()) || trip.isEnded()
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
    /**
     * Returns true if the tap off function of the BusStop was successful by the card.
     * The tap off function will not charge the card if it was a legal trip, i.e. the card tapped on
     * at a BusStop that is legally connected to this BusStop, but if the trip was illegal, i.e.
     * last tap on was a station or an non-connected BusStop, they will be charged the maximum
     * of $6.00.
     *
     * @param route     the route that the user was taking before tapping off the BusStop
     * @param card      the card that is being used to tap off the BusStop
     * @param timestamp the time recorded at which the card taps off the BusStop
     * @return          true if successful tap off or if user illegally exited the transit without tapping
     */
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
    /**
     * Connects the stop to this BusStop so that tap on/tap off functions will consider trips between
     * these two stops legal. Override function.
     *
     *  @param stop  connects the stop to this BusStop if station, throws exception otherwise
     */
    @Override
    void connectStop(Stop stop) {
        if (!(stop instanceof Station)) throw new RuntimeException("BusStop connected with BusStop instead of Station");
        Station station = (Station) stop;
        super.connectStop(station);
    }
    /**
     * Connects the station to this BusStop so that tap on/tap off functions will consider trips between
     * the station and the BusStop legal.
     *
     * @param station   connects station to this BusStop
     */
    void connectStation(Station station) {
        connectStop(station);
    }
    /**
     * Returns the Station that this BusStop is connected to.
     *
     * @return      the station that this BusStop is connected to
     */
    Station getConnectedStation() {
        return (Station) this.getConnectedStop();
    }
}
