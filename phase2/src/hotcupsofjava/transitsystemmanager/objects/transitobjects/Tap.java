package hotcupsofjava.transitsystemmanager.objects.transitobjects;

import hotcupsofjava.transitsystemmanager.objects.userobjects.Card;

public interface Tap {
    void tapOn(long timeStamp, Stop stop, Card card);
    void tapOff(long timeStamp, Stop stop, Card card);

}
