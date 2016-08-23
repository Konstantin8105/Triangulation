package counter;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Counter {
    Map<String, Integer> map = new TreeMap<>();
    String name;

    public Counter(String name) {
        this.name = name;
    }

    public void add(String str) {
        if (map.get(str) == null)
            map.put(str, 0);
        map.put(str, map.get(str) + 1);
    }

    public void add(String str, int value) {
        if (map.get(str) == null)
            map.put(str, 0);
        if (map.get(str) < value)
            map.put(str, value);
    }

    public void addTime(String str, int time) {
        if (map.get(str) == null)
            map.put(str, time);
        else map.put(str, map.get(str) + time);
    }


    @Override
    public String toString() {
        String msg = new String();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            msg += key + ":" + map.get(key) + "\n";
        }
        return msg;
    }

    public void clear() {
        map.clear();
    }
}
