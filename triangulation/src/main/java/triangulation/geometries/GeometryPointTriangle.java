package triangulation.geometries;

import triangulation.elements.Point;

public class GeometryPointTriangle {
    public enum PointTriangleState {
        POINT_ON_LINE_0,
        POINT_ON_LINE_1,
        POINT_ON_LINE_2,
        POINT_ON_CORNER,
        POINT_INSIDE,
        POINT_OUTSIDE,
        POINT_OUTSIDE_LINE_0,
        POINT_OUTSIDE_LINE_1,
        POINT_OUTSIDE_LINE_2,
    }

    public static GeometryPointTriangle.PointTriangleState statePointInTriangle(Point p, Point[] tri) {
        for (Point aTri : tri) {
            if (p.equals(aTri))
                return PointTriangleState.POINT_ON_CORNER;
        }

        Value line1 = Geometry.pointOnLine(tri[0], p, tri[1]);
        if (GeometryCoordinate.isPointInRectangle(p, tri[0], tri[1])) {
            if (Geometry.is3pointsCollinear(line1))
                return PointTriangleState.POINT_ON_LINE_0;
        }
        if (!Geometry.isCounterClockwise(line1)) {
            return PointTriangleState.POINT_OUTSIDE_LINE_0;
        }

        Value line2 = Geometry.pointOnLine(tri[1], p, tri[2]);
        if (GeometryCoordinate.isPointInRectangle(p, tri[1], tri[2])) {
            if (Geometry.is3pointsCollinear(line2))
                return PointTriangleState.POINT_ON_LINE_1;
        }
        if (!Geometry.isCounterClockwise(line2)) {
            return PointTriangleState.POINT_OUTSIDE_LINE_1;
        }

        Value line3 = Geometry.pointOnLine(tri[2], p, tri[0]);
        if (GeometryCoordinate.isPointInRectangle(p, tri[2], tri[0])) {
            if (Geometry.is3pointsCollinear(line3))
                return PointTriangleState.POINT_ON_LINE_2;
        }
        if (!Geometry.isCounterClockwise(line3)) {
            return PointTriangleState.POINT_OUTSIDE_LINE_2;
        }

        boolean[] sign = new boolean[]{
                Geometry.isCounterClockwise(line1),
                Geometry.isCounterClockwise(line2),
                Geometry.isCounterClockwise(line3)
        };
        if (sign[0] && sign[1] && sign[2]) {
            return PointTriangleState.POINT_INSIDE;
        }

        return PointTriangleState.POINT_OUTSIDE;
    }
}
