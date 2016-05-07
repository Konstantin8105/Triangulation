package crosssection;

import triangulation.elements.Point;
import org.junit.Assert;
import org.junit.Test;
import triangulation.Triangulation;

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
        Assert.assertEquals(Area.area(triangulation.getMesh()), 0.5, 0.000001);
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
        Assert.assertEquals(Area.area(triangulation.getMesh()), 1.0, 0.000001);
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
        Assert.assertEquals(Area.area(triangulation.getMesh()), 1.0, 0.000001);
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
            Assert.assertTrue(Area.area(triangulation.getMesh()) > 0);
        }
    }

    @Test
    public void triangleSmall2() throws Exception {
        double shortDistance = 1.0D;
        for (int i = 0; i < 30; i++) {
            shortDistance *= 10;
            Point[] points = new Point[]{
                    new Point(0.0D, 0.0D),
                    new Point(1.0D, 1.0D),
                    new Point(shortDistance + 0.5D, 0.5D)
            };
            Triangulation triangulation = new Triangulation(Arrays.asList(points));
            System.out.println("shortDistance = " + String.format("%.2e", shortDistance) + ": Area = " + String.format("%.2e", Area.area(triangulation.getMesh())));
            Assert.assertTrue(Area.area(triangulation.getMesh()) > 0);
        }
    }

    @Test
    public void triangleSmall3() throws Exception {
        double shortDistance = 1.0D;
        for (int i = 0; i < 30; i++) {
            shortDistance *= 10;
            Point[] points = new Point[]{
                    new Point(0.0D, 0.0D),
                    new Point(shortDistance, shortDistance),
                    new Point(shortDistance*0.5D+1.0D, shortDistance*0.5D)
            };
            Triangulation triangulation = new Triangulation(Arrays.asList(points));
            System.out.println("shortDistance = " + String.format("%.2e", shortDistance) + ": Area = " + String.format("%.2e", Area.area(triangulation.getMesh())));
            Assert.assertTrue(Area.area(triangulation.getMesh()) > 0);
        }
    }
}
