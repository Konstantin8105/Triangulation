package elements.Collections;

import java.util.*;

public class IDable<T> {
    private class Element {
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

    public IDable() {
        list = new ArrayList<>();
    }

    public void add(T value) {
        Element element = new Element(GeneratorId.getID(), value);
        list.add(element);
    }

    public void remove(int id) {
        Element search = new Element(id);
        int index = Collections.binarySearch(list,search,comparatorId);
        list.remove(index);
    }

    public void remove(T value){
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).value.equals(value)){
                index = i;
                break;
            }
        }
        list.remove(index);
    }

    public T get(int id){
        Element search = new Element(id);
        int index = Collections.binarySearch(list,search,comparatorId);
        return list.get(index).value;
    }

    public int size(){
        return list.size();
    }

    public List<Integer> getListId(){
        List<Integer> ids = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            ids.add(list.get(i).id);
        }
        return ids;
    }
}
