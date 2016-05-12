package triangulation.grid;

import triangulation.border.BorderBox;
import triangulation.elements.Point;

import java.util.*;

public class Grid {
    private List<Integer> map[];
    double dx, dy;
    int size;
    BorderBox box;

    public Grid(BorderBox box, int sizePoint) {
        this.box = box;
        size = calculateSize(sizePoint);
        dx = (box.getX_max() - box.getX_min()) / (double) size;
        dy = (box.getY_max() - box.getY_min()) / (double) size;
        map = new ArrayList[size * size];
        for (int i = 0; i < size * size; i++) {
            map[i] = new ArrayList<>();
        }
    }

    private int calculateSize(int sizePoint) {
        return (int) Math.max(1, Math.sqrt(sizePoint));
    }

    public void add(BorderBox box, int id) {
        Integer[] positions = convert(box);
        for (Integer position : positions) {
            map[position].add(id);
        }
    }

    public Integer[] get(Point point) {
        int position = convert(point);
        Integer[] out = new Integer[map[position].size()];
        out = map[position].toArray(out);
        return out;
    }

    public void remove(BorderBox box, int id) {
        Integer[] positions = convert(box);
        for (Integer position : positions) {
            map[position].remove((Integer) id);
        }
    }

    private int convert(Point point) {
        int positionX = (int) Math.min((point.getX() - box.getX_min()) / dx, size - 1);
        int positionY = (int) Math.min((point.getY() - box.getY_min()) / dy, size - 1);
        return positionX + size * positionY;
    }

    static long time = 0;

    private Integer[] convert(BorderBox inputBox) {
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
}
