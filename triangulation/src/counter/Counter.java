package counter;

import java.util.HashMap;
import java.util.Map;

public class Counter {
    Map<String, Integer> map = new HashMap<>();
    String name;

    public Counter(String name) {
        this.name = name;
    }

    public void add(String str) {
        if (map.get(str) == null)
            map.put(str, 0);
        map.put(str, map.get(str) + 1);
    }

    public void add(String str , int value) {
        if (map.get(str) == null)
            map.put(str, 0);
        if(map.get(str) < value)
            map.put(str, value);
    }

    @Override
    public String toString() {
        return "Counter{" + name +
                "map=" + map +
                '}';
    }
}
