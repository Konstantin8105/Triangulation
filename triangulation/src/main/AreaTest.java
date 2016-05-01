package main;

import org.junit.Assert;
import org.junit.Test;
import un.api.collection.Sequence;
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
        Assert.assertEquals(Area.area(delaunay),1,0.000001);
    }

    @Test
    public void square2() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(0.0D, 0.0D));
        delaunay.insertPoint(new Point(0.0D, 1.0D));
        delaunay.insertPoint(new Point(1.0D, 0.0D));
        delaunay.insertPoint(new Point(1.0D, 1.0D));
        delaunay.insertPoint(new Point(0.5D, 0.5D));
        Assert.assertEquals(Area.area(delaunay),1,0.000001);
    }

    @Test
    public void square3() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(0.0D, 0.0D));
        delaunay.insertPoint(new Point(0.0D, 1.0D));
        delaunay.insertPoint(new Point(1.0D, 0.0D));
        delaunay.insertPoint(new Point(1.0D, 1.0D));
        int AMOUNT_ADDITION_POINTS = 100;
        Random random = new Random();
        for (int i = 0; i < AMOUNT_ADDITION_POINTS; i++) {
            delaunay.insertPoint(new Point(random.nextDouble(), random.nextDouble()));
        }
        Assert.assertEquals(Area.area(delaunay),1,0.00001);
    }

    @Test
    public void rectangle1() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(0.0D, 0.0D));
        delaunay.insertPoint(new Point(0.0D, 1.0D));
        delaunay.insertPoint(new Point(2.0D, 0.0D));
        delaunay.insertPoint(new Point(2.0D, 1.0D));
        Assert.assertEquals(Area.area(delaunay),2,0.000001);
    }

    @Test
    public void rectangle2() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(0.0D, 0.0D));
        delaunay.insertPoint(new Point(0.0D, 1.0D));
        delaunay.insertPoint(new Point(2.0D, 0.0D));
        delaunay.insertPoint(new Point(2.0D, 1.0D));
        delaunay.insertPoint(new Point(1.0D, 0.5D));
        Assert.assertEquals(Area.area(delaunay),2,0.000001);
    }

    @Test
    public void rectangle3() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(-0.5D, -0.5D));
        delaunay.insertPoint(new Point(-0.5D, +0.5D));
        delaunay.insertPoint(new Point(+0.5D, -0.5D));
        delaunay.insertPoint(new Point(+0.5D, +0.5D));
        Assert.assertEquals(Area.area(delaunay),1,0.000001);
    }

    @Test
    public void rectangle4() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(-1.5D, -0.5D));
        delaunay.insertPoint(new Point(-1.5D, +0.5D));
        delaunay.insertPoint(new Point(+1.5D, -0.5D));
        delaunay.insertPoint(new Point(+1.5D, +0.5D));
        Assert.assertEquals(Area.area(delaunay),3,0.000001);
    }

    @Test
    public void rectangle5() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(-1.5D, -5.0D));
        delaunay.insertPoint(new Point(-1.5D, +5.0D));
        delaunay.insertPoint(new Point(+1.5D, -5.0D));
        delaunay.insertPoint(new Point(+1.5D, +5.0D));
        Assert.assertEquals(Area.area(delaunay),30,0.000001);
    }
}
