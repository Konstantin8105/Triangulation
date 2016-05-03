package core.old.geometry;


import core.elements.Coordinate;

import java.util.List;

public class GeometryPointTriangle {

    public enum PointTriangleState {
        POINT_ON_LINE,
        POINT_ON_CORNER,
        POINT_INSIDE,
        POINT_OUTSIDE
    }

    public static PointTriangleState isPointInTriangle(Coordinate coordinate, List<Coordinate> tri) {

        if (!GeometryCoordinate.isPointInRectangle(coordinate, tri))
            return PointTriangleState.POINT_OUTSIDE;

        for (int i = 0; i < tri.size(); i++) {
            if (coordinate.equals(tri.get(i)))
                return PointTriangleState.POINT_ON_CORNER;
        }

        if (GeometryPointLine.statePointOnLine(coordinate, tri.get(0), tri.get(1)) == GeometryPointLine.PointLineState.POINT_ON_LINE) {
            return PointTriangleState.POINT_ON_LINE;
        }
        if (GeometryPointLine.statePointOnLine(coordinate, tri.get(1), tri.get(2)) == GeometryPointLine.PointLineState.POINT_ON_LINE) {
            return PointTriangleState.POINT_ON_LINE;
        }
        if (GeometryPointLine.statePointOnLine(coordinate, tri.get(2), tri.get(0)) == GeometryPointLine.PointLineState.POINT_ON_LINE) {
            return PointTriangleState.POINT_ON_LINE;
        }

        GeometryLineLine.IntersectState noIS = GeometryLineLine.IntersectState.NOT_INTERSECT;
        if (GeometryLineLine.stateLineLine(coordinate, tri.get(0), tri.get(1), tri.get(2)) == noIS &&
                GeometryLineLine.stateLineLine(coordinate, tri.get(1), tri.get(2), tri.get(0)) == noIS &&
                GeometryLineLine.stateLineLine(coordinate, tri.get(2), tri.get(0), tri.get(1)) == noIS) {

            //double sumOfAngles = GeometryCoordinate.angleBetween2Line(tri.get(0), tri.get(1), tri.get(2))
            //        + GeometryCoordinate.angleBetween2Line(tri.get(1), tri.get(2), tri.get(0))
            //        + GeometryCoordinate.angleBetween2Line(tri.get(2), tri.get(1), tri.get(0));

            if
                    (GeometryCoordinate.angleBetween2Line(tri.get(0), tri.get(1), coordinate) <
                    GeometryCoordinate.angleBetween2Line(tri.get(0), tri.get(1), tri.get(2)) &&

                    GeometryCoordinate.angleBetween2Line(tri.get(0), tri.get(2), coordinate) <
                            GeometryCoordinate.angleBetween2Line(tri.get(0), tri.get(2), tri.get(1)) &&

                    GeometryCoordinate.angleBetween2Line(tri.get(1), tri.get(0), coordinate) <
                            GeometryCoordinate.angleBetween2Line(tri.get(1), tri.get(0), tri.get(2)) &&

                    GeometryCoordinate.angleBetween2Line(tri.get(1), tri.get(2), coordinate) <
                            GeometryCoordinate.angleBetween2Line(tri.get(1), tri.get(2), tri.get(0)) &&

                    GeometryCoordinate.angleBetween2Line(tri.get(2), tri.get(0), coordinate) <
                            GeometryCoordinate.angleBetween2Line(tri.get(2), tri.get(0), tri.get(1)) &&

                    GeometryCoordinate.angleBetween2Line(tri.get(2), tri.get(1), coordinate) <
                            GeometryCoordinate.angleBetween2Line(tri.get(2), tri.get(1), tri.get(0))
                    )

                return PointTriangleState.POINT_INSIDE;
        }

        return PointTriangleState.POINT_OUTSIDE;
    }
}
