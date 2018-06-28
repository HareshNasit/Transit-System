package transitNetwork;

public class Stop {
    private Stop previous;
    private Stop next;
    private String name;
    Stop(String name){
        this.name = name;
    }
}
