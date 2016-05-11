package triangulation.grid;

import triangulation.border.BorderBox;
import triangulation.elements.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Set<Integer> positions = convert(box);
        for (Integer position : positions) {
            map[position].add(id);
        }
    }

    public Set<Integer> get(Point point) {
        int position = convert(point);
        return new HashSet<>(map[position]);
    }

    private int convert(Point point) {
        int positionX = (int) Math.min((point.getX() - box.getX_min()) / dx, size - 1);
        int positionY = (int) Math.min((point.getY() - box.getY_min()) / dy, size - 1);
        return positionX + size * positionY;
    }

    private Set<Integer> convert(BorderBox inputBox) {
        Set<Integer> positions = new HashSet<>();
        int positionX_min = (int) ((inputBox.getX_min() - box.getX_min()) / dx);
        int positionY_min = (int) ((inputBox.getY_min() - box.getY_min()) / dy);
        int positionX_max = (int) Math.min((inputBox.getX_max() - box.getX_min()) / dx, size - 1);
        int positionY_max = (int) Math.min((inputBox.getY_max() - box.getY_min()) / dy, size - 1);
        for (int i = positionX_min; i <= positionX_max; i++) {
            for (int j = positionY_min; j <= positionY_max; j++) {
                int position = i + size * j;
                for (int k = 0; k < map[position].size(); k++) {
                    positions.add(map[position].get(k));
                }
            }
        }
        return positions;
    }
}
