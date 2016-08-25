import org.junit.Assert;
import org.junit.Test;
import research.ResearchTest;
import triangulationAdvance.Point;
import triangulationAdvance.TriangulationAdvance;

import java.util.Random;

public class TriangulationAdvanceTest {

    final private boolean PRINTABLE = false;
    final private int AMOUNT_POINTS = 5;
    final private static Random random = new Random();

    @Test
    public void randomTestAdvance() {
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
    public void simpleTest1() {
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
    public void simpleTest2() {
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
    public void simpleTest3() {
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
    public void simpleTest4() {
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
    public void simpleTest5() {
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
    public void simpleTest6() {
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
    public void simpleTest7() {
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
    public void simpleTest8() {
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
    public void simpleTest9() {
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
    public void simpleTest10() {
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
    public void simpleTest11() {
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
    public void simpleTest12() {
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
    public void simpleTest13() {
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
    public void simpleTest14() {
        Point[] points = new Point[]{
                new Point(0, 34),
                new Point(10, 48),
                new Point(48, 46)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest15() {
        Point[] points = new Point[]{
                new Point(28, 17),
                new Point(39, 24),
                new Point(45, 25),
                new Point(7, 38),
                new Point(16, 0)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest16() {
        Point[] points = new Point[]{
                new Point(27, 32),
                new Point(27, 1),
                new Point(3, 37),
                new Point(27, 28),
                new Point(44, 14)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest17() {
        Point[] points = new Point[]{
                new Point(20, 38),
                new Point(34, 17),
                new Point(16, 8),
                new Point(43, 2),
                new Point(25, 47)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest18() {
        Point[] points = new Point[]{
                new Point(0, 37),
                new Point(38, 13),
                new Point(12, 35),
                new Point(8, 33),
                new Point(32, 37)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest19() {
        Point[] points = new Point[]{
                new Point(33, 16),
                new Point(9, 24),
                new Point(23, 37),
                new Point(18, 2),
                new Point(26, 28)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest20() {
        Point[] points = new Point[]{
                new Point(34, 45),
                new Point(17, 25),
                new Point(0, 31),
                new Point(25, 0),
                new Point(15, 24)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest21() {
        Point[] points = new Point[]{
                new Point(0, 0),
                new Point(1, 1),
                new Point(1, 0)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest22() {
        Point[] points = new Point[]{
                new Point(0, 0),
                new Point(1, 1),
                new Point(1, 0),
                new Point(2, 0)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleTest23() {
        Point[] points = new Point[]{
                new Point(0, 0),
                new Point(1, 1),
                new Point(1, 0),
                new Point(2, 0),
                new Point(1, 0.5)
        };
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleCircle() {
        Point[] points = (Point[]) ResearchTest.getCirclePoints(4).toArray();
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleInTriangle() {
        Point[] points = (Point[]) ResearchTest.getInTriangles(10).toArray();
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void simpleInLine() {
        Point[] points = (Point[]) ResearchTest.getLineOnLine(1).toArray();
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() == 2);
    }

    @Test
    public void testBigInputData() {
        Point[] points = (Point[]) ResearchTest.getRandomPoints(20000).toArray();
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }

    @Test
    public void toggleTriangle() {
        double shortDistance = 1.0D;
        for (int i = 0; i < 9; i++) {
            shortDistance /= 10;
            Point[] points = new Point[]{
                    new Point(0.0D, 0.0D),
                    new Point(0.0D, 1.0D),
                    new Point(shortDistance, 0.5D)
            };
            TriangulationAdvance triangulationAdvance = new TriangulationAdvance(points);

            String msg = "shortDistance = " + String.format("%.1e", shortDistance)
                    + ": Size of triangles = " + triangulationAdvance.getTriangles().size();
            Assert.assertTrue(msg, triangulationAdvance.getTriangles().size() > 0);
        }
    }

    @Test
    public void TestIsAtRightOf1T() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);
        Point p3 = new Point(1, 0);
        Assert.assertTrue(TriangulationAdvance.Geometry.isAtRightOf(p1, p2, p3));
    }

    @Test
    public void TestIsAtRightOf2T() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 0);
        Point p3 = new Point(0.5, -0.5);
        Assert.assertTrue(TriangulationAdvance.Geometry.isAtRightOf(p1, p2, p3));
    }

    @Test
    public void TestIsAtRightOf1F() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);
        Point p3 = new Point(0, 1);
        Assert.assertFalse(TriangulationAdvance.Geometry.isAtRightOf(p1, p2, p3));
    }

    @Test
    public void TestIsCounterClockwise1() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0.5, 0.5);
        Point p3 = new Point(1, 0);
        Assert.assertTrue(TriangulationAdvance.Geometry.isCounterClockwise(p1, p2, p3));
    }

    @Test
    public void TestIsCounterClockwise2() {
        Point p1 = new Point(1, 0);
        Point p2 = new Point(0.5, 0.5);
        Point p3 = new Point(0, 0);
        Assert.assertFalse(TriangulationAdvance.Geometry.isCounterClockwise(p1, p2, p3));
    }

    @Test
    public void TestIs3pointsCollinear1() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0.5, 0.5);
        Point p3 = new Point(1, 1);
        Assert.assertTrue(TriangulationAdvance.Geometry.is3pointsCollinear(p1, p2, p3));
    }

    @Test
    public void TestIs3pointsCollinear2() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(0.5, 0.5);
        Point p3 = new Point(0, 0);
        Assert.assertTrue(TriangulationAdvance.Geometry.is3pointsCollinear(p1, p2, p3));
    }
}
