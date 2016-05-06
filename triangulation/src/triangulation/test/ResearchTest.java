package triangulation.test;

import elements.Point;
import triangulation.Triangulation;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ResearchTest {

    static Random random = new Random();

    public static void main(String[] args) throws Exception {
        test(3);
        test(5);
        test(10);
        test(20);
        test(50);
        test(100);
        test(200);
        test(500);
        test(1000);
        test(2000);
        test(5000);
        test(10000);
    }

    /*
    With test, without advance
    Average speed for    5 point is     13.8 ms/point; Time is     69.0 ms
    Average speed for   10 point is     28.5 ms/point; Time is    284.5 ms
    Average speed for   20 point is     51.8 ms/point; Time is   1036.0 ms
    Average speed for   50 point is    107.0 ms/point; Time is   5351.5 ms
    Average speed for  100 point is    194.8 ms/point; Time is  19479.5 ms
    Average speed for  200 point is    467.8 ms/point; Time is  93551.5 ms
     */
/*
Without test, without Advance
Average speed for    5 point is     16.5 ms/point; Time is     82.5 ms
Average speed for   10 point is     16.7 ms/point; Time is    167.0 ms
Average speed for   20 point is     32.7 ms/point; Time is    653.0 ms
Average speed for   50 point is     40.4 ms/point; Time is   2022.5 ms
Average speed for  100 point is     52.5 ms/point; Time is   5247.5 ms
Average speed for  200 point is     85.7 ms/point; Time is  17145.5 ms
*/
/*
Without test with advance
Average speed for    5 point is     11.8 ms/point; Time is     59.0 ms
Average speed for   10 point is     16.8 ms/point; Time is    167.5 ms
Average speed for   20 point is     22.6 ms/point; Time is    452.0 ms
Average speed for   50 point is     22.4 ms/point; Time is   1121.0 ms
Average speed for  100 point is     24.5 ms/point; Time is   2447.0 ms
Average speed for  200 point is     37.7 ms/point; Time is   7547.0 ms
Average speed for 1000 point is     53.9 ms/point; Time is  53902.5 ms
 */
/*
Without test with advance with Ram
Average speed for    5 point is      5.8 ms/point; Time is     29.0 ms
Average speed for   10 point is      1.0 ms/point; Time is      9.5 ms
Average speed for   20 point is      1.1 ms/point; Time is     22.5 ms
Average speed for   50 point is      1.7 ms/point; Time is     84.0 ms
Average speed for  100 point is      1.2 ms/point; Time is    116.5 ms
Average speed for  200 point is      1.2 ms/point; Time is    236.0 ms
Average speed for  500 point is      3.3 ms/point; Time is   1631.0 ms
Average speed for 1000 point is      6.1 ms/point; Time is   6089.5 ms
Average speed for 5000 point is     42.9 ms/point; Time is 214481.5 ms
*/
/*
Without test with advance with Ram with BinarySearch IDs
Average speed for    5 point is      2.3 ms/point; Time is     11.5 ms
Average speed for   10 point is      0.6 ms/point; Time is      6.0 ms
Average speed for   20 point is      0.6 ms/point; Time is     13.0 ms
Average speed for   50 point is      0.6 ms/point; Time is     30.5 ms
Average speed for  100 point is      0.5 ms/point; Time is     46.0 ms
Average speed for  200 point is      0.6 ms/point; Time is    113.5 ms
Average speed for  500 point is      1.2 ms/point; Time is    620.5 ms
Average speed for 1000 point is      1.4 ms/point; Time is   1402.0 ms
Average speed for 2000 point is      3.3 ms/point; Time is   6692.0 ms
Average speed for 5000 point is     14.3 ms/point; Time is  71542.5 ms
*/
/*
Without test with advance with Ram with BinarySearch IDs with advance map sqrt(size)
Average speed for    5 point is      4.0 ms/point; Time is     20.0 ms
Average speed for   10 point is      1.3 ms/point; Time is     13.0 ms
Average speed for   20 point is      0.9 ms/point; Time is     18.0 ms
Average speed for   50 point is      0.7 ms/point; Time is     36.0 ms
Average speed for  100 point is      0.5 ms/point; Time is     54.0 ms
Average speed for  200 point is      0.5 ms/point; Time is     95.5 ms
Average speed for  500 point is      0.6 ms/point; Time is    289.0 ms
Average speed for 1000 point is      0.9 ms/point; Time is    927.5 ms
Average speed for 2000 point is      1.2 ms/point; Time is   2399.0 ms
Average speed for 5000 point is      1.7 ms/point; Time is   8372.0 ms
 */
/*
Without advance mesh
Average speed for    3 point is     3.50 ms/point; Time is    10.50 ms
Average speed for    5 point is     1.00 ms/point; Time is     5.00 ms
Average speed for   10 point is     0.30 ms/point; Time is     3.00 ms
Average speed for   20 point is     0.40 ms/point; Time is     8.00 ms
Average speed for   50 point is     0.42 ms/point; Time is    21.00 ms
Average speed for  100 point is     0.38 ms/point; Time is    37.50 ms
Average speed for  200 point is     0.34 ms/point; Time is    69.00 ms
Average speed for  500 point is     0.52 ms/point; Time is   262.50 ms
Average speed for 1000 point is     0.70 ms/point; Time is   696.50 ms
Average speed for 2000 point is     1.37 ms/point; Time is  2742.50 ms
Average speed for 5000 point is     4.43 ms/point; Time is 22158.00 ms
Average speed for 10000 point is  11.02 ms/point; Time is 110203.00 ms
*/
/* Regular mesh
Average speed for      3 point. Time is        13.00 ms. Triangles is      1 triangles.
Average speed for      5 point. Time is         4.00 ms. Triangles is      3 triangles.
Average speed for     10 point. Time is         5.00 ms. Triangles is      8 triangles.
Average speed for     20 point. Time is        16.00 ms. Triangles is     18 triangles.
Average speed for     50 point. Time is        83.00 ms. Triangles is     48 triangles.
Average speed for    100 point. Time is        91.00 ms. Triangles is     98 triangles.
Average speed for    200 point. Time is       377.00 ms. Triangles is    198 triangles.
Average speed for    500 point. Time is      2371.00 ms. Triangles is    498 triangles.
Average speed for   1000 point. Time is     14886.00 ms. Triangles is    998 triangles.
     */
    /*
Average speed for      3 point. Time is        44.00 ms. Triangles is      1 triangles.
Average speed for      5 point. Time is         9.00 ms. Triangles is      3 triangles.
Average speed for     10 point. Time is        11.00 ms. Triangles is      8 triangles.
Average speed for     20 point. Time is        31.00 ms. Triangles is     18 triangles.
Average speed for     50 point. Time is       144.00 ms. Triangles is     48 triangles.
Average speed for    100 point. Time is       326.00 ms. Triangles is     98 triangles.
Average speed for    200 point. Time is      1017.00 ms. Triangles is    198 triangles.
Average speed for    500 point. Time is      7685.00 ms. Triangles is    498 triangles.
     */
    static void test(int size) throws Exception {
        int amountTest = 1;
        long start[] = new long[amountTest];
        long finish[] = new long[amountTest];
//        float averageSpeed = 0;
        float averageTime = 0;
        int sizeTriangles = 0;
        List<Point> mav = getPoints(size);
        for (int i = 0; i < amountTest; i++) {
            start[i] = (new Date()).getTime();
            Triangulation triangulation = new Triangulation(mav);
            finish[i] = (new Date()).getTime();
            sizeTriangles = triangulation.getMesh().sizeTriangles();
            //System.out.println("Time = " + (finish[i]-start[i]));
        }
        for (int i = 0; i < amountTest; i++) {
            float time = (float) (finish[i] - start[i]);
//            float speed = time / (float) size;
            /*System.out.println(
                    "i = " + i
                            + " : size = " + size
                            + " : Time = " + time + " msec"
                            + " : Speed = " + String.format("%.3f", speed) + "ms/point"
            );*/
//            averageSpeed += speed;
            averageTime += time;
        }

//        averageSpeed /= amountTest;
        averageTime /= amountTest;
        //System.out.println("Average time  for " + size + " point is " + String.format("%.3f", averageTime ) + "ms");
        System.out.println("Average speed for " + String.format("%6d", size)
                        + " point. "
//                + String.format("%03.2f", averageSpeed) + " ms/point; "
                        + "Time is "
                        + String.format("%12.2f", averageTime) + " ms. "
                        + "Triangles is "
                        + String.format("%6d", sizeTriangles) + " triangles. "
        );
    }
//
//    private static List<Point> getPoints(int size) {
//        Point[] coordinates = new Point[size];
//        for (int j = 0; j < coordinates.length; j++) {
//            coordinates[j] = new Point((random.nextFloat() - 0.5f) * 120, (random.nextFloat() - 0.5f) * 120);
//        }
//        return Arrays.asList(coordinates);
//    }
    private static List<Point> getPoints(int size) {
        Point[] coordinates = new Point[size];
        for (int j = 0; j < size; j++) {
            coordinates[j] = new Point(size * Math.sin(2 * 3.1415 / size * j), size * Math.cos(2 * 3.1415 / size * j));
        }
        return Arrays.asList(coordinates);
    }
}