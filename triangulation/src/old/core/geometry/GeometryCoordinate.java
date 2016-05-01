package old.core.geometry;


import old.core.elements.Coordinate;

import java.util.List;

public class GeometryCoordinate extends Geometry {

    public static double angleBetween2Line
            (Coordinate vertex,
             Coordinate point1,
             Coordinate point2) {
        return angleBetween2Line
                (vertex.getX(), vertex.getY(),
                        point1.getX(), point1.getY(),
                        point2.getX(), point2.getY());
    }

    public static boolean is3pointsCollinear
            (Coordinate pLine1,
             Coordinate pLine2,
             Coordinate pLine3) {
        return is3pointsCollinear(pLine1.getX(), pLine1.getY(),
                pLine2.getX(), pLine2.getY(),
                pLine3.getX(), pLine3.getY());
    }

    public static boolean isPointInRectangle
            (Coordinate coordinate,
             Coordinate p1Line,
             Coordinate p2Line) {
        return isPointInRectangle(coordinate.getX(), coordinate.getY(),
                p1Line.getX(), p1Line.getY(),
                p2Line.getX(), p2Line.getY());
    }

    public static boolean isPointsOnOneLine(List<Coordinate> coordinatePoints) {
        for (int i = 2; i < coordinatePoints.size(); i++) {
            if (!is3pointsCollinear(coordinatePoints.get(0), coordinatePoints.get(1), coordinatePoints.get(i)))
                return false;
        }
        return true;
    }

    public static boolean isPointInRectangle(double x, double y,
                                             Coordinate p1Line,
                                             Coordinate p2Line) {
        return isPointInRectangle(x, y, p1Line.getX(), p1Line.getY(), p2Line.getX(), p2Line.getY());
    }

    static boolean isPointInRectangle(Coordinate coordinate, List<Coordinate> list) {
        double minX = list.get(0).getX();
        double maxX = list.get(0).getX();
        double minY = list.get(0).getY();
        double maxY = list.get(0).getY();
        for (Coordinate c : list) {
            if (minX > c.getX()) minX = c.getX();
            if (maxX < c.getX()) maxX = c.getX();
            if (minY > c.getY()) minY = c.getY();
            if (maxY < c.getY()) maxY = c.getY();
        }
        return isPointInRectangle(coordinate.getX(), coordinate.getY(), minX, minY, maxX, maxY);
    }
}
