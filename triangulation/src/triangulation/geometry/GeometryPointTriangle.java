package triangulation.geometry;


import triangulation.elements.Point;

public class GeometryPointTriangle {

    public enum PointTriangleState {
        POINT_ON_LINE_0,
        POINT_ON_LINE_1,
        POINT_ON_LINE_2,
        POINT_ON_CORNER,
        POINT_INSIDE,
        POINT_OUTSIDE
    }

    public static PointTriangleState isPointInTriangle(Point p, Point[] tri) {

        if (!GeometryCoordinate.isPointInRectangle(p, tri))
            return PointTriangleState.POINT_OUTSIDE;

        for (triangulation.elements.Point aTri : tri) {
            if (p.equals(aTri))
                return PointTriangleState.POINT_ON_CORNER;
        }

        if (GeometryPointLine.statePointOnLine(p, tri[0], tri[1]) == GeometryPointLine.PointLineState.POINT_ON_LINE) {
            return PointTriangleState.POINT_ON_LINE_0;
        }
        if (GeometryPointLine.statePointOnLine(p, tri[1], tri[2]) == GeometryPointLine.PointLineState.POINT_ON_LINE) {
            return PointTriangleState.POINT_ON_LINE_1;
        }
        if (GeometryPointLine.statePointOnLine(p, tri[2], tri[0]) == GeometryPointLine.PointLineState.POINT_ON_LINE) {
            return PointTriangleState.POINT_ON_LINE_2;
        }

        GeometryLineLine.IntersectState noIS = GeometryLineLine.IntersectState.NOT_INTERSECT;
        if (GeometryLineLine.stateLineLine(p, tri[0], tri[1], tri[2]) == noIS &&
                GeometryLineLine.stateLineLine(p, tri[1], tri[2], tri[0]) == noIS &&
                GeometryLineLine.stateLineLine(p, tri[2], tri[0], tri[1]) == noIS) {

            //double sumOfAngles = GeometryCoordinate.angleBetween2Line(tri[0], tri[1], tri.get(2))
            //        + GeometryCoordinate.angleBetween2Line(tri[1], tri[2], tri.get(0))
            //        + GeometryCoordinate.angleBetween2Line(tri[2], tri[1], tri.get(0));

//            if
//                    (GeometryCoordinate.angleBetween2Line(tri[0], tri[1], p) <
//                    GeometryCoordinate.angleBetween2Line(tri[0], tri[1], tri[2]) &&
//
//                    GeometryCoordinate.angleBetween2Line(tri[0], tri[2], p) <
//                            GeometryCoordinate.angleBetween2Line(tri[0], tri[2], tri[1]) &&
//
//                    GeometryCoordinate.angleBetween2Line(tri[1], tri[0], p) <
//                            GeometryCoordinate.angleBetween2Line(tri[1], tri[0], tri[2]) &&
//
//                    GeometryCoordinate.angleBetween2Line(tri[1], tri[2], p) <
//                            GeometryCoordinate.angleBetween2Line(tri[1], tri[2], tri[0]) &&
//
//                    GeometryCoordinate.angleBetween2Line(tri[2], tri[0], p) <
//                            GeometryCoordinate.angleBetween2Line(tri[2], tri[0], tri[1]) &&
//
//                    GeometryCoordinate.angleBetween2Line(tri[2], tri[1], p) <
//                            GeometryCoordinate.angleBetween2Line(tri[2], tri[1], tri[0])
//                    )
//
//                return PointTriangleState.POINT_INSIDE;

            // interpret v1 and v2 as vectors
            Point v1 = new Point(tri[1].getX() - tri[0].getX(), tri[1].getY() - tri[0].getY());
            Point v2 = new Point(tri[2].getX() - tri[0].getX(), tri[2].getY() - tri[0].getY());
            double det = v1.getX() * v2.getY() - v2.getX() * v1.getY();
            Point tmp = new Point(p.getX() - tri[0].getX(), p.getY() - tri[0].getY());
            double lambda = (tmp.getX() * v2.getY() - v2.getX() * tmp.getY()) / det;
            double mue = (v1.getX() * tmp.getY() - tmp.getX() * v1.getY()) / det;
            if (lambda >= 0 && mue >= 0 && (lambda + mue) <= 1) {
                return PointTriangleState.POINT_INSIDE;
            }


        }

        return PointTriangleState.POINT_OUTSIDE;

//
//        // interpret v1 and v2 as vectors
//        Point v1 = new Point(tri[1].getX() - tri[0].getX(), tri[1].getY() - tri[0].getY());
//        Point v2 = new Point(tri[2].getX() - tri[0].getX(), tri[2].getY() - tri[0].getY());
//        double det = v1.getX() * v2.getY() - v2.getX() * v1.getY();
//        Point tmp = new Point(p.getX() - tri[0].getX(), p.getY() - tri[0].getY());
//        double lambda = (tmp.getX() * v2.getY() - v2.getX() * tmp.getY()) / det;
//        double mue = (v1.getX() * tmp.getY() - tmp.getX() * v1.getY()) / det;
//        if (lambda < Precisions.epsilon() && (lambda + mue) <= 1)
//            return PointTriangleState.POINT_ON_LINE;
//        if (mue < Precisions.epsilon() && (lambda + mue) <= 1)
//            return PointTriangleState.POINT_ON_LINE;
//        if (lambda >= 0 && mue >= 0 && (lambda + mue) <= 1) {
//            return PointTriangleState.POINT_INSIDE;
//        }
//        return PointTriangleState.POINT_OUTSIDE;
    }
}
