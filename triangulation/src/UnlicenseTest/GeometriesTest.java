package UnlicenseTest;

import org.junit.Assert;
import org.junit.Test;
import un.impl.geometry.Geometries;
import un.impl.geometry.Point;

public class GeometriesTest {

    @Test
    public void testTrue1() {
        Point point1 = new Point(0.0D, 0.0D);
        Point point2 = new Point(1.0D, 0.0D);
        Point point3 = new Point(2.0D, 0.0D);
        Assert.assertTrue(Geometries.isOnLine(point1, point2, point3));
    }

    @Test
    public void testTrue2() {
        double width = Math.PI;
        for (int i = 0; i < 300; i++) {
            Point point1 = new Point(0.0D, 0.0D);
            Point point2 = new Point(width * 0.5D, 0.000D);
            Point point3 = new Point(width, 0.0D);
            Assert.assertTrue(Geometries.isOnLine(point1, point2, point3));
            width *= 10D;
        }
    }

    @Test
    public void testFalse1() {
        Point point1 = new Point(0.0D, 1.0D);
        Point point2 = new Point(1.0D, 0.0D);
        Point point3 = new Point(2.0D, 0.0D);
        Assert.assertFalse(Geometries.isOnLine(point1, point2, point3));
    }

    @Test
    public void testFalse2() {
        Point point1 = new Point(0.0D, 0.0D);
        Point point2 = new Point(1.0D, 1.0D);
        Point point3 = new Point(2.0D, 0.0D);
        Assert.assertFalse(Geometries.isOnLine(point1, point2, point3));
    }

    @Test
    public void testFalse3() {
        Point point1 = new Point(0.0D, 0.0D);
        Point point2 = new Point(1.0D, 0.0D);
        Point point3 = new Point(2.0D, 1.0D);
        Assert.assertFalse(Geometries.isOnLine(point1, point2, point3));
    }

    @Test
    public void testFalse4() {
        double width = Math.PI;
        for (int i = 0; i < 300; i++) {
            Point point1 = new Point(0.0D, 0.0D);
            Point point2 = new Point(width * 0.5D, 1e-5);
            Point point3 = new Point(width, 0.0D);
            Assert.assertFalse(Geometries.isOnLine(point1, point2, point3));
            width *= 10D;
        }
    }
}
