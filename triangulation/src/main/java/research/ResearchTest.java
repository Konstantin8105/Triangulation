package research;

import triangulation.Triangulation;
import triangulation.elements.Point;
import triangulationAdvance.TriangulationAdvance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ResearchTest {

    enum TYPE_TEST {
        RANDOM,
        CIRCLE,
        LINE_IN_LINE,
        IN_TRIANGLE
    }

    private static Random random = new Random();

    public static void main(String[] args) throws Exception {
        int[] amountPoints = new int[]{
                3,
                5,
                10, 20, 50,
                100, 200, 500,
//                1000, 2000,
//                5000,
//                10000, 20000, 50000,
//                100000

        };
        for (int i = 0; i < TYPE_TEST.values().length; i++) {
            System.out.println("TYPE OF TEST: " + TYPE_TEST.values()[i].toString());
            for (int amountPoint : amountPoints) {
                test(amountPoint, TYPE_TEST.values()[i]);
            }
        }
    }

    private static void test(int size, TYPE_TEST type_test) throws Exception {
        int amountTest = 20;//10;//1;//500;//
        long start[] = new long[amountTest];
        long finish[] = new long[amountTest];
        float averageTime = 0;
        int sizeTriangles = 0;
        List<Point> mav = new ArrayList<>();
        switch (type_test) {
            case RANDOM:
                mav = getRandomPoints(size);
                break;
            case CIRCLE:
                mav = getCirclePoints(size);
                break;
            case LINE_IN_LINE:
                mav = getLineOnLine(size);
                break;
            case IN_TRIANGLE:
                mav = getInTriangles(size);
                break;
        }

        System.out.println(mav);

        for (int i = 0; i < amountTest; i++) {
            start[i] = System.currentTimeMillis();//(new Date()).getTime();
            Triangulation triangulation = new Triangulation(mav);
            finish[i] = System.currentTimeMillis();//(new Date()).getTime();
            sizeTriangles = triangulation.getTriangles().size();
        }
        for (int i = 0; i < amountTest; i++) {
            float time = (float) (finish[i] - start[i]);
            averageTime += time;
        }

        averageTime /= amountTest;
        System.out.println("Amount points: " + String.format("%6d", size)
                + " point. "
                + "Time is "
                + String.format("%12.2f", averageTime) + " ms. "
                + "Triangles is "
                + String.format("%6d", sizeTriangles) + " triangles. "
        );

        //Advance

        Point[] points = (Point[]) mav.toArray();

        for (int i = 0; i < amountTest; i++) {
            start[i] = System.currentTimeMillis();//(new Date()).getTime();
            TriangulationAdvance triangulation = new TriangulationAdvance(points);
            finish[i] = System.currentTimeMillis();//(new Date()).getTime();
            sizeTriangles = triangulation.getTriangles().size();
        }
        for (int i = 0; i < amountTest; i++) {
            float time = (float) (finish[i] - start[i]);
            averageTime += time;
        }

        averageTime /= amountTest;
        System.out.println("Amount points: " + String.format("%6d", size)
                + " point. "
                + "Time is "
                + String.format("%12.2f", averageTime) + " ms. "
                + "Triangles is "
                + String.format("%6d", sizeTriangles) + " triangles. "
        );

        System.out.println("==");
    }

    final private static int SIZE = 600;

    public static List<Point> getRandomPoints(final int size) {
        Point[] coordinates = new Point[size];
        for (int j = 0; j < coordinates.length; j++) {
            coordinates[j] = new Point(
                    random.nextInt(SIZE), random.nextInt(SIZE)
            );
        }
        return Arrays.asList(coordinates);
    }

    public static List<Point> getCirclePoints(final int size) {
        Point[] coordinates = new Point[size];
        for (int j = 0; j < size - 1; j++) {
            coordinates[j] = new Point(
                    SIZE/2. * Math.sin(2. * Math.PI / size * j) + SIZE/2,
                    SIZE/2. * Math.cos(2. * Math.PI / size * j) + SIZE/2
            );
        }
        coordinates[coordinates.length - 1] = new Point(SIZE/2, SIZE/2);
        return Arrays.asList(coordinates);
    }

    public static List<Point> getLineOnLine(final int size) {
        Point[] coordinates = new Point[3 + size];
        coordinates[0] = new Point(0, 0);
        coordinates[1] = new Point(SIZE, 0);
        coordinates[2] = new Point(SIZE, SIZE);
        for (int j = 3; j < coordinates.length; j++) {
            coordinates[j] = new Point(
                    100 + (SIZE-200) * j / coordinates.length,
                    100 + (SIZE-200) * j / coordinates.length
            );
        }
        return Arrays.asList(coordinates);
    }

    public static List<Point> getInTriangles(final int size) {
        Point[] coordinates = new Point[4 + size];
        coordinates[0] = new Point(0, 0);
        coordinates[1] = new Point(SIZE, 0);
        coordinates[2] = new Point(0, SIZE);
        coordinates[3] = new Point(SIZE, SIZE);
        for (int j = 4; j < coordinates.length; j++) {
            coordinates[j] = new Point(
                    100 + (SIZE-200) * j / coordinates.length, SIZE/2
            );
        }
        return Arrays.asList(coordinates);
    }
}
