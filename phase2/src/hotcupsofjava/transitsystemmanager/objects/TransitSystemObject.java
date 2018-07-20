package hotcupsofjava.transitsystemmanager.objects;


import hotcupsofjava.transitsystemmanager.managers.IDManager;

public abstract class TransitSystemObject {
    private static IDManager idManager = null;

    public static void setIdManager(IDManager m) {
        if (idManager == null) {
            idManager = m;
        }
    }

    private String id;
    
    public TransitSystemObject(String id) {
        this.id = idManager.addId(id); }

    public String getId() {
        return this.id;
    }

}

