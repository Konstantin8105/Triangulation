package main;

import java.util.Random;
import org.junit.Assert;
import org.junit.Test;
import un.api.collection.Sequence;
import un.impl.geometry.Point;
import un.impl.geometry.triangulate.Delaunay;

public class DelaunayTest {
    public DelaunayTest() {
    }

    @Test
    public void singleTest() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(0.0D, 0.0D));
        delaunay.insertPoint(new Point(0.0D, 1.0D));
        delaunay.insertPoint(new Point(1.0D, 0.0D));
        Sequence edges = delaunay.computeEdges();
        Assert.assertEquals(3L, (long)edges.getSize());
        Assert.assertArrayEquals(new Point[]{new Point(0.0D, 1.0D), new Point(1.0D, 0.0D)}, (Point[])((Point[])edges.get(0)));
        Assert.assertArrayEquals(new Point[]{new Point(0.0D, 0.0D), new Point(0.0D, 1.0D)}, (Point[])((Point[])edges.get(1)));
        Assert.assertArrayEquals(new Point[]{new Point(0.0D, 0.0D), new Point(1.0D, 0.0D)}, (Point[])((Point[])edges.get(2)));
        Sequence triangles = delaunay.computeTriangles();
        Assert.assertEquals(1L, (long)triangles.getSize());
        Assert.assertArrayEquals(new Point[]{new Point(1.0D, 0.0D), new Point(0.0D, 1.0D), new Point(0.0D, 0.0D)}, (Point[])((Point[])triangles.get(0)));
        Sequence voronoi = delaunay.computeVoronoi();
        Assert.assertEquals(3L, (long)voronoi.getSize());
    }

    @Test
    public void singleTest2() {
        Random random = new Random();
        Delaunay delaunay = new Delaunay();

        for(int triangles = 0; triangles < 10000; ++triangles) {
            delaunay.insertPoint(new Point(random.nextDouble(), random.nextDouble()));
        }

        Sequence var4 = delaunay.computeTriangles();
        Assert.assertTrue(var4.getSize() > 0);
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
        Sequence edges = delaunay.computeEdges();
        Sequence triangles = delaunay.computeTriangles();
        Assert.assertTrue(triangles.getSize() > 0);
    }

    @Test
    public void singleTest4() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(0.0D, 0.0D));
        Sequence edges = delaunay.computeEdges();
        Sequence triangles = delaunay.computeTriangles();
        Assert.assertTrue(triangles.getSize() == 0);
        System.out.println(triangles);
    }

    @Test
    public void singleTest5() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(0.0D, 0.0D));
        delaunay.insertPoint(new Point(1.0D, 1.0D));
        Sequence edges = delaunay.computeEdges();
        Sequence triangles = delaunay.computeTriangles();
        Assert.assertTrue(triangles.getSize() == 0);
        System.out.println(triangles);
    }

    @Test
    public void singleTest6() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(0.0D, 0.0D));
        delaunay.insertPoint(new Point(1.0D, 1.0D));
        delaunay.insertPoint(new Point(2.0D, 2.0D));
        Sequence edges = delaunay.computeEdges();
        Sequence triangles = delaunay.computeTriangles();
        Assert.assertTrue(triangles.getSize() == 0);
        System.out.println(triangles);
    }

    @Test
    public void singleTest7() {
        Delaunay delaunay = new Delaunay();
        delaunay.insertPoint(new Point(0.0D, 0.0D));
        delaunay.insertPoint(new Point(1.0D, 1.0D));
        delaunay.insertPoint(new Point(2.0D, 2.0D));
        delaunay.insertPoint(new Point(3.0D, 3.0D));
        Sequence edges = delaunay.computeEdges();
        Sequence triangles = delaunay.computeTriangles();
        Assert.assertTrue(triangles.getSize() == 0);
        System.out.println(triangles);
    }
}

