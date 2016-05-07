import triangulation.elements.Point;
import triangulation.Triangulation;

import java.util.*;

public class ResearchTest {

    enum TYPE_TEST{
        RANDOM,
        CIRCLE
    }

    private static Random random = new Random();

    public static void main(String[] args) throws Exception {
        int [] amountPoints = new int[]{3,5,10,20,50,100,200,500,1000,2000};
        for (int i = 0; i < TYPE_TEST.values().length; i++) {
            System.out.println("TYPE OF TEST: "+TYPE_TEST.values()[i].toString());
            for (int j = 0; j < amountPoints.length; j++) {
                test(amountPoints[j],TYPE_TEST.values()[i]);
            }
        }
    }

    private static void test(int size, TYPE_TEST type_test) throws Exception {
        int amountTest = 1;
        long start[] = new long[amountTest];
        long finish[] = new long[amountTest];
        float averageTime = 0;
        int sizeTriangles = 0;
        List<Point> mav = new ArrayList<>();
        switch(type_test){
            case RANDOM:
                mav = getRandomPoints(size);
                break;
            case CIRCLE:
                mav = getCirclePoints(size);
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
        System.out.println("Average speed for " + String.format("%6d", size)
                        + " point. "
                        + "Time is "
                        + String.format("%12.2f", averageTime) + " ms. "
                        + "Triangles is "
                        + String.format("%6d", sizeTriangles) + " triangles. "
        );
    }


    private static List<Point> getRandomPoints(int size) {
        Point[] coordinates = new Point[size];
        for (int j = 0; j < coordinates.length; j++) {
            coordinates[j] = new Point((random.nextFloat() - 0.5f) * 120, (random.nextFloat() - 0.5f) * 120);
        }
        return Arrays.asList(coordinates);
    }

    private static List<Point> getCirclePoints(int size) {
        Point[] coordinates = new Point[size];
        for (int j = 0; j < size; j++) {
            coordinates[j] = new Point(size * Math.sin(2 * 3.1415 / size * j), size * Math.cos(2 * 3.1415 / size * j));
        }
        return Arrays.asList(coordinates);
    }
}
