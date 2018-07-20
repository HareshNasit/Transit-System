package hotcupsofjava.transitsystemmanager.objects;

public abstract class TransitSystemObject {
    private String id;
    
    public TransitSystemObject(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}

