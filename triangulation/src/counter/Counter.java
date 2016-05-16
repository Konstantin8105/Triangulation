package counter;

import java.util.HashMap;
import java.util.Map;

public class Counter {
    Map<String, Integer> map = new HashMap<>();

    public void add(String str) {
        if (map.get(str) == null)
            map.put(str, 0);
        map.put(str, map.get(str) + 1);
    }

    @Override
    public String toString() {
        return "Counter{" +
                "map=" + map +
                '}';
    }
}
