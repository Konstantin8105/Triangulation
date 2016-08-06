package unlicenseTest;

//------------------------------------
// Test created for check Delaunay triangulation
// Created by: Izyumov Konstantin
//------------------------------------

//import un.api.collection.Sequence;
//import un.impl.geometry.Point;
//import un.impl.geometry.triangulate.Delaunay;

public class DelaunayTest {
/*

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
    // Incorrectly for small triangle
    // Output: shortDistance = 0.001: Size of triangles = 0 ;
    // Size can not be zero
    //------------------------------------
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
    // Incorrectly for small triangle
    // Output: shortDistance = 0.001: Size of triangles = 0 ;
    // Size can not be zero
    //------------------------------------
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
*/

}

