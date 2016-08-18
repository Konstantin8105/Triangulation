package triangulation.geometry;


import triangulation.border.BorderBox;
import triangulation.elements.Point;

import java.util.List;

public class GeometryCoordinate extends Geometry {

    public static double angleBetween2Line
            (Point vertex,
             Point point1,
             Point point2) {
        return angleBetween2Line
                (vertex.getX(), vertex.getY(),
                        point1.getX(), point1.getY(),
                        point2.getX(), point2.getY());
    }

    public static boolean is3pointsCollinear
            (Point pLine1,
             Point pLine2,
             Point pLine3) {
        return is3pointsCollinear(pLine1.getX(), pLine1.getY(),
                pLine2.getX(), pLine2.getY(),
                pLine3.getX(), pLine3.getY());
    }

    public static boolean isPointInRectangle
            (Point Point,
             Point p1Line,
             Point p2Line) {
        return isPointInRectangle(Point.getX(), Point.getY(),
                p1Line.getX(), p1Line.getY(),
                p2Line.getX(), p2Line.getY());
    }

    public static boolean isPointsOnOneLine(List<Point> coordinatePoints) {
        for (int i = 2; i < coordinatePoints.size(); i++) {
            if (!is3pointsCollinear(coordinatePoints.get(0), coordinatePoints.get(1), coordinatePoints.get(i)))
                return false;
        }
        return true;
    }

    public static boolean isPointInRectangle(double x, double y,
                                             Point p1Line,
                                             Point p2Line) {
        return isPointInRectangle(x, y, p1Line.getX(), p1Line.getY(), p2Line.getX(), p2Line.getY());
    }

//    static boolean isPointInRectangle(Point point, List<Point> list) {
//        if(list.get(0).getX() == -12 && list.get(0).getY() == 57) {
//            System.out.println(point.toString() + list.toString() + "\n");
//        }
//        BorderBox borderBox = new BorderBox();
//        for (int i = 0; i < list.size(); i++) {
//            borderBox.addPoint(list.get(i));
//        }
//        return borderBox.isInBox(point);
//        double minX = list.get(0).getX();
//        double maxX = list.get(0).getX();
//        double minY = list.get(0).getY();
//        double maxY = list.get(0).getY();
//        for (Point c : list) {
//            if (minX > c.getX()) minX = c.getX();
//            if (maxX < c.getX()) maxX = c.getX();
//            if (minY > c.getY()) minY = c.getY();
//            if (maxY < c.getY()) maxY = c.getY();
//        }
//        return isPointInRectangle(point.getX(), point.getY(), minX, minY, maxX, maxY);
//    }

    static boolean isPointInRectangle(Point point, Point[] list) {

        BorderBox borderBox = new BorderBox();
        for (int i = 0; i < list.length; i++) {
            borderBox.addPoint(list[i]);
        }
        return borderBox.isInBox(point);

//        return isPointInRectangle(point, Arrays.asList(list));
    }
}
