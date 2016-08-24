package counter;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Counter {
    final private Map<String, Integer> map = new TreeMap<>();
    private String name;
    private long timeMoment = System.currentTimeMillis();

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

    public void addTime(String str) {
        long presentMoment = System.currentTimeMillis();
        int time = (int) (presentMoment - timeMoment);
        timeMoment = presentMoment;

        if (map.get(str) == null) {
            map.put(str, time);
        } else map.put(str, map.get(str) + time);
    }


    @Override
    public String toString() {
        String msg = name + "\n";
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
