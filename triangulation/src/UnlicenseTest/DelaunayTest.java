package UnlicenseTest;

//------------------------------------
// Test created for check Delaunay triangulation
// Created by: Izyumov Konstantin
//------------------------------------

import org.junit.Assert;
import org.junit.Test;
import un.api.collection.Sequence;
import un.impl.geometry.Point;
import un.impl.geometry.triangulate.Delaunay;

import java.util.Random;

public class DelaunayTest {

    @Test
    public void singleTest() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(0.0D, 0.0D));
        delaunay.insertPoint(new Point(0.0D, 1.0D));
        delaunay.insertPoint(new Point(1.0D, 0.0D));
        Sequence edges = delaunay.computeEdges();
        Assert.assertEquals(3L, (long) edges.getSize());
        Assert.assertArrayEquals(new Point[]{new Point(0.0D, 1.0D), new Point(1.0D, 0.0D)}, (Point[]) ((Point[]) edges.get(0)));
        Assert.assertArrayEquals(new Point[]{new Point(0.0D, 0.0D), new Point(0.0D, 1.0D)}, (Point[]) ((Point[]) edges.get(1)));
        Assert.assertArrayEquals(new Point[]{new Point(0.0D, 0.0D), new Point(1.0D, 0.0D)}, (Point[]) ((Point[]) edges.get(2)));
        Sequence triangles = delaunay.computeTriangles();
        Assert.assertEquals(1L, (long) triangles.getSize());
        Assert.assertArrayEquals(new Point[]{new Point(1.0D, 0.0D), new Point(0.0D, 1.0D), new Point(0.0D, 0.0D)}, (Point[]) ((Point[]) triangles.get(0)));
        Sequence voronoi = delaunay.computeVoronoi();
        Assert.assertEquals(3L, (long) voronoi.getSize());
    }

    @Test
    public void singleTest2() {
        Random random = new Random();
        Delaunay delaunay = new Delaunay();
        for (int triangles = 0; triangles < 10000; ++triangles) {
            delaunay.insertPoint(new Point(random.nextDouble(), random.nextDouble()));
        }
        Assert.assertTrue(delaunay.computeTriangles().getSize() > 0);
    }

    @Test
    public void singleTest3() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(0.0D, 0.0D));
        delaunay.insertPoint(new Point(1.0D, 0.0D));
        delaunay.insertPoint(new Point(2.0D, 0.0D));
        delaunay.insertPoint(new Point(3.0D, 0.0D));
        delaunay.insertPoint(new Point(4.0D, 0.0D));
        delaunay.insertPoint(new Point(5.0D, 0.0D));
        delaunay.insertPoint(new Point(6.0D, 0.0D));
        delaunay.insertPoint(new Point(7.0D, 0.0D));
        delaunay.insertPoint(new Point(0.0D, 1.0D));
        Sequence triangles = delaunay.computeTriangles();
        Assert.assertTrue(triangles.getSize() > 0);
    }

    @Test
    public void singleTest4() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(0.0D, 0.0D));
        Sequence triangles = delaunay.computeTriangles();
        Assert.assertTrue(triangles.getSize() == 0);
    }

    @Test
    public void singleTest5() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(0.0D, 0.0D));
        delaunay.insertPoint(new Point(1.0D, 1.0D));
        Sequence triangles = delaunay.computeTriangles();
        Assert.assertTrue(triangles.getSize() == 0);
    }

    @Test
    public void singleTest6() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(0.0D, 0.0D));
        delaunay.insertPoint(new Point(1.0D, 1.0D));
        delaunay.insertPoint(new Point(2.0D, 2.0D));
        Sequence triangles = delaunay.computeTriangles();
        Assert.assertTrue(triangles.getSize() == 0);
    }

    @Test
    public void singleTest7() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(0.0D, 0.0D));
        delaunay.insertPoint(new Point(1.0D, 1.0D));
        delaunay.insertPoint(new Point(2.0D, 2.0D));
        delaunay.insertPoint(new Point(3.0D, 3.0D));
        Sequence triangles = delaunay.computeTriangles();
        Assert.assertTrue(triangles.getSize() == 0);
    }

    //------------------------------------
    // Uncorrect for small triangle
    // Output: shortDistance = 0.001: Size of triangles = 0 ;
    // Size can not be zero
    //------------------------------------
    //TODO: Add more precision for triangulation
    @Test
    public void triangleSmall1() {
        double shortDistance = 1.0D;
        for (int i = 0; i < 10; i++) {
            shortDistance /= 10;
            Delaunay delaunay = new Delaunay();
            delaunay.insertPoint(new Point(0.0D, 0.0D));
            delaunay.insertPoint(new Point(0.0D, 1.0D));
            delaunay.insertPoint(new Point(shortDistance, 0.5D));
            System.out.println("shortDistance = " + shortDistance + ": Size of triangles = " + delaunay.computeTriangles().getSize());
            Assert.assertTrue(delaunay.computeTriangles().getSize() == 1);
        }
    }

    //------------------------------------
    // Uncorrect for small triangle
    // Output: shortDistance = 0.001: Size of triangles = 0 ;
    // Size can not be zero
    //------------------------------------
    //TODO: Add more precision for triangulation
    @Test
    public void triangleSmall2() {
        double shortDistance = 1.0D;
        for (int i = 0; i < 10; i++) {
            shortDistance /= 10;
            Delaunay delaunay = new Delaunay();
            delaunay.insertPoint(new Point(0.0D, 0.0D));
            delaunay.insertPoint(new Point(0.0D, 1.0D));
            delaunay.insertPoint(new Point(shortDistance, 0.5D));
            delaunay.insertPoint(new Point(shortDistance, -0.5D));
            System.out.println("shortDistance = " + shortDistance + ": Size of triangles = " + delaunay.computeTriangles().getSize());
            Assert.assertTrue(delaunay.computeTriangles().getSize() == 2);
        }
    }

}

