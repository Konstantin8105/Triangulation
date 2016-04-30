package geometry.test;

import com.home.fgd.stack.triangulation.elements.Coordinate;
import com.home.fgd.stack.triangulation.geometry.GeometryLineLine;
import org.junit.Test;

import static org.junit.Assert.*;

public class GeometryLineLineTest {

    @Test
    public void testIsLinesIntersect() throws Exception {
        Coordinate c1 = new Coordinate(0f, 2f);
        Coordinate c2 = new Coordinate(2f, 0f);
        Coordinate a1 = new Coordinate(-5f, -5f);
        Coordinate a2 = new Coordinate(10f, 10f);
        assertTrue(GeometryLineLine.stateLineLine(c1, c2, a1, a2) == GeometryLineLine.IntersectState.INTERSECT);
        {
            Coordinate q1 = new Coordinate(5f, 5f);
            Coordinate q2 = new Coordinate(10f, 10f);
            assertTrue(GeometryLineLine.stateLineLine(c1, c2, q1, q2) == GeometryLineLine.IntersectState.NOT_INTERSECT);
        }
        {
            Coordinate q1 = new Coordinate(5f, 5f);
            Coordinate q2 = new Coordinate(10f, 10f);
            assertTrue(GeometryLineLine.stateLineLine(c1, c2, q1, q2) == GeometryLineLine.IntersectState.NOT_INTERSECT);
        }
        {
            Coordinate q1 = new Coordinate(0f, 0f);
            Coordinate q2 = new Coordinate(1f, 1f);
            assertTrue(GeometryLineLine.stateLineLine(c1, c2, q1, q2) == GeometryLineLine.IntersectState.INTERSECT);
        }
        {
            Coordinate q1 = new Coordinate(0f, 2f);
            Coordinate q2 = new Coordinate(12f, 12f);
            assertTrue(GeometryLineLine.stateLineLine(c1, c2, q1, q2) == GeometryLineLine.IntersectState.INTERSECT_CORNER);
        }
        {
            Coordinate q1 = new Coordinate(0.5f, 1.5f);
            Coordinate q2 = new Coordinate(1.5f, 0.5f);
            assertTrue(GeometryLineLine.stateLineLine(c1, c2, q1, q2) == GeometryLineLine.IntersectState.LINE_IN_LINE);
        }
        {
            Coordinate q1 = new Coordinate(5f, 5f);
            Coordinate q2 = new Coordinate(0f, 0f);
            Coordinate w1 = new Coordinate(1f, 0f);
            Coordinate w2 = new Coordinate(4f, 4f);
            assertTrue(GeometryLineLine.stateLineLine(q1, q2, w1, w2) == GeometryLineLine.IntersectState.INTERSECT);
        }
        {
//            p1Line1 = Coordinate{ x = 4.00, y = 4.00}
//            p2Line1 = Coordinate{ x = 0.00, y = 0.00}
//            p1Line2 = Coordinate{ x = 4.00, y = 4.00}
//            p2Line2 = Coordinate{ x = 5.00, y = 5.00}
//            result = LINE_IN_LINE
            Coordinate q1 = new Coordinate(4f, 4f);
            Coordinate q2 = new Coordinate(0f, 0f);
            Coordinate w1 = new Coordinate(4f, 4f);
            Coordinate w2 = new Coordinate(5f, 5f);
            assertTrue(GeometryLineLine.stateLineLine(q1, q2, w1, w2) == GeometryLineLine.IntersectState.INTERSECT_CORNER);
        }
        {
//            p1Line1 = Coordinate{ x = -3.00, y = -3.00}
//            p2Line1 = Coordinate{ x = 1.00, y = 1.00}
//            p1Line2 = Coordinate{ x = 1.00, y = -1.00}
//            p2Line2 = Coordinate{ x = 0.00, y = 0.00}
//            result = NOT_INTERSECT
            Coordinate q1 = new Coordinate(-3f, -3f);
            Coordinate q2 = new Coordinate(1f, 1f);
            Coordinate w1 = new Coordinate(1f, -1f);
            Coordinate w2 = new Coordinate(0f, 0f);
            assertTrue(GeometryLineLine.stateLineLine(q1, q2, w1, w2) == GeometryLineLine.IntersectState.INTERSECT);
        }
        {
//            p1Line1 = Coordinate{ x = 1.00, y = -1.00}
//            p2Line1 = Coordinate{ x = 2.00, y = 2.00}
//            p1Line2 = Coordinate{ x = 1.00, y = 1.00}
//            p2Line2 = Coordinate{ x = 0.00, y = 0.00}
//            result = INTERSECT
            Coordinate q1 = new Coordinate(1f, -1f);
            Coordinate q2 = new Coordinate(2f, 2f);
            Coordinate w1 = new Coordinate(1f, 1f);
            Coordinate w2 = new Coordinate(0f, 0f);
            assertTrue(GeometryLineLine.stateLineLine(q1, q2, w1, w2) == GeometryLineLine.IntersectState.NOT_INTERSECT);
        }
    }

    @Test
    public void testIntersect() throws Exception {
        Coordinate[] c = new Coordinate[3];
        c[0] = new Coordinate(22.9187316894531f, -51.8696022033691f);
        c[1] = new Coordinate(-3.22566747665405f, -59.6902160644531f);
        c[2] = new Coordinate(49.6557922363281f, 58.3099060058594f);

        assertTrue(GeometryLineLine.stateLineLine(c[2], c[1], c[1], c[0]) == GeometryLineLine.IntersectState.INTERSECT_CORNER);
    }

