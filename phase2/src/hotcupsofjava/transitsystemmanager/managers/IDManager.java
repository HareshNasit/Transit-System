package hotcupsofjava.transitsystemmanager.managers;

import java.io.Serializable;
import java.util.HashMap;

public class IDManager implements Serializable {
    private static IDManager instance;

    public static IDManager getInstance() {
        return instance;
    }

    public static void setInstance(IDManager m) {
        instance = m;
    }

    private HashMap<String, Boolean> ids;

    public IDManager() {
        ids = new HashMap<>();
    }

    public String addId(String id) {
        int modifier = -1;
        String resultId = id;
        while (ids.containsKey(resultId)) {
            modifier++;
            resultId = id + modifier;
        }

        ids.put(id, true);
        return id;
    }
}

