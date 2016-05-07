import triangulation.elements.Point;
import triangulation.geometry.GeometryPointLine;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GeometryPointLineTest {

    @Test
    public void testIsPointOnLine1() throws Exception {
        double x = 0;
        double y = 0;
        Point p1Line = new Point(-1d,-1d);
        Point p2Line = new Point(1d,1d);
        assertTrue(GeometryPointLine.statePointOnLine(x,y,p1Line,p2Line) == GeometryPointLine.PointLineState.POINT_ON_LINE);
    }

    @Test
    public void testIsPointOnLine2() throws Exception {
        double x = 1;
        double y = 0;
        Point p1Line = new Point(-1d,-1d);
        Point p2Line = new Point(1d,1d);
        assertTrue(GeometryPointLine.statePointOnLine(x,y,p1Line,p2Line) == GeometryPointLine.PointLineState.POINT_OUTSIDE_LINE);
    }

    @Test
    public void testIsPointOnLine3() throws Exception {
        double x = 2;
        double y = 2;
        Point p1Line = new Point(-1d,-1d);
        Point p2Line = new Point(1d,1d);
        assertTrue(GeometryPointLine.statePointOnLine(x,y,p1Line,p2Line) == GeometryPointLine.PointLineState.POINT_OUTSIDE_LINE);
    }

    @Test
    public void testIsPointOnLine4() throws Exception {
        double x = 1;
        double y = 1;
        Point p1Line = new Point(-1d,-1d);
        Point p2Line = new Point(1d,1d);
        assertTrue(GeometryPointLine.statePointOnLine(x,y,p1Line,p2Line) == GeometryPointLine.PointLineState.POINT_ON_CORNER);
    }
}