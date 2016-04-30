package geometry.test;

import com.home.fgd.stack.triangulation.elements.Coordinate;
import com.home.fgd.stack.triangulation.geometry.GeometryPointTriangle;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GeometryPointTriangleTest {

    @Test
    public void testPointInTriangle1() throws Exception {
        Coordinate nextPoint = new Coordinate(11.47f, -156.82f);

        List<Coordinate> list = new ArrayList<>();
        list.add(new Coordinate(36.22f,30.57f));
        list.add(new Coordinate(-27.65f, 35.68f));
        list.add(new Coordinate(19.10f, -57.66f));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint,list),GeometryPointTriangle.PointTriangleState.POINT_OUTSIDE);
    }

    @Test
    public void testPointInTriangle2() throws Exception {
        Coordinate nextPoint = new Coordinate(0, 0);

        List<Coordinate> list = new ArrayList<>();
        list.add(new Coordinate(0,10f));
        list.add(new Coordinate(1,-1));
        list.add(new Coordinate(-1,-1));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint,list),GeometryPointTriangle.PointTriangleState.POINT_INSIDE);
    }

    @Test
    public void testPointInTriangle3() throws Exception {
        Coordinate nextPoint = new Coordinate(1, 0);

        List<Coordinate> list = new ArrayList<>();
        list.add(new Coordinate(0,1f));
        list.add(new Coordinate(1,-1));
        list.add(new Coordinate(-1,-1));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint,list),GeometryPointTriangle.PointTriangleState.POINT_OUTSIDE);
    }

    @Test
    public void testPointInTriangle4() throws Exception {
        Coordinate nextPoint = new Coordinate(1, 1);

        List<Coordinate> list = new ArrayList<>();
        list.add(new Coordinate(0,0f));
        list.add(new Coordinate(2,2));
        list.add(new Coordinate(2,0));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint,list),GeometryPointTriangle.PointTriangleState.POINT_ON_LINE);
    }

    @Test
    public void testPointInTriangle5() throws Exception {
        Coordinate nextPoint = new Coordinate(1, 0);

        List<Coordinate> list = new ArrayList<>();
        list.add(new Coordinate(0,0f));
        list.add(new Coordinate(2,2));
        list.add(new Coordinate(2,0));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint,list),GeometryPointTriangle.PointTriangleState.POINT_ON_LINE);
    }

    @Test
    public void testPointInTriangle6() throws Exception {
        Coordinate nextPoint = new Coordinate(2, 1);

        List<Coordinate> list = new ArrayList<>();
        list.add(new Coordinate(0,0f));
        list.add(new Coordinate(2,2));
        list.add(new Coordinate(2,0));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint,list),GeometryPointTriangle.PointTriangleState.POINT_ON_LINE);
    }

    @Test
    public void testPointInTriangle7() throws Exception {
        Coordinate nextPoint = new Coordinate(0.5, 1.5);

        List<Coordinate> list = new ArrayList<>();
        list.add(new Coordinate(0,0f));
        list.add(new Coordinate(2,2));
        list.add(new Coordinate(2,0));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint,list),GeometryPointTriangle.PointTriangleState.POINT_OUTSIDE);
    }
}