package elements.Collections;

import java.util.*;

public class IDable<T> {

    public class Element<T> {
        public int id;
        public T value;

        public Element(int id, T value) {
            this.id = id;
            this.value = value;
        }

        public Element(int id) {
            this.id = id;
        }
    }

    private List<elements.Collections.IDable.Element> list = new ArrayList<>();

    private static class GeneratorId {
        private static int position = 0;

        public static int getID() {
            return position++;
        }
    }

    private Comparator<Element> comparatorId = new Comparator<Element>() {
        @Override
        public int compare(Element o1, Element o2) {
            return o1.id - o2.id;
        }
    };

    public int add(T value) {
        int id = GeneratorId.getID();
        Element element = new Element(id, value);
        list.add(element);
        return id;
    }

    public void add(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            Element element = new Element(GeneratorId.getID(), list.get(i));
            this.list.add(element);
        }
    }

    public void remove(int id) {
        Element search = new Element(id);
        int index = Collections.binarySearch(list, search, comparatorId);
        list.remove(index);
    }

    public Element<T> getById(int id) {
        Element search = new Element(id);
        int index = Collections.binarySearch(list, search, comparatorId);
        return list.get(index);
    }

    public T getByIndex(int index) {
        return (T) list.get(index).value;
    }

    public Element<T> getElement(int index) {
        return list.get(index);
    }

    public List<elements.Collections.IDable.Element> getListElements() {
        return list;
    }

    public int size() {
        return list.size();
    }
}
