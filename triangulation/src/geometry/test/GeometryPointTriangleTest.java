package geometry.test;

import elements.Point;
import geometry.GeometryPointTriangle;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GeometryPointTriangleTest {

    @Test
    public void testPointInTriangle1() throws Exception {
        Point nextPoint = new Point(11.47f, -156.82f);

        List<Point> list = new ArrayList<>();
        list.add(new Point(36.22f,30.57f));
        list.add(new Point(-27.65f, 35.68f));
        list.add(new Point(19.10f, -57.66f));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint,(Point[])list.toArray()),GeometryPointTriangle.PointTriangleState.POINT_OUTSIDE);
    }

    @Test
    public void testPointInTriangle2() throws Exception {
        Point nextPoint = new Point(0, 0);

        List<Point> list = new ArrayList<>();
        list.add(new Point(0,10f));
        list.add(new Point(1,-1));
        list.add(new Point(-1,-1));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint,(Point[])list.toArray()),GeometryPointTriangle.PointTriangleState.POINT_INSIDE);
    }

    @Test
    public void testPointInTriangle3() throws Exception {
        Point nextPoint = new Point(1, 0);

        List<Point> list = new ArrayList<>();
        list.add(new Point(0,1f));
        list.add(new Point(1,-1));
        list.add(new Point(-1,-1));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint,(Point[])list.toArray()),GeometryPointTriangle.PointTriangleState.POINT_OUTSIDE);
    }

    @Test
    public void testPointInTriangle4() throws Exception {
        Point nextPoint = new Point(1, 1);

        List<Point> list = new ArrayList<>();
        list.add(new Point(0,0f));
        list.add(new Point(2,2));
        list.add(new Point(2,0));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint,(Point[])list.toArray()),GeometryPointTriangle.PointTriangleState.POINT_ON_LINE);
    }

    @Test
    public void testPointInTriangle5() throws Exception {
        Point nextPoint = new Point(1, 0);

        List<Point> list = new ArrayList<>();
        list.add(new Point(0,0f));
        list.add(new Point(2,2));
        list.add(new Point(2,0));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint,(Point[])list.toArray()),GeometryPointTriangle.PointTriangleState.POINT_ON_LINE);
    }

    @Test
    public void testPointInTriangle6() throws Exception {
        Point nextPoint = new Point(2, 1);

        List<Point> list = new ArrayList<>();
        list.add(new Point(0,0f));
        list.add(new Point(2,2));
        list.add(new Point(2,0));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint,(Point[])list.toArray()),GeometryPointTriangle.PointTriangleState.POINT_ON_LINE);
    }

    @Test
    public void testPointInTriangle7() throws Exception {
        Point nextPoint = new Point(0.5, 1.5);

        List<Point> list = new ArrayList<>();
        list.add(new Point(0,0f));
        list.add(new Point(2,2));
        list.add(new Point(2,0));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint,(Point[])list.toArray()),GeometryPointTriangle.PointTriangleState.POINT_OUTSIDE);
    }
}