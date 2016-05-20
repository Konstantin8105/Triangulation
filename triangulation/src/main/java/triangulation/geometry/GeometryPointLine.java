package triangulation.geometry;

import triangulation.elements.Point;

public class GeometryPointLine {

    public enum PointLineState {
        POINT_ON_LINE,
        POINT_OUTSIDE_LINE,
        POINT_ON_CORNER
    }

    public static PointLineState statePointOnLine(double x, double y,
                                                  Point p1Line,
                                                  Point p2Line){

        if(p1Line.equals(new Point(x,y)))
            return PointLineState.POINT_ON_CORNER;
        if(p2Line.equals(new Point(x,y)))
            return PointLineState.POINT_ON_CORNER;

        if (!Geometry.is3pointsCollinear(x, y,
                p1Line.getX(), p1Line.getY(),
                p2Line.getX(), p2Line.getY()))
            return PointLineState.POINT_OUTSIDE_LINE;

        if (!GeometryCoordinate.isPointInRectangle(x,y,p1Line,p2Line))
            return PointLineState.POINT_OUTSIDE_LINE;

        return PointLineState.POINT_ON_LINE;
    }

    public static PointLineState statePointOnLine(Point Point,
                                                  Point p1Line,
                                                  Point p2Line){
        return statePointOnLine(Point.getX(),Point.getY(),
                p1Line,p2Line);
    }
}
