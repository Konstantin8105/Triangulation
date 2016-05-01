package old.core.storage;

import old.core.elements.Coordinate;
import old.core.elements.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RamPoint {

    List<Point> points;

    public RamPoint() {
        points = new ArrayList<>();
    }

    private static Comparator<Point> comparatorId = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            return o1.getId() - o2.getId();
        }
    };

//    public void addCoordinate(Coordinate coordinate) throws Exception {
//        points.add(new Point(coordinate));
//        Collections.sort(points, comparatorId);
//    }

    public void addCoordinate(List<Coordinate> coordinates) throws Exception {
        for (Coordinate coordinate : coordinates) {
            points.add(new Point(coordinate));
        }
        Collections.sort(points, comparatorId);
    }

    public List<Coordinate> getCoordinate() throws Exception {
        List<Coordinate> coordinates = new ArrayList<>(points.size());
        for (Point point : points) {
            coordinates.add(point.getCoordinate());
        }
        return coordinates;
    }

    public int sizePoints() {
        return points.size();
    }

    public List<Point> getPoints() {
        return points;
    }

//    public List<Integer> getPointsID() throws Exception {
//        List<Integer> ids = new ArrayList<>(points.size());
//        for (Point point : points) {
//            ids.add(point.getId());
//        }
//        return ids;
//    }

    public Point getPointsID(int idPointA) throws Exception {
        Point search = new Point(idPointA, new Coordinate(-1, -1));
        int index = Collections.binarySearch(points, search, comparatorId);
        return points.get(index);
    }

//    public int getIdPointByCoordinate(Coordinate coordinate) throws Exception {
//        for (Point point : points) {
//            if (point.getCoordinate().equals(coordinate))
//                return point.getId();
//        }
//        throw new Exception("Not found getIdPointByCoordinate = " + coordinate);
//    }

    public Coordinate getCoordinateByPointId(int idPoint) throws Exception {
        int index = Collections.binarySearch(points, new Point(idPoint, new Coordinate(-1, -1)), comparatorId);
        return points.get(index).getCoordinate();
    }

    public void deletePoint(int idPoint) throws Exception {
        int index = Collections.binarySearch(points, new Point(idPoint, new Coordinate(-1, -1)), comparatorId);
        points.remove(index);
    }

    public void deleteSameCoordinates() throws Exception {
        if (points.size() < 2)
            return;

        List<Integer> idPointDelete = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                if (points.get(i).getCoordinate().equals(points.get(j).getCoordinate())) {
                    idPointDelete.add(i);
                }
            }
        }
        for (int i = 0; i < idPointDelete.size(); i++) {
            deletePoint(idPointDelete.get(i));
        }
    }
}
