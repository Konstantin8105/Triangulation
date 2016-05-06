package triangulation;

import elements.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GridIndex {

    private List<Integer>[][] map;
    private double minX;
    private double minY;
    private double dx;
    private double dy;
    private int gridAmount;

    public GridIndex(int gridAmount, BorderBox bBox) throws Exception {
        if (gridAmount < 1) {
            throw new Exception("Not correct gridAmount");
        }
        this.gridAmount = gridAmount;
        this.minX = bBox.getX_min();
        this.minY = bBox.getY_min();
        this.dx = (bBox.getX_max() - bBox.getX_min()) / (gridAmount);
        this.dy = (bBox.getY_max() - bBox.getY_min()) / (gridAmount);
        map = new List[gridAmount][gridAmount];
        for (int i = 0; i < gridAmount; i++) {
            for (int j = 0; j < gridAmount; j++) {
                map[i][j] = new ArrayList<>();
            }
        }
    }

    public GridIndex(GridIndex grid) {
        this.minX = grid.minX;
        this.minY = grid.minY;
        this.dx = grid.dx;
        this.dy = grid.dy;
        this.gridAmount = grid.gridAmount;
        map = new List[gridAmount][gridAmount];
        for (int i = 0; i < gridAmount; i++) {
            for (int j = 0; j < gridAmount; j++) {
                map[i][j] = new ArrayList<>();
            }
        }
    }

    public void add(int id, BorderBox borderBox) throws Exception {
        List<Position> positions = convert(borderBox);
        for (int i = 0; i < positions.size(); i++) {
            map[position(positions.get(i).i)][position(positions.get(i).j)].add(id);
        }
    }

    private int position(int pos) {
        if (pos < 0) return 0;
        if (pos > gridAmount) return gridAmount;
        return pos;
    }

    public void remove(int id) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                for (int k = 0; k < map[i][j].size(); k++) {
                    if (map[i][j].get(k) == id) {
                        map[i][j].remove(k);
                        k--;
                    }
                }
            }
        }
    }

    public List<Integer> get(Point point) throws Exception {
        BorderBox bBox = new BorderBox();
        bBox.addPoint(point);
        return get(bBox);
    }

    public List<Integer> get(BorderBox box) throws Exception {
        List<Position> positions = convert(box);
        List<Integer> out = new ArrayList<>();
        for (int i = 0; i < positions.size(); i++) {
            out.addAll(map[positions.get(i).i][positions.get(i).j]);
        }
        return out;
    }


    private class Position {
        public int i, j;

        public Position(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "i=" + i +
                    ", j=" + j +
                    '}';
        }
    }

    private List<Position> convert(BorderBox borderBox) throws Exception {
        Position position1 = convertToPosition(borderBox.getX_min(), borderBox.getY_min());
        Position position2 = convertToPosition(borderBox.getX_max(), borderBox.getY_max());
        Position min = new Position(
                min(max(0, position1.i - 1), max(0, position2.i - 1)),
                min(max(0, position1.j - 1), max(0, position2.j - 1))
        );
        Position max = new Position(
                max(min(gridAmount - 1, position1.i + 1), min(gridAmount - 1, position2.i + 1)),
                max(min(gridAmount - 1, position1.j + 1), min(gridAmount - 1, position2.j + 1))
        );

        List<Position> out = new ArrayList<>();
        for (int i = min.i; i <= max.i; i++) {
            for (int j = min.j; j <= max.j; j++) {
                out.add(new Position(i, j));
            }
        }
        if (out.size() == 0)
            throw new Exception("Size of grid cannot be zero");
        return out;
    }

    private Position convertToPosition(double x, double y) {
        x = x - minX;
        y = y - minY;
        Position result = new Position(
                (int) Math.round(x / dx),
                (int) Math.round(y / dy)
        );
        return result;
    }

    private int max(int a, int b) {
        if (a > b) return a;
        return b;
    }

    private int min(int a, int b) {
        if (a > b) return b;
        return a;
    }
}
