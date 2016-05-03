package core.old.geometry.test;

import old.core.elements.Coordinate;
import old.core.geometry.Geometry;
import old.core.geometry.Precisions;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GeometryTest {

    double epsilon = Precisions.epsilon();

    @Test
    public void testAngle1() throws Exception {
        Coordinate c1 = new Coordinate(0f, 0f);
        Coordinate c2 = new Coordinate(1f, 0f);
        assertEquals(Geometry.angle(c1.getX(),c1.getY(), c2.getX(), c2.getY()), 0d, epsilon);
    }

    @Test
    public void testAngle2() throws Exception {
        Coordinate c1 = new Coordinate(0f, 0f);
        Coordinate c2 = new Coordinate(0f, 1f);
        assertEquals(Geometry.angle(c1.getX(),c1.getY(), c2.getX(), c2.getY()), Math.PI / 2d, epsilon);
    }

    @Test
    public void testAngle3() throws Exception {
        Coordinate c1 = new Coordinate(0f, 0f);
        Coordinate c2 = new Coordinate(-1f, 0f);
        assertEquals(Geometry.angle(c1.getX(),c1.getY(), c2.getX(), c2.getY()), Math.PI, epsilon);
    }

    @Test
    public void testAngle4() throws Exception {
        Coordinate c1 = new Coordinate(0f, 0f);
        Coordinate c2 = new Coordinate(0f, -1f);
        Assert.assertEquals(Geometry.angle(c1.getX(),c1.getY(), c2.getX(), c2.getY()), Math.PI * 1.5d, epsilon);
    }

    @Test
    public void testAngle5() throws Exception {
        Coordinate c1 = new Coordinate(0f, 0f);
        Coordinate c2 = new Coordinate(1f, 1f);
        Assert.assertEquals(Geometry.angle(c1.getX(),c1.getY(), c2.getX(), c2.getY()), Math.toRadians(45d), epsilon);
    }

    @Test
    public void testAngle6() throws Exception {
        Coordinate c1 = new Coordinate(0f, 0f);
        Coordinate c2 = new Coordinate(-1f, 1f);
        Assert.assertEquals(Geometry.angle(c1.getX(),c1.getY(), c2.getX(), c2.getY()), Math.toRadians(135d), epsilon);
    }

    @Test
    public void testAngle7() throws Exception {
        Coordinate c1 = new Coordinate(0f, 0f);
        Coordinate c2 = new Coordinate(-1f, -1f);
        Assert.assertEquals(Geometry.angle(c1.getX(),c1.getY(), c2.getX(), c2.getY()), Math.toRadians(225d), epsilon);
    }

    @Test
    public void testAngle8() throws Exception {
        Coordinate c1 = new Coordinate(0f, 0f);
        Coordinate c2 = new Coordinate(1f, -1f);
        Assert.assertEquals(Geometry.angle(c1.getX(),c1.getY(), c2.getX(), c2.getY()), Math.toRadians(315d), epsilon);
    }

    @Test
    public void testAngleAround() throws Exception {
        Coordinate c1 = new Coordinate(0f, 0f);
        for (double i = 0; i < 360; i += 0.002) {
            double angle = Math.toRadians(i);
            Coordinate c2 = new Coordinate((float) Math.cos(angle), (float) Math.sin(angle));
            Assert.assertEquals(Geometry.angle(c1.getX(),c1.getY(), c2.getX(), c2.getY()), angle, epsilon);
        }
    }

    @Test
    public void testNormalizeAnglePositive() throws Exception {
        for (int i = 0; i < 359; i++) {
            assertEquals(Geometry.normalizeAngle(Math.toRadians(i)),Math.toRadians(i), Precisions.epsilon());
        }
    }

    @Test
    public void testNormalizeAngleNegative() throws Exception {
        for (int i = 1; i < 359; i++) {
            assertEquals(Geometry.normalizeAngle(Math.toRadians(-i)),Math.toRadians(360d-i), Precisions.epsilon());
        }
    }

    @Test
    public void testNormalizeAnglePositiveCircle() throws Exception {
        for (int i = 360; i < 500; i++) {
            assertEquals(Geometry.normalizeAngle(Math.toRadians(i)),Math.toRadians(i-360d), Precisions.epsilon());
        }
    }

    @Test
    public void testNormalizeAngleNegativeCircle() throws Exception {
        for (int i = -500; i < -361; i++) {
            assertEquals(Math.toDegrees(Geometry.normalizeAngle(Math.toRadians(i))),i+2*360d, Precisions.epsilon());
        }
    }

    @Test
    public void testAngleBetween2LineSimple() throws Exception {
        double vertexX = 0d;
        double vertexY = 0d;
        double x1 = 2d;
        double y1 = 0d;
        double x2 = 5d;
        double y2 = 5d;
        assertEquals(Geometry.angleBetween2Line(vertexX,vertexY,x1,y1,x2,y2),Math.toRadians(45d),Precisions.epsilon());
    }

    @Test
    public void testAngleBetween2LineRotate() throws Exception {
        double vertexX = 0d;
        double vertexY = 0d;
        double x1 = 2d;
        double y1 = 0d;
        double x2 = 5d;
        double y2 = 5d;
        for (int i = 0; i < 360; i++) {
            double angle = Math.toRadians(i);
            double A = Geometry.lengthBetweenPoints(vertexX,vertexY,x1,y1);
            double B = Geometry.lengthBetweenPoints(vertexX,vertexY,x2,y2);
            double a1 = Geometry.angle(vertexX,vertexY,x1,y1);
            double a2 = Geometry.angle(vertexX,vertexY,x2,y2);
            x1 = A*Math.cos(angle+a1);
            y1 = A*Math.sin(angle+a1);
            x2 = B*Math.cos(angle+a2);
            y2 = B*Math.sin(angle+a2);
            assertEquals(Geometry.angleBetween2Line(vertexX,vertexY,x1,y1,x2,y2),Math.toRadians(45d),Precisions.epsilon());
        }
    }

    @Test
    public void testLengthBetweenPoints() throws Exception {
        for (int i = 0; i < 360; i++) {
            double angle = Math.toRadians(i);
            double x = 2d;
            double y = 0d;
            double A = Geometry.lengthBetweenPoints(0,0,x,y);
            x = A*Math.cos(angle);
            y = A*Math.sin(angle);
            assertEquals(Geometry.lengthBetweenPoints(0d,0d,x,y),2d,Precisions.epsilon());
        }
    }

    @Test
    public void testIsPointInRectangle() throws Exception {
        assertTrue(Geometry.isPointInRectangle(0f,0f,-1f,-1f,1f,1f));
        assertTrue(Geometry.isPointInRectangle(-0.5f,0f,-1f,-1f,1f,1f));
        assertTrue(Geometry.isPointInRectangle(0.5f,0.5f,-1f,-1f,1f,1f));
        assertTrue(Geometry.isPointInRectangle(0.5f,-0.5f,-1f,-1f,1f,1f));
        assertTrue(Geometry.isPointInRectangle(-0.5f,0.5f,-1f,-1f,1f,1f));
        assertTrue(Geometry.isPointInRectangle(-0.5f,-0.5f,-1f,-1f,1f,1f));

        assertTrue(Geometry.isPointInRectangle(1f,1f,-1f,-1f,1f,1f));
        assertTrue(Geometry.isPointInRectangle(1f,-1f,-1f,-1f,1f,1f));
        assertTrue(Geometry.isPointInRectangle(-1f,1f,-1f,-1f,1f,1f));
        assertTrue(Geometry.isPointInRectangle(-1f,-1f,-1f,-1f,1f,1f));

        assertFalse(Geometry.isPointInRectangle(2f,2f,-1f,-1f,1f,1f));
        assertFalse(Geometry.isPointInRectangle(0f,2f,-1f,-1f,1f,1f));
        assertFalse(Geometry.isPointInRectangle(2f,0f,-1f,-1f,1f,1f));
        assertFalse(Geometry.isPointInRectangle(0f,-2f,-1f,-1f,1f,1f));
        assertFalse(Geometry.isPointInRectangle(-2f,0f,-1f,-1f,1f,1f));
    }


    @Test
    public void testAngleO1() throws Exception {
        assertEquals(Geometry.angleBetween2Line(0d, 0d, 1d, 0d, 5d, 5d), Math.PI / 4d, Precisions.epsilon());
        assertEquals(Geometry.angleBetween2Line(0d, 0d, 1d, 0d, 0d, 1d), Math.PI / 2d, Precisions.epsilon());
        assertEquals(Geometry.angleBetween2Line(0d, 0d, 1d, 0d, -1d, 0d), Math.PI, Precisions.epsilon());
        assertEquals(Geometry.angleBetween2Line(0d, 0d, 1d, 0d, 0d, -1d), Math.PI * 0.5d, Precisions.epsilon());
        assertEquals(
                Geometry.angleBetween2Line(0d, 0d, 1d, 0d, Math.cos(Math.toRadians(125)), Math.sin(Math.toRadians(125))),
                Math.toRadians(125), Precisions.epsilon());
        assertEquals(
                Geometry.angleBetween2Line(2d, 2d, 3d, 2d, 2d + Math.cos(Math.toRadians(125)), 2d + Math.sin(Math.toRadians(125))),
                Math.toRadians(125), Precisions.epsilon());
        double angle = 43;
        assertEquals(
                Geometry.angleBetween2Line(0d, 0d, 1d, 0d, Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle))),
                Math.toRadians(angle), Precisions.epsilon());
        angle = 91;
        assertEquals(
                Geometry.angleBetween2Line(0d, 0d, 1d, 0d, Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle))),
                Math.toRadians(angle), Precisions.epsilon());
        angle = 179;
        assertEquals(
                Geometry.angleBetween2Line(0d, 0d, 1d, 0d, Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle))),
                Math.toRadians(angle), Precisions.epsilon());
        angle = 181;
        assertEquals(
                Geometry.angleBetween2Line(0d, 0d, 1d, 0d, Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle))),
                Math.toRadians(179), Precisions.epsilon());

        double dx = 123.23d;
        double dy = -3.232342d;
        angle = 179;
        assertEquals(
                Geometry.angleBetween2Line(0d + dx, 0d + dy,
                        1d + dx, 0d + dy,
                        Math.cos(Math.toRadians(angle)) + dx, Math.sin(Math.toRadians(angle)) + dy),
                Math.toRadians(angle), Precisions.epsilon());

        double base_angle = 23d;
        assertEquals(
                Geometry.angleBetween2Line(dx, dy,
                        Math.cos(Math.toRadians(base_angle)) + dx, Math.sin(Math.toRadians(base_angle)) + dy,
                        Math.cos(Math.toRadians(angle + base_angle)) + dx, Math.sin(Math.toRadians(angle + base_angle)) + dy),
                Math.toRadians(angle), Precisions.epsilon());
    }

    @Test
    public void testIs3pointsOnOneLine() throws Exception {
        assertTrue(Geometry.is3pointsCollinear(0f, 0f, 1f, 1f, -1f, -1f));
        assertTrue(Geometry.is3pointsCollinear(0f, 0f, 0f, 1f, 0f, -1f));
    }
}