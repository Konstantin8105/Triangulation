package triangulationAdvance;

import org.junit.Assert;
import org.junit.Test;
import research.ResearchTest;
import triangulation.elements.Point;

import java.util.Random;

public class TriangulationAdvanceTest {

    final private boolean PRINTABLE = true;
    final private int AMOUNT_POINTS = 5;
    private static Random random = new Random();

    @Test
    public void randomTestAdvance() throws Exception {
        for (int i = 0; i < 20000; i++) {
            Point points[] = new Point[AMOUNT_POINTS];
            for (int j = 0; j < points.length; j++) {
                points[j] = new Point(random.nextInt(50), random.nextInt(50));
            }
            if (PRINTABLE) {
                System.out.println("\n*********");
                System.out.println("RandomTest #" + i);
                for (Point aList : points) {
                    System.out.println(String.format("\"%+5.8f\"", aList.getX()) + "," + String.format("\"%+5.8f\",", aList.getY()));
                }
            }
            TriangulationAdvance triangulation = new TriangulationAdvance(points);
            if (PRINTABLE) {
                System.out.println("Amount of triangles = " + triangulation.getTriangles().size());
            }
            Assert.assertTrue(points.toString(), triangulation.getTriangles().size() > 0);
        }
    }


    @Test
    public void simpleTest1() throws Exception {
        Point[] points = new Point[]{
                new Point(48, 47),
                new Point(39, 0),
                new Point(0, 1),
                new Point(10, 7),
                new Point(19, 45)
        };

        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest2() throws Exception {
        Point[] points = new Point[]{
                new Point(35, 22),
                new Point(20, 40),
                new Point(40, 16),
                new Point(24, 29),
                new Point(33, 34)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest3() throws Exception {
        Point[] points = new Point[]{
                new Point(33, 11),
                new Point(25, 25),
                new Point(37, 4),
                new Point(29, 22),
                new Point(35, 36)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest4() throws Exception {
        Point[] points = new Point[]{
                new Point(8, 34),
                new Point(11, 35),
                new Point(44, 46),
                new Point(4, 42),
                new Point(20, 11)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest5() throws Exception {
        Point[] points = new Point[]{
                new Point(31, 24),
                new Point(0, 0),
                new Point(33, 4),
                new Point(28, 36),
                new Point(43, 23)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest6() throws Exception {
        Point[] points = new Point[]{
                new Point(11, 24),
                new Point(16, 16),
                new Point(1, 1),
                new Point(44, 44),
                new Point(8, 21)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest7() throws Exception {
        Point[] points = new Point[]{
                new Point(24, 0),
                new Point(22, 9),
                new Point(19, 44),
                new Point(1, 12),
                new Point(6, 3)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest8() throws Exception {
        Point[] points = new Point[]{
                new Point(0, 34),
                new Point(10, 48),
                new Point(48, 46),
                new Point(0, 46),
                new Point(11, 32)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest9() throws Exception {
        Point[] points = new Point[]{
                new Point(34, 13),
                new Point(37, 45),
                new Point(23, 49),
                new Point(24, 16)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest10() throws Exception {
        Point[] points = new Point[]{
                new Point(-18.45, -18.45),
                new Point(-20.5, 72.05),
                new Point(65.5, -20.5),
                new Point(11, 24),
                new Point(16, 16),
                new Point(1, 1),
                new Point(44, 44),
                new Point(8, 21),
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest11() throws Exception {
        Point[] points = new Point[]{
                new Point(20, 6),
                new Point(6, 11),
                new Point(24, 7),
                new Point(22, 9),
                new Point(9, 22)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest12() throws Exception {
        Point[] points = new Point[]{
                new Point(1, 37),
                new Point(31, 20),
                new Point(31, 24),
                new Point(31, 5),
                new Point(14, 16)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest13() throws Exception {
        Point[] points = new Point[]{
                new Point(36, 8),
                new Point(47, 43),
                new Point(36, 37),
                new Point(27, 17),
                new Point(35, 26)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleCircle() throws Exception {
        Point[] points = (Point[]) ResearchTest.getCirclePoints(6).toArray();
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleInTriangle() throws Exception {
        Point[] points = (Point[]) ResearchTest.getInTriangles(10).toArray();
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

}
