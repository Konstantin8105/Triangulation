package geometry;


import elements.Point;

public class GeometryLineLine {

    public enum IntersectState {
        INTERSECT_CORNER,
        INTERSECT,
        NOT_INTERSECT,
        LINE_IN_LINE,
        INTERSECT_POINT_ON_LINE
    }

    public static IntersectState stateLineLine(Point p1Line1, Point p2Line1,
                                               Point p1Line2, Point p2Line2) {

        int[] state = {
                GeometryPointLine.statePointOnLine(p1Line1, p1Line2, p2Line2).ordinal(),
                GeometryPointLine.statePointOnLine(p2Line1, p1Line2, p2Line2).ordinal(),
                GeometryPointLine.statePointOnLine(p1Line2, p1Line1, p2Line1).ordinal(),
                GeometryPointLine.statePointOnLine(p2Line2, p1Line1, p2Line1).ordinal()};

        int[] statePointOnLine = new int[GeometryPointLine.PointLineState.values().length];
        for (int i = 0; i < statePointOnLine.length; i++) {
            statePointOnLine[i] = 0;
        }

        for (int i = 0; i < state.length; i++) {
            statePointOnLine[state[i]]++;
        }

        System.out.println(" ------ ");
        System.out.println(p1Line1);
        System.out.println(p2Line1);
        System.out.println(p1Line2);
        System.out.println(p2Line2);
        for (int i = 0; i < 3; i++) {
            System.out.println(GeometryPointLine.PointLineState.values()[i].toString() + "\t"+statePointOnLine[i]);
        }

        if (statePointOnLine[GeometryPointLine.PointLineState.POINT_OUTSIDE_LINE.ordinal()] == 4) {
            if (isLinesIntersect(p1Line1, p2Line1, p1Line2, p2Line2)) {
                return IntersectState.INTERSECT;
            }
        } else if (statePointOnLine[GeometryPointLine.PointLineState.POINT_OUTSIDE_LINE.ordinal()] == 3 &&
                statePointOnLine[GeometryPointLine.PointLineState.POINT_ON_LINE.ordinal()] == 1) {
            return IntersectState.INTERSECT_POINT_ON_LINE;
        } else if (statePointOnLine[GeometryPointLine.PointLineState.POINT_OUTSIDE_LINE.ordinal()] == 2 &&
                statePointOnLine[GeometryPointLine.PointLineState.POINT_ON_CORNER.ordinal()] == 2) {
            return IntersectState.INTERSECT_CORNER;
        } else if (statePointOnLine[GeometryPointLine.PointLineState.POINT_OUTSIDE_LINE.ordinal()] == 2 &&
                statePointOnLine[GeometryPointLine.PointLineState.POINT_ON_LINE.ordinal()] == 2) {
            return IntersectState.LINE_IN_LINE;
        } else if (statePointOnLine[GeometryPointLine.PointLineState.POINT_ON_LINE.ordinal()] == 1 &&
                statePointOnLine[GeometryPointLine.PointLineState.POINT_OUTSIDE_LINE.ordinal()] == 1 &&
                statePointOnLine[GeometryPointLine.PointLineState.POINT_ON_CORNER.ordinal()] == 2) {
            return IntersectState.LINE_IN_LINE;
        }

        return IntersectState.NOT_INTERSECT;
    }

    private static boolean isLinesIntersect(Point p1, Point p2, Point p3, Point p4) {
        Point intersect = coordinateIntersect(p1, p2, p3, p4);
        if (intersect == null)
            return false;
        if (GeometryCoordinate.isPointInRectangle(intersect, p1, p2) &&
                GeometryCoordinate.isPointInRectangle(intersect, p3, p4)) {
            return true;
        }
        return false;
    }


    private static Point coordinateIntersect(Point p1, Point p2, Point p3, Point p4) {
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

        return new Point(x, y);
    }

}
