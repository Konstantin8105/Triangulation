package core.old;

import core.elements.Coordinate;

import java.util.Arrays;
import java.util.Date;
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
//        test(10000);
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
    static void test(int size) throws Exception {
        int amountTest = 2;
        long start[] = new long[amountTest];
        long finish[] = new long[amountTest];
        float averageSpeed = 0;
        float averageTime = 0;
        Coordinate[] coordinates = new Coordinate[size];
        for (int j = 0; j < coordinates.length; j++) {
            coordinates[j] = new Coordinate((random.nextFloat() - 0.5f) * 120, (random.nextFloat() - 0.5f) * 120);
        }
        for (int i = 0; i < amountTest; i++) {
            Triangulation triangulation = new Triangulation();
            triangulation.add(Arrays.asList(coordinates));
            start[i] = (new Date()).getTime();
            triangulation.run();
            finish[i] = (new Date()).getTime();
            //System.out.println("Time = " + (finish[i]-start[i]));
        }
        for (int i = 0; i < amountTest; i++) {
            float time = (float) (finish[i] - start[i]);
            float speed = time / (float) size;
            /*System.out.println(
                    "i = " + i
                            + " : size = " + size
                            + " : Time = " + time + " msec"
                            + " : Speed = " + String.format("%.3f", speed) + "ms/point"
            );*/
            averageSpeed += speed;
            averageTime += time;
        }

        averageSpeed /= amountTest;
        averageTime /= amountTest;
        //System.out.println("Average time  for " + size + " point is " + String.format("%.3f", averageTime ) + "ms");
        System.out.println("Average speed for " + String.format("%4d",size) + " point is "
                + String.format("%8.2f", averageSpeed) + " ms/point; "
                + "Time is "
                + String.format("%8.2f", averageTime) + " ms");
    }
}
