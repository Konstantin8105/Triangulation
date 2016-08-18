package research;

import org.junit.Assert;
import org.junit.Test;
import triangulation.Triangulation;
import triangulation.elements.Point;
import triangulationAdvance.TriangulationAdvance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTest {

    final private boolean PRINTABLE = true;
    final private int AMOUNT_POINTS = 5;
    private static Random random = new Random();

    @Test
    public void randomTest() throws Exception {
        List<Point> list = new ArrayList<>();
        for (int i = 0; i < 20000; i++) {
            for (int j = 0; j < AMOUNT_POINTS; j++) {
                list.add(new Point((random.nextFloat()) * 5d, (random.nextFloat()) * 5d));
            }
            Triangulation triangulation = new Triangulation(list);
            if (PRINTABLE) {
                System.out.println("\n*********");
                System.out.println("RandomTest #" + i);
                for (Point aList : list) {
                    System.out.println(String.format("\"%+5.8f\"", aList.getX()) + "," + String.format("\"%+5.8f\",", aList.getY()));
                }
                System.out.println("Amount of triangles = " + triangulation.getTriangles().size());
            }
            Assert.assertTrue(triangulation.getTriangles().toString(), triangulation.getTriangles().size() > 0);
            list.clear();
        }
    }

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
    public void test() {
        Point[] points = new Point[]{
                new Point(48, 32),
                new Point(48, 36),
                new Point(21, 27),
                new Point(32, 42),
                new Point(28, 30),
        };
        TriangulationAdvance advance = new TriangulationAdvance(points);
        Assert.assertTrue(advance.getTriangles().size() > 0);
    }

}