package triangulation.geometries;

import triangulation.elements.Point;

public class GeometryPointTriangle {

    public enum PointTriangleState {
        POINT_ON_LINE_0,
        POINT_ON_LINE_1,
        POINT_ON_LINE_2,
        POINT_ON_CORNER,
        POINT_INSIDE,
        POINT_OUTSIDE_LINE_0,
        POINT_OUTSIDE_LINE_1,
        POINT_OUTSIDE_LINE_2,
    }

    public static PointTriangleState statePointInTriangle(Point p, Point[] tri, Geometry.POINT_ON_LINE[] values) {
        for (Point aTri : tri) {
            if (p.equals(aTri))
                return PointTriangleState.POINT_ON_CORNER;
        }

        if (GeometryCoordinate.isPointInRectangle(p, tri[0], tri[1])) {
            if (Geometry.is3pointsCollinear(values[0]))
                return PointTriangleState.POINT_ON_LINE_0;
        }
        if (Geometry.isAtRightOf(values[0])) {
            return PointTriangleState.POINT_OUTSIDE_LINE_0;
        }

        if (GeometryCoordinate.isPointInRectangle(p, tri[1], tri[2])) {
            if (Geometry.is3pointsCollinear(values[1]))
                return PointTriangleState.POINT_ON_LINE_1;
        }
        if (Geometry.isAtRightOf(values[1])) {
            return PointTriangleState.POINT_OUTSIDE_LINE_1;
        }

        if (GeometryCoordinate.isPointInRectangle(p, tri[2], tri[0])) {
            if (Geometry.is3pointsCollinear(values[2]))
                return PointTriangleState.POINT_ON_LINE_2;
        }
        if (Geometry.isAtRightOf(values[2])) {
            return PointTriangleState.POINT_OUTSIDE_LINE_2;
        }

        return PointTriangleState.POINT_INSIDE;
    }
}
