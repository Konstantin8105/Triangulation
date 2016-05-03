package core.old.id;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortArrayID<T> {
    private class Element{
        public int id;
        public T value;
    }
    private List<Element> list;

    private static class GeneratorId {
        private static int position = 1;
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

    public SortArrayID() {
        list = new ArrayList<>();
    }

    public void add(T value){
        Element element = new Element();
        element.value = value;
        element.id = GeneratorId.getID();
        list.add(element);
    }

    public int getSize(){
        return list.size();
    }
}
