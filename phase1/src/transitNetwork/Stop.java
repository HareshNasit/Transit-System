package transitNetwork;

public class Stop {
    private Stop previous;
    private Stop next;
    private String name;
    Stop(String name){
        this.name = name;
    }

    public Stop getNext() {
        return next;
    }

    public String getName() {
        return name;
    }

    public Stop getPrevious() {
        return previous;
    }

    public void setNext(Stop next) {
        this.next = next;
    }

    public void setPrevious(Stop previous) {
        this.previous = previous;
    }
}
