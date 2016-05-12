import triangulation.Triangulation;
import triangulation.elements.Point;

import java.util.*;

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
                3, 5,
                10, 20, 50,
                100, 200, 500,
                1000};//, 2000
        for (int i = 0; i < TYPE_TEST.values().length; i++) {
            System.out.println("TYPE OF TEST: " + TYPE_TEST.values()[i].toString());
            for (int amountPoint : amountPoints) {
                test(amountPoint, TYPE_TEST.values()[i]);
            }
        }
    }

    private static void test(int size, TYPE_TEST type_test) throws Exception {
        int amountTest = 10;
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

        for (int i = 0; i < amountTest; i++) {
            start[i] = (new Date()).getTime();
            Triangulation triangulation = new Triangulation(mav);
            finish[i] = (new Date()).getTime();
            sizeTriangles = triangulation.getMesh().sizeTriangles();
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
    }


    public static List<Point> getRandomPoints(final int size) {
        Point[] coordinates = new Point[size];
        for (int j = 0; j < coordinates.length; j++) {
            coordinates[j] = new Point((random.nextFloat()) * 600, (random.nextFloat()) * 600);
        }
        return Arrays.asList(coordinates);
    }

    public static List<Point> getCirclePoints(final int size) {
        Point[] coordinates = new Point[size];
        for (int j = 0; j < size - 1; j++) {
            coordinates[j] = new Point(300 * Math.sin(2 * 3.1415 / size * j) + 300, 300 * Math.cos(2 * 3.1415 / size * j) + 300);
        }
        coordinates[coordinates.length - 1] = new Point(300, 300);
        return Arrays.asList(coordinates);
    }

    public static List<Point> getLineOnLine(final int size) {
        Point[] coordinates = new Point[3+size];
        coordinates[0] = new Point(000,000);
        coordinates[1] = new Point(600,0);
        coordinates[2] = new Point(600,0);
        for (int j = 3; j < coordinates.length; j++) {
            coordinates[j] = new Point(j+1,j+1);
        }
        return Arrays.asList(coordinates);
    }

    public static List<Point> getInTriangles(final int size) {
        Point[] coordinates = new Point[4+size];
        coordinates[0] = new Point(000,000);
        coordinates[1] = new Point(600,000);
        coordinates[2] = new Point(000,600);
        coordinates[3] = new Point(600,600);
        for (int j = 4; j < coordinates.length; j++) {
            coordinates[j] = new Point(0.1+600*0.8*(double)j/(double)size,300);
        }
        return Arrays.asList(coordinates);
    }

}
