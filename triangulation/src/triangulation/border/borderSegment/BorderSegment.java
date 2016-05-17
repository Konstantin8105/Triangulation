package triangulation.border.borderSegment;

import triangulation.border.borderSegment.elements.LineWithPoints;
import triangulation.elements.Line;
import triangulation.elements.Point;
import triangulation.geometry.GeometryLineLine;

import java.util.List;

public abstract class BorderSegment {

    public abstract List<Line> segmentation(final Point nextPoint,final List<LineWithPoints> loop) throws Exception;

    protected static boolean isIntersect(Point nextPoint, Point middle, Point pointA, Point pointB) {
        GeometryLineLine.IntersectState state = GeometryLineLine.stateLineLine(
                nextPoint, middle,
                pointA, pointB
        );
        if (state == GeometryLineLine.IntersectState.INTERSECT ||
                state == GeometryLineLine.IntersectState.INTERSECT_POINT_ON_LINE ||
                state == GeometryLineLine.IntersectState.LINE_IN_LINE) {
            return true;
        }
        return false;
    }
}
