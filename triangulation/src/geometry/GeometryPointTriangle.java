package geometry;


import elements.Point;

import java.util.List;

public class GeometryPointTriangle {

    public enum PointTriangleState {
        POINT_ON_LINE,
        POINT_ON_CORNER,
        POINT_INSIDE,
        POINT_OUTSIDE
    }

    public static PointTriangleState isPointInTriangle(Point Point, Point[] tri) {

        if (!GeometryCoordinate.isPointInRectangle(Point, tri))
            return PointTriangleState.POINT_OUTSIDE;

        for (int i = 0; i < tri.length; i++) {
            if (Point.equals(tri[i]))
                return PointTriangleState.POINT_ON_CORNER;
        }

        if (GeometryPointLine.statePointOnLine(Point, tri[0], tri[1]) == GeometryPointLine.PointLineState.POINT_ON_LINE) {
            return PointTriangleState.POINT_ON_LINE;
        }
        if (GeometryPointLine.statePointOnLine(Point, tri[1], tri[2]) == GeometryPointLine.PointLineState.POINT_ON_LINE) {
            return PointTriangleState.POINT_ON_LINE;
        }
        if (GeometryPointLine.statePointOnLine(Point, tri[2], tri[0]) == GeometryPointLine.PointLineState.POINT_ON_LINE) {
            return PointTriangleState.POINT_ON_LINE;
        }

        GeometryLineLine.IntersectState noIS = GeometryLineLine.IntersectState.NOT_INTERSECT;
        if (GeometryLineLine.stateLineLine(Point, tri[0], tri[1], tri[2]) == noIS &&
                GeometryLineLine.stateLineLine(Point, tri[1], tri[2], tri[0]) == noIS &&
                GeometryLineLine.stateLineLine(Point, tri[2], tri[0], tri[1]) == noIS) {

            //double sumOfAngles = GeometryCoordinate.angleBetween2Line(tri[0], tri[1], tri.get(2))
            //        + GeometryCoordinate.angleBetween2Line(tri[1], tri[2], tri.get(0))
            //        + GeometryCoordinate.angleBetween2Line(tri[2], tri[1], tri.get(0));

            if
                    (GeometryCoordinate.angleBetween2Line(tri[0], tri[1], Point) <
                    GeometryCoordinate.angleBetween2Line(tri[0], tri[1], tri[2]) &&

                    GeometryCoordinate.angleBetween2Line(tri[0], tri[2], Point) <
                            GeometryCoordinate.angleBetween2Line(tri[0], tri[2], tri[1]) &&

                    GeometryCoordinate.angleBetween2Line(tri[1], tri[0], Point) <
                            GeometryCoordinate.angleBetween2Line(tri[1], tri[0], tri[2]) &&

                    GeometryCoordinate.angleBetween2Line(tri[1], tri[2], Point) <
                            GeometryCoordinate.angleBetween2Line(tri[1], tri[2], tri[0]) &&

                    GeometryCoordinate.angleBetween2Line(tri[2], tri[0], Point) <
                            GeometryCoordinate.angleBetween2Line(tri[2], tri[0], tri[1]) &&

                    GeometryCoordinate.angleBetween2Line(tri[2], tri[1], Point) <
                            GeometryCoordinate.angleBetween2Line(tri[2], tri[1], tri[0])
                    )

                return PointTriangleState.POINT_INSIDE;
        }

        return PointTriangleState.POINT_OUTSIDE;
    }
}
