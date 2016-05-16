import org.junit.Assert;
import org.junit.Test;
import triangulation.elements.Point;
import triangulation.geometry.GeometryPointTriangle;

public class GeometryPointTriangleTest {

    @Test
    public void testPointInTriangle1() throws Exception {
        Point nextPoint = new Point(11.47, -156.82);

        Point[] list = new Point[3];
        list[0] = (new Point(36.22, 30.57));
        list[1] = (new Point(-27.65, 35.68));
        list[2] = (new Point(19.10, -57.66));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint, list),
                GeometryPointTriangle.PointTriangleState.POINT_OUTSIDE);
    }

    @Test
    public void testPointInTriangle2() throws Exception {
        Point nextPoint = new Point(0, 0);

        Point[] list = new Point[3];
        list[0] = (new Point(0, 10));
        list[1] = (new Point(1, -1));
        list[2] = (new Point(-1, -1));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint, list),
                GeometryPointTriangle.PointTriangleState.POINT_INSIDE);
    }

    @Test
    public void testPointInTriangle3() throws Exception {
        Point nextPoint = new Point(1, 0);

        Point[] list = new Point[3];
        list[0] = (new Point(0, 1));
        list[1] = (new Point(1, -1));
        list[2] = (new Point(-1, -1));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint, list),
                GeometryPointTriangle.PointTriangleState.POINT_OUTSIDE);
    }


    @Test
    public void testPointInTriangle4() throws Exception {
        Point nextPoint = new Point(1, 1);

        Point[] list = new Point[3];
        list[0] = (new Point(0, 0));
        list[1] = (new Point(2, 2));
        list[2] = (new Point(2, 0));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint, list),
                GeometryPointTriangle.PointTriangleState.POINT_ON_LINE_0);
    }

    @Test
    public void testPointInTriangle5() throws Exception {
        Point nextPoint = new Point(2, 1);

        Point[] list = new Point[3];
        list[0] = (new Point(0, 0));
        list[1] = (new Point(2, 2));
        list[2] = (new Point(2, 0));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint, list),
                GeometryPointTriangle.PointTriangleState.POINT_ON_LINE_1);
    }

    @Test
    public void testPointInTriangle6() throws Exception {
        Point nextPoint = new Point(1, 0);

        Point[] list = new Point[3];
        list[0] = (new Point(0, 0));
        list[1] = (new Point(2, 2));
        list[2] = (new Point(2, 0));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint, list),
                GeometryPointTriangle.PointTriangleState.POINT_ON_LINE_2);
    }


    @Test
    public void testPointInTriangle7() throws Exception {
        Point nextPoint = new Point(0.5, 1.5);

        Point[] list = new Point[3];
        list[0] = (new Point(0, 0));
        list[1] = (new Point(2, 2));
        list[2] = (new Point(2, 0));

        Assert.assertEquals(GeometryPointTriangle.isPointInTriangle(nextPoint, list),
                GeometryPointTriangle.PointTriangleState.POINT_OUTSIDE);
    }
}