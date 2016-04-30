package core.geometry;

import core.elements.Coordinate;

public class GeometryPointLine {

    public enum PointLineState {
        POINT_ON_LINE,
        POINT_OUTSIDE_LINE,
        POINT_ON_CORNER
    }

    public static PointLineState statePointOnLine(double x, double y,
                                                  Coordinate p1Line,
                                                  Coordinate p2Line){

        if(p1Line.equals(new Coordinate(x,y)))
            return PointLineState.POINT_ON_CORNER;
        if(p2Line.equals(new Coordinate(x,y)))
            return PointLineState.POINT_ON_CORNER;

        if (!Geometry.is3pointsCollinear(x, y,
                p1Line.getX(), p1Line.getY(),
                p2Line.getX(), p2Line.getY()))
            return PointLineState.POINT_OUTSIDE_LINE;

        if (!GeometryCoordinate.isPointInRectangle(x,y,p1Line,p2Line))
            return PointLineState.POINT_OUTSIDE_LINE;

        return PointLineState.POINT_ON_LINE;
    }

    public static PointLineState statePointOnLine(Coordinate coordinate,
                                                  Coordinate p1Line,
                                                  Coordinate p2Line){
        return statePointOnLine(coordinate.getX(),coordinate.getY(),
                p1Line,p2Line);
    }

}
