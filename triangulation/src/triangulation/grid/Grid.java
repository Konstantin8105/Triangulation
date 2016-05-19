package triangulation.grid;

import triangulation.border.BorderBox;
import triangulation.elements.Point;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    //    Counter counter = new Counter("Grid");
    private List<Integer> map[];
    double dx, dy;
    int size;
    BorderBox box;

    public Grid(BorderBox box, int sizePoint) {
        //counter.add("Grid");
        this.box = box;
        size = calculateSize(sizePoint);
        dx = (box.getX_max() - box.getX_min()) / (double) size;
        dy = (box.getY_max() - box.getY_min()) / (double) size;
        map = new ArrayList[size * size];
        for (int i = 0; i < size * size; i++) {
            map[i] = new ArrayList<>();
        }
    }

    private final int calculateSize(int sizePoint) {
        return (int) Math.max(1, Math.sqrt(sizePoint));
    }

    public void add(final BorderBox box, final int id) {
        //counter.add("add");
        Integer[] positions = convert(box);
        for (Integer position : positions) {
            map[position].add(id);
        }
    }

    public final List<Integer> get(final Point point) {
        //counter.add("get");
        // TODO: 19.05.2016 optimize - don`t delete every time
        int position = convert(point);
        return map[position];
    }

    private int counter = 0;
    private int MAX_COUNTER_FOR_START_DELETE = 100;

    private class RemoveBox {
        public BorderBox box;
        public int id;

        public RemoveBox(BorderBox box, int id) {
            this.box = box;
            this.id = id;
        }
    }

    private List<RemoveBox> removeBoxes = new ArrayList<>();

    public void remove(final BorderBox box, final int id) {
        //counter.add("remove");
        // TODO: 19.05.2016 optimize - don`t delete every time
//        Integer[] positions = convert(box);
//        for (Integer position : positions) {
//            map[position].remove((Integer) id);
//        }
        removeBoxes.add(new RemoveBox(box, id));
        counter++;
        if (counter >= MAX_COUNTER_FOR_START_DELETE) {
            counter = 0;
            for (RemoveBox removeBox : removeBoxes) {
                // TODO: 19.05.2016 optimize by sorting
                Integer[] positions = convert(removeBox.box);
                for (Integer position : positions) {
                    map[position].remove((Integer) removeBox.id);
                }
            }
            removeBoxes.clear();
        }
    }

    private int convert(final Point point) {
        //counter.add("convert1");
        int positionX = (int) Math.min((point.getX() - box.getX_min()) / dx, size - 1);
        int positionY = (int) Math.min((point.getY() - box.getY_min()) / dy, size - 1);
        return positionX + size * positionY;
    }

    private final Integer[] convert(final BorderBox inputBox) {
        //counter.add("convert2");
        int positionX_min = (int) ((inputBox.getX_min() - box.getX_min()) / dx);
        int positionY_min = (int) ((inputBox.getY_min() - box.getY_min()) / dy);
        int positionX_max = (int) Math.min((inputBox.getX_max() - box.getX_min()) / dx, size - 1);
        int positionY_max = (int) Math.min((inputBox.getY_max() - box.getY_min()) / dy, size - 1);

        Integer[] positions = new Integer[(positionX_max - positionX_min + 1) * (positionY_max - positionY_min + 1)];
        int k = 0;
        for (int i = positionX_min; i <= positionX_max; i++) {
            for (int j = positionY_min; j <= positionY_max; j++) {
                positions[k] = i + size * j;
                k++;
            }
        }
        return positions;
    }
//
//    public Counter getCounter() {
//        return counter;
//    }
}
