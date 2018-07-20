package hotcupsofjava.transitsystemmanager.managers;

import java.util.HashMap;

public class IDManager {
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

