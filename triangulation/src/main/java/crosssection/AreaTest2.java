package crosssection;

import org.junit.Assert;
import org.junit.Test;
import triangulation.Triangulation;
import triangulation.elements.Point;
import triangulation.geometry.Precisions;

import java.util.Arrays;

public class AreaTest2 {

    @Test
    public void square1() throws Exception {
        Point[] points = new Point[]{
                new Point(0.0D, 0.0D),
                new Point(1.0D, 0.0D),
                new Point(1.0D, 1.0D)
        };
        Triangulation triangulation = new Triangulation(Arrays.asList(points));
        Assert.assertEquals(Area.area(triangulation.getMesh()), 0.5, Precisions.epsilon());
    }

    @Test
    public void square2() throws Exception {
        Point[] points = new Point[]{
                new Point(0.0D, 0.0D),
                new Point(1.0D, 0.0D),
                new Point(0.0D, 1.0D),
                new Point(1.0D, 1.0D)
        };
        Triangulation triangulation = new Triangulation(Arrays.asList(points));
        Assert.assertEquals(Area.area(triangulation.getMesh()), 1.0, Precisions.epsilon());
    }

    @Test
    public void square3() throws Exception {
        Point[] points = new Point[]{
                new Point(0.0D, 0.0D),
                new Point(1.0D, 0.0D),
                new Point(0.0D, 1.0D),
                new Point(0.5D, 0.5D),
                new Point(1.0D, 1.0D)
        };
        Triangulation triangulation = new Triangulation(Arrays.asList(points));
        Assert.assertEquals(Area.area(triangulation.getMesh()), 1.0, Precisions.epsilon());
    }

    @Test
    public void triangleSmall1() throws Exception {
        double shortDistance = 1.0D;
        for (int i = 0; i < 10; i++) {
            shortDistance /= 10;
            Point[] points = new Point[]{
                    new Point(0.0D, 0.0D),
                    new Point(1.0D, 1.0D),
                    new Point(shortDistance + 0.5D, 0.5D)
            };
            Triangulation triangulation = new Triangulation(Arrays.asList(points));
            System.out.println("shortDistance = " + String.format("%.2e", shortDistance) + ": Area = " + String.format("%.2e", Area.area(triangulation.getMesh())));

            if (shortDistance / Math.sqrt(2) > Precisions.epsilon()) {
                Assert.assertTrue(Area.area(triangulation.getMesh()) > 0);
            }
        }
    }

    @Test
    public void triangleSmall2() throws Exception {
        double longDistance = 1.0D;
        for (int i = 0; i < 30; i++) {
            longDistance *= 10;
            Point[] points = new Point[]{
                    new Point(0.0D, 0.0D),
                    new Point(1.0D, 1.0D),
                    new Point(longDistance + 0.5D, 0.5D)
            };
            Triangulation triangulation = new Triangulation(Arrays.asList(points));
            System.out.println("longDistance = " + String.format("%.2e", longDistance) + ": Area = " + String.format("%.2e", Area.area(triangulation.getMesh())));

            if (Math.sqrt(2) / longDistance > Precisions.epsilon()) {
                Assert.assertTrue(Area.area(triangulation.getMesh()) > 0);
            }
        }
    }
}
