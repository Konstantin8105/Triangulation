package triangulation.elements.Collections;

import java.util.*;

public class IDable<T> implements Iterable<IDable<T>.Element<T>> {

    public void removeSame() {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i+1; j < list.size(); j++) {
                if(list.get(i).equals(list.get(j))){
                    list.remove(i);
                    removeSame();
                    break;
                }
            }
        }
    }

    public class Element<V> {
        final public int id;
        public V value;

        public Element(int id, V value) {
            this.id = id;
            this.value = value;
        }

        public Element(int id) {
            this.id = id;
        }
    }

    private ArrayList<triangulation.elements.Collections.IDable<T>.Element<T>> list = new ArrayList<>();

    private int generatorID = 0;

    private int getID() {
        return generatorID++;
    }

    private final Comparator<Element> comparatorId = new Comparator<Element>() {
        @Override
        public int compare(Element o1, Element o2) {
            return o1.id - o2.id;
        }
    };

    public int add(T value) {
        int id = getID();
        Element<T> element = new Element<>(id, value);
        list.add(element);
        return id;
    }

    public void add(List<T> list) {
        this.list.ensureCapacity(this.list.size() + list.size());
        for (T aList : list) {
            Element<T> element = new Element<>(getID(), aList);
            this.list.add(element);
        }
    }

    private int convertIDtoINDEX(int id) {
        if (0 <= id && id < list.size()) {
            if (list.get(id).id == id) {
                return id;
            }
        }
        Element search = new Element(id);
        return Collections.binarySearch(list, search, comparatorId);
    }

    public void remove(int id) {
        int index = convertIDtoINDEX(id);
        list.remove(index);
    }

    public Element<T> getById(int id) {
        int index = convertIDtoINDEX(id);
        if (index < 0)
            return null;
        return list.get(index);
    }

    @Override
    public Iterator<IDable<T>.Element<T>> iterator() {
        return list.iterator();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return list.size();
    }
}
