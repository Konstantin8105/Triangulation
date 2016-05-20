package crosssection;

import org.junit.Assert;
import org.junit.Test;
import un.impl.geometry.Point;
import un.impl.geometry.triangulate.Delaunay;

import java.util.Random;

public class AreaTest {

    @Test
    public void square1() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(0.0D, 0.0D));
        delaunay.insertPoint(new Point(0.0D, 1.0D));
        delaunay.insertPoint(new Point(1.0D, 0.0D));
        delaunay.insertPoint(new Point(1.0D, 1.0D));
        Assert.assertEquals(Area.area(delaunay.computeTriangles()), 1, 0.000001);
    }

    @Test
    public void square2() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(0.0D, 0.0D));
        delaunay.insertPoint(new Point(0.0D, 1.0D));
        delaunay.insertPoint(new Point(1.0D, 0.0D));
        delaunay.insertPoint(new Point(1.0D, 1.0D));
        delaunay.insertPoint(new Point(0.5D, 0.5D));
        Assert.assertEquals(Area.area(delaunay.computeTriangles()), 1, 0.000001);
    }

    @Test
    public void square3() {
        int AMOUNT_TESTS = 10;
        for (int i = 0; i < AMOUNT_TESTS; i++) {
            Delaunay delaunay = new Delaunay();
            delaunay.insertPoint(new Point(0.0D, 0.0D));
            delaunay.insertPoint(new Point(0.0D, 1.0D));
            delaunay.insertPoint(new Point(1.0D, 0.0D));
            delaunay.insertPoint(new Point(1.0D, 1.0D));
            int AMOUNT_ADDITION_INTERNAL_POINTS = 100;
            double GAP = 0.00005D;
            Random random = new Random();
            for (int j = 0; j < AMOUNT_ADDITION_INTERNAL_POINTS; j++) {
                delaunay.insertPoint(new Point(random.nextDouble() * (1 - 2 * GAP) + GAP, random.nextDouble() * (1 - 2 * GAP) + GAP));
            }
            Assert.assertEquals(Area.area(delaunay.computeTriangles()), 1, 0.000001);
        }
    }

    @Test
    public void rectangle1() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(0.0D, 0.0D));
        delaunay.insertPoint(new Point(0.0D, 1.0D));
        delaunay.insertPoint(new Point(2.0D, 0.0D));
        delaunay.insertPoint(new Point(2.0D, 1.0D));
        Assert.assertEquals(Area.area(delaunay.computeTriangles()), 2, 0.000001);
    }

    @Test
    public void rectangle2() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(0.0D, 0.0D));
        delaunay.insertPoint(new Point(0.0D, 1.0D));
        delaunay.insertPoint(new Point(2.0D, 0.0D));
        delaunay.insertPoint(new Point(2.0D, 1.0D));
        delaunay.insertPoint(new Point(1.0D, 0.5D));
        Assert.assertEquals(Area.area(delaunay.computeTriangles()), 2, 0.000001);
    }

    @Test
    public void rectangle3() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(-0.5D, -0.5D));
        delaunay.insertPoint(new Point(-0.5D, +0.5D));
        delaunay.insertPoint(new Point(+0.5D, -0.5D));
        delaunay.insertPoint(new Point(+0.5D, +0.5D));
        Assert.assertEquals(Area.area(delaunay.computeTriangles()), 1, 0.000001);
    }

    @Test
    public void rectangle4() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(-1.5D, -0.5D));
        delaunay.insertPoint(new Point(-1.5D, +0.5D));
        delaunay.insertPoint(new Point(+1.5D, -0.5D));
        delaunay.insertPoint(new Point(+1.5D, +0.5D));
        Assert.assertEquals(Area.area(delaunay.computeTriangles()), 3, 0.000001);
    }

    @Test
    public void rectangle5() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(-1.5D, -5.0D));
        delaunay.insertPoint(new Point(-1.5D, +5.0D));
        delaunay.insertPoint(new Point(+1.5D, -5.0D));
        delaunay.insertPoint(new Point(+1.5D, +5.0D));
        Assert.assertEquals(Area.area(delaunay.computeTriangles()), 30, 0.000001);
    }

    @Test
    public void triangleSmall() {
        double shortDistance = 1.0D;
        for (int i = 0; i < 10; i++) {
            shortDistance /= 10;
            Delaunay delaunay = new Delaunay();
            delaunay.insertPoint(new Point(0.0D, 0.0D));
            delaunay.insertPoint(new Point(0.0D, 1.0D));
            delaunay.insertPoint(new Point(shortDistance, 0.5D));
            System.out.println("shortDistance = " + shortDistance + ": Area = " + Area.area(delaunay.computeTriangles()));
            Assert.assertTrue(Area.area(delaunay.computeTriangles())>0);
        }
    }

    @Test
    public void triangleSmall2() {
        double shortDistance = 1.0D;
        for (int i = 0; i < 10; i++) {
            shortDistance /= 10;
            Delaunay delaunay = new Delaunay();
            delaunay.insertPoint(new Point(0.0D, 0.0D));
            delaunay.insertPoint(new Point(0.0D, 1.0D));
            delaunay.insertPoint(new Point(shortDistance, 0.0D));
            System.out.println("shortDistance = " + shortDistance + ": Area = " + Area.area(delaunay.computeTriangles()));
            Assert.assertTrue(Area.area(delaunay.computeTriangles())>0);
        }
    }
}
