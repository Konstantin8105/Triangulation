package old.core.geometry;

import old.core.elements.Coordinate;

public class GeometryLineLine {

    public enum IntersectState {
        INTERSECT_CORNER,
        INTERSECT,
        NOT_INTERSECT,
        LINE_IN_LINE,
        INTERSECT_POINT_ON_LINE
    }

    public static IntersectState stateLineLine(Coordinate p1Line1, Coordinate p2Line1,
                                               Coordinate p1Line2, Coordinate p2Line2) {
//        System.out.println(GeometryPointLine.statePointOnLine(p2Line1, p1Line2, p2Line2).ordinal());
//        System.out.println(GeometryPointLine.statePointOnLine(p1Line1, p1Line2, p2Line2).ordinal());
//        System.out.println(GeometryPointLine.statePointOnLine(p2Line2, p1Line1, p2Line1).ordinal()); // true
//        System.out.println(GeometryPointLine.statePointOnLine(p1Line2, p1Line1, p2Line1).ordinal());
//        System.out.println(p1Line1.equals(p1Line2));
//        System.out.println(p1Line1.equals(p2Line2));
//        System.out.println(p2Line1.equals(p1Line2)); // true
//        System.out.println(p2Line1.equals(p2Line2));

        if (p1Line1.equals(p1Line2) && p2Line1.equals(p2Line2)) {
            return IntersectState.LINE_IN_LINE;
        }
        if (p2Line1.equals(p1Line2) && p1Line1.equals(p2Line2)) {
            return IntersectState.LINE_IN_LINE;
        }
        if (GeometryPointLine.statePointOnLine(p1Line1, p1Line2, p2Line2) == GeometryPointLine.PointLineState.POINT_ON_LINE &&
                (p2Line1.equals(p2Line2) || p2Line1.equals(p1Line2))) {
            return IntersectState.LINE_IN_LINE;
        }
        if ((p1Line1.equals(p2Line2) || p1Line1.equals(p1Line2)) &&
                GeometryPointLine.statePointOnLine(p2Line1, p1Line2, p2Line2) == GeometryPointLine.PointLineState.POINT_ON_LINE) {
            return IntersectState.LINE_IN_LINE;
        }
        if (GeometryPointLine.statePointOnLine(p1Line2, p1Line1, p2Line1) == GeometryPointLine.PointLineState.POINT_ON_LINE &&
                (p2Line2.equals(p2Line1) || p2Line2.equals(p1Line1))) {
            return IntersectState.LINE_IN_LINE;
        }
        if ((p1Line2.equals(p2Line1) || p1Line2.equals(p1Line1)) &&
                GeometryPointLine.statePointOnLine(p2Line2, p1Line1, p2Line1) == GeometryPointLine.PointLineState.POINT_ON_LINE) {
            return IntersectState.LINE_IN_LINE;
        }
//        if (p1Line1.equals(p1Line2) &&
//                (p2Line1.equals(p2Line2) || GeometryPointLine.statePointOnLine(p2Line1, p1Line2, p2Line2) == GeometryPointLine.PointLineState.POINT_ON_LINE) ) {
//            return IntersectState.LINE_IN_LINE;
//        }
//        if (p1Line1.equals(p2Line2) &&
//                (p2Line1.equals(p1Line2) || GeometryPointLine.statePointOnLine(p2Line1, p1Line2, p2Line2) == GeometryPointLine.PointLineState.POINT_ON_LINE)) {
//            return IntersectState.LINE_IN_LINE;
//        }
        if (GeometryPointLine.statePointOnLine(p1Line1, p1Line2, p2Line2) == GeometryPointLine.PointLineState.POINT_ON_LINE) {
            return IntersectState.INTERSECT_POINT_ON_LINE;
        }
        if (GeometryPointLine.statePointOnLine(p2Line1, p1Line2, p2Line2) == GeometryPointLine.PointLineState.POINT_ON_LINE) {
            return IntersectState.INTERSECT_POINT_ON_LINE;
        }
        if (GeometryPointLine.statePointOnLine(p1Line2, p1Line1, p2Line1) == GeometryPointLine.PointLineState.POINT_ON_LINE) {
            return IntersectState.INTERSECT_POINT_ON_LINE;
        }
        if (GeometryPointLine.statePointOnLine(p2Line2, p1Line1, p2Line1) == GeometryPointLine.PointLineState.POINT_ON_LINE) {
            return IntersectState.INTERSECT_POINT_ON_LINE;
        }
        if (p1Line1.equals(p1Line2) || p1Line1.equals(p2Line2)) {
            return IntersectState.INTERSECT_CORNER;
        }
        if (p2Line1.equals(p1Line2) || p2Line1.equals(p2Line2)) {
            return IntersectState.INTERSECT_CORNER;
        }
        if (isLinesIntersect(p1Line1, p2Line1, p1Line2, p2Line2)) {
            return IntersectState.INTERSECT;
        }
        return IntersectState.NOT_INTERSECT;

    }

    private static boolean isLinesIntersect(Coordinate p1, Coordinate p2, Coordinate p3, Coordinate p4) {
        Coordinate intersect = coordinateIntersect(p1, p2, p3, p4);
        if (intersect == null)
            return false;
        if (GeometryCoordinate.isPointInRectangle(intersect, p1, p2) &&
                GeometryCoordinate.isPointInRectangle(intersect, p3, p4)) {
            return true;
        }
        return false;
    }


    private static Coordinate coordinateIntersect(Coordinate p1, Coordinate p2, Coordinate p3, Coordinate p4) {
        // Line p1-p2
        // Line p3-p4
        if (Math.abs((p4.getY() - p3.getY()) * (p2.getX() - p1.getX()) - (p4.getX() - p3.getX()) * (p2.getY() - p1.getY())) < Precisions.epsilon()) {
            return null;
        }
//        if (Math.abs((p4.getY() - p2.getY()) * (p2.getX() - p1.getX()) - (p4.getX() - p3.getX()) * (p2.getY() - p1.getY())) < Precisions.epsilon()) {
////            logger.info("isLinesIntersect - finish #2");
//            return false;
//        }
        double Ua = ((p4.getX() - p3.getX()) * (p1.getY() - p3.getY()) - (p4.getY() - p3.getY()) * (p1.getX() - p3.getX())) /
                ((p4.getY() - p3.getY()) * (p2.getX() - p1.getX()) - (p4.getX() - p3.getX()) * (p2.getY() - p1.getY()));
//        double Ub = ((p2.getX() - p1.getX()) * (p1.getY() - p3.getY()) - (p2.getY() - p1.getY()) * (p1.getX() - p3.getX())) /
//                ((p4.getY() - p2.getY()) * (p2.getX() - p1.getX()) - (p4.getX() - p3.getX()) * (p2.getY() - p1.getY()));
        double x = p1.getX() + Ua * (p2.getX() - p1.getX());
        double y = p1.getY() + Ua * (p2.getY() - p1.getY());

        return new Coordinate(x, y);
    }

}