    @Test
    public void testIntersect2() throws Exception {
        Coordinate[] c = new Coordinate[3];
        c[0] = new Coordinate(0f, 0f);
        c[1] = new Coordinate(-1f, 1f);
        c[2] = new Coordinate(1f, -1f);
        assertTrue(GeometryLineLine.stateLineLine(c[2], c[1], c[1], c[0]) == GeometryLineLine.IntersectState.LINE_IN_LINE);
    }

    @Test
    public void testIntersect3() throws Exception {
//        INFO: 1:4:2:3NOT_INTERSECT
//        "1"	"1"	"24.6247882843018"	"-32.9209480285645"
//        "2"	"1"	"-56.3960037231445"	"-9.26701736450195"
//        "3"	"1"	"56.9165382385254"	"-20.0892524719238"
//        "4"	"1"	"-46.6352882385254"	"8.6562967300415"

        Coordinate[] c = new Coordinate[4];
        c[0] = new Coordinate(24.6247882843018f, -32.9209480285645f);
        c[1] = new Coordinate(-56.3960037231445f, -9.26701736450195f);
        c[2] = new Coordinate(56.9165382385254f, -20.0892524719238f);
        c[3] = new Coordinate(-46.6352882385254f, 8.6562967300415f);

        assertEquals(GeometryLineLine.stateLineLine(c[0], c[3], c[1], c[2]), GeometryLineLine.IntersectState.INTERSECT);
    }

    @Test
    public void testIntersect4() throws Exception {
        Coordinate[] c = new Coordinate[4];
        c[0] = new Coordinate(0f, 0f);
        c[1] = new Coordinate(1f, 1f);
        c[2] = new Coordinate(-0.5f, 0.5f);
        c[3] = new Coordinate(1f, -1f);

        assertEquals(GeometryLineLine.stateLineLine(c[0], c[1], c[2], c[3]), GeometryLineLine.IntersectState.INTERSECT_POINT_ON_LINE);
    }

    @Test
    public void testIntersect5() throws Exception {
//        middle point = Coordinate{ x = -0.50, y = 0.50}
//        nextpoint    = Coordinate{ x = 1.00, y = -1.00}
//        A = Coordinate{ x = 0.00, y = 0.00}
//        B = Coordinate{ x = 1.00, y = 1.00}
//        RESULT = NOT_INTERSECT
        Coordinate[] c = new Coordinate[4];
        c[0] = new Coordinate(-0.5f, 0.5f);
        c[1] = new Coordinate(1f, -1f);
        c[2] = new Coordinate(0f, 0f);
        c[3] = new Coordinate(1f, 1f);

        assertEquals(GeometryLineLine.stateLineLine(c[0], c[1], c[2], c[3]), GeometryLineLine.IntersectState.INTERSECT_POINT_ON_LINE);
    }

    @Test
    public void testIntersect6() throws Exception {
        Coordinate[] c = new Coordinate[4];
        c[0] = new Coordinate(5.4f, -38.7f);
        c[1] = new Coordinate(32.3f, -58.4f);
        c[2] = new Coordinate(7.2f, -43f);
        c[3] = new Coordinate(54f, 16.4f);

        assertEquals(GeometryLineLine.stateLineLine(c[0], c[1], c[2], c[3]), GeometryLineLine.IntersectState.INTERSECT);
    }



    @Test
    public void testIntersect7() throws Exception {
//        middle point A = 1
//        middle point B = 2
//        middle point = Coordinate{ x = 0.00, y = 25.00}
//        id_nextpoint = 5
//        nextpoint    = Coordinate{ x = -0.00, y = -50.00}
//        A = 3
//        A = Coordinate{ x = 43.30, y = 25.00}
//        B = 4
//        B = Coordinate{ x = 43.30, y = -25.00}
//        RESULT = NOT_INTERSECT
        Coordinate[] c = new Coordinate[4];
        c[0] = new Coordinate(0f, 25f);
        c[1] = new Coordinate(-0f, -50f);
        c[2] = new Coordinate(43.3f, 25f);
        c[3] = new Coordinate(43.3f, -25f);

        assertEquals(GeometryLineLine.stateLineLine(c[0], c[1], c[2], c[3]), GeometryLineLine.IntersectState.NOT_INTERSECT);
    }


    @Test
    public void testIntersect8() throws Exception {
//        middle point A = 3
//        middle point B = 1
//        middle point = Coordinate{ x = 1.804598, y = 3.072663}
//        id_nextpoint = 4
//        nextpoint    = Coordinate{ x = 3.000000, y = 0.000000}
//        A = 2
//        A = Coordinate{ x = 3.472594, y = 0.222331}
//        B = 3
//        B = Coordinate{ x = 1.998920, y = 2.706861}
//        RESULT = NOT_INTERSECT
        Coordinate[] c = new Coordinate[4];
        c[0] = new Coordinate(1.804598f, 3.072663f);
        c[1] = new Coordinate(3.000000f, 0f);
        c[2] = new Coordinate(3.472594f, 0.2223315f);
        c[3] = new Coordinate(1.998920f, 2.706861f);

        assertEquals(GeometryLineLine.stateLineLine(c[0], c[1], c[2], c[3]), GeometryLineLine.IntersectState.NOT_INTERSECT);
    }
}