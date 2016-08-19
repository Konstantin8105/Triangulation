package triangulationAdvance;

import org.junit.Assert;
import org.junit.Test;
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
                new Point(19, 45),
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
                new Point(33, 34),
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
                new Point(35, 36),
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
                new Point(20, 11),
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }
}
