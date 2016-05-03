package geometry.test;

import elements.Point;
import geometry.GeometryLineLine;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GeometryLineLineTest {

    @Test
    public void testIsLinesIntersect() throws Exception {
        Point c1 = new Point(0f, 2f);
        Point c2 = new Point(2f, 0f);
        Point a1 = new Point(-5f, -5f);
        Point a2 = new Point(10f, 10f);
        assertTrue(GeometryLineLine.stateLineLine(c1, c2, a1, a2) == GeometryLineLine.IntersectState.INTERSECT);
        {
            Point q1 = new Point(5f, 5f);
            Point q2 = new Point(10f, 10f);
            assertTrue(GeometryLineLine.stateLineLine(c1, c2, q1, q2) == GeometryLineLine.IntersectState.NOT_INTERSECT);
        }
        {
            Point q1 = new Point(5f, 5f);
            Point q2 = new Point(10f, 10f);
            assertTrue(GeometryLineLine.stateLineLine(c1, c2, q1, q2) == GeometryLineLine.IntersectState.NOT_INTERSECT);
        }
        {
            Point q1 = new Point(0f, 0f);
            Point q2 = new Point(1f, 1f);
            assertTrue(GeometryLineLine.stateLineLine(c1, c2, q1, q2) == GeometryLineLine.IntersectState.INTERSECT);
        }
        {
            Point q1 = new Point(0f, 2f);
            Point q2 = new Point(12f, 12f);
            assertTrue(GeometryLineLine.stateLineLine(c1, c2, q1, q2) == GeometryLineLine.IntersectState.INTERSECT_CORNER);
        }
        {
            Point q1 = new Point(0.5f, 1.5f);
            Point q2 = new Point(1.5f, 0.5f);
            assertTrue(GeometryLineLine.stateLineLine(c1, c2, q1, q2) == GeometryLineLine.IntersectState.LINE_IN_LINE);
        }
        {
            Point q1 = new Point(5f, 5f);
            Point q2 = new Point(0f, 0f);
            Point w1 = new Point(1f, 0f);
            Point w2 = new Point(4f, 4f);
            assertTrue(GeometryLineLine.stateLineLine(q1, q2, w1, w2) == GeometryLineLine.IntersectState.INTERSECT);
        }
        {
//            p1Line1 = Point{ x = 4.00, y = 4.00}
//            p2Line1 = Point{ x = 0.00, y = 0.00}
//            p1Line2 = Point{ x = 4.00, y = 4.00}
//            p2Line2 = Point{ x = 5.00, y = 5.00}
//            result = LINE_IN_LINE
            Point q1 = new Point(4f, 4f);
            Point q2 = new Point(0f, 0f);
            Point w1 = new Point(4f, 4f);
            Point w2 = new Point(5f, 5f);
            assertTrue(GeometryLineLine.stateLineLine(q1, q2, w1, w2) == GeometryLineLine.IntersectState.INTERSECT_CORNER);
        }
        {
//            p1Line1 = Point{ x = -3.00, y = -3.00}
//            p2Line1 = Point{ x = 1.00, y = 1.00}
//            p1Line2 = Point{ x = 1.00, y = -1.00}
//            p2Line2 = Point{ x = 0.00, y = 0.00}
//            result = NOT_INTERSECT
            Point q1 = new Point(-3f, -3f);
            Point q2 = new Point(1f, 1f);
            Point w1 = new Point(1f, -1f);
            Point w2 = new Point(0f, 0f);
            assertTrue(GeometryLineLine.stateLineLine(q1, q2, w1, w2) == GeometryLineLine.IntersectState.INTERSECT);
        }
        {
//            p1Line1 = Point{ x = 1.00, y = -1.00}
//            p2Line1 = Point{ x = 2.00, y = 2.00}
//            p1Line2 = Point{ x = 1.00, y = 1.00}
//            p2Line2 = Point{ x = 0.00, y = 0.00}
//            result = INTERSECT
            Point q1 = new Point(1f, -1f);
            Point q2 = new Point(2f, 2f);
            Point w1 = new Point(1f, 1f);
            Point w2 = new Point(0f, 0f);
            assertTrue(GeometryLineLine.stateLineLine(q1, q2, w1, w2) == GeometryLineLine.IntersectState.NOT_INTERSECT);
        }
    }

    @Test
    public void testIntersect() throws Exception {
        Point[] c = new Point[3];
        c[0] = new Point(22.9187316894531f, -51.8696022033691f);
        c[1] = new Point(-3.22566747665405f, -59.6902160644531f);
        c[2] = new Point(49.6557922363281f, 58.3099060058594f);

        assertTrue(GeometryLineLine.stateLineLine(c[2], c[1], c[1], c[0]) == GeometryLineLine.IntersectState.INTERSECT_CORNER);
    }

    @Test
    public void testIntersect2() throws Exception {
        Point[] c = new Point[3];
        c[0] = new Point(0f, 0f);
        c[1] = new Point(-1f, 1f);
        c[2] = new Point(1f, -1f);
        assertTrue(GeometryLineLine.stateLineLine(c[2], c[1], c[1], c[0]) == GeometryLineLine.IntersectState.LINE_IN_LINE);
    }

    @Test
    public void testIntersect3() throws Exception {
//        INFO: 1:4:2:3NOT_INTERSECT
//        "1"	"1"	"24.6247882843018"	"-32.9209480285645"
//        "2"	"1"	"-56.3960037231445"	"-9.26701736450195"
//        "3"	"1"	"56.9165382385254"	"-20.0892524719238"
//        "4"	"1"	"-46.6352882385254"	"8.6562967300415"

        Point[] c = new Point[4];
        c[0] = new Point(24.6247882843018f, -32.9209480285645f);
        c[1] = new Point(-56.3960037231445f, -9.26701736450195f);
        c[2] = new Point(56.9165382385254f, -20.0892524719238f);
        c[3] = new Point(-46.6352882385254f, 8.6562967300415f);

        assertEquals(GeometryLineLine.stateLineLine(c[0], c[3], c[1], c[2]), GeometryLineLine.IntersectState.INTERSECT);
    }

    @Test
    public void testIntersect4() throws Exception {
        Point[] c = new Point[4];
        c[0] = new Point(0f, 0f);
        c[1] = new Point(1f, 1f);
        c[2] = new Point(-0.5f, 0.5f);
        c[3] = new Point(1f, -1f);

        assertEquals(GeometryLineLine.stateLineLine(c[0], c[1], c[2], c[3]), GeometryLineLine.IntersectState.INTERSECT_POINT_ON_LINE);
    }

    @Test
    public void testIntersect5() throws Exception {
//        middle point = Point{ x = -0.50, y = 0.50}
//        nextpoint    = Point{ x = 1.00, y = -1.00}
//        A = Point{ x = 0.00, y = 0.00}
//        B = Point{ x = 1.00, y = 1.00}
//        RESULT = NOT_INTERSECT
        Point[] c = new Point[4];
        c[0] = new Point(-0.5f, 0.5f);
        c[1] = new Point(1f, -1f);
        c[2] = new Point(0f, 0f);
        c[3] = new Point(1f, 1f);

        assertEquals(GeometryLineLine.stateLineLine(c[0], c[1], c[2], c[3]), GeometryLineLine.IntersectState.INTERSECT_POINT_ON_LINE);
    }

    @Test
    public void testIntersect6() throws Exception {
        Point[] c = new Point[4];
        c[0] = new Point(5.4f, -38.7f);
        c[1] = new Point(32.3f, -58.4f);
        c[2] = new Point(7.2f, -43f);
        c[3] = new Point(54f, 16.4f);

        assertEquals(GeometryLineLine.stateLineLine(c[0], c[1], c[2], c[3]), GeometryLineLine.IntersectState.INTERSECT);
    }



    @Test
    public void testIntersect7() throws Exception {
//        middle point A = 1
//        middle point B = 2
//        middle point = Point{ x = 0.00, y = 25.00}
//        id_nextpoint = 5
//        nextpoint    = Point{ x = -0.00, y = -50.00}
//        A = 3
//        A = Point{ x = 43.30, y = 25.00}
//        B = 4
//        B = Point{ x = 43.30, y = -25.00}
//        RESULT = NOT_INTERSECT
        Point[] c = new Point[4];
        c[0] = new Point(0f, 25f);
        c[1] = new Point(-0f, -50f);
        c[2] = new Point(43.3f, 25f);
        c[3] = new Point(43.3f, -25f);

        assertEquals(GeometryLineLine.stateLineLine(c[0], c[1], c[2], c[3]), GeometryLineLine.IntersectState.NOT_INTERSECT);
    }


    @Test
    public void testIntersect8() throws Exception {
//        middle point A = 3
//        middle point B = 1
//        middle point = Point{ x = 1.804598, y = 3.072663}
//        id_nextpoint = 4
//        nextpoint    = Point{ x = 3.000000, y = 0.000000}
//        A = 2
//        A = Point{ x = 3.472594, y = 0.222331}
//        B = 3
//        B = Point{ x = 1.998920, y = 2.706861}
//        RESULT = NOT_INTERSECT
        Point[] c = new Point[4];
        c[0] = new Point(1.804598f, 3.072663f);
        c[1] = new Point(3.000000f, 0f);
        c[2] = new Point(3.472594f, 0.2223315f);
        c[3] = new Point(1.998920f, 2.706861f);

        assertEquals(GeometryLineLine.stateLineLine(c[0], c[1], c[2], c[3]), GeometryLineLine.IntersectState.NOT_INTERSECT);
    }
}