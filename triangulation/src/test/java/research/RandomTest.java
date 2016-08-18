package research;

import org.junit.Assert;
import org.junit.Test;
import triangulation.Triangulation;
import triangulation.elements.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTest {

    final private boolean PRINTABLE = false;
    private static Random random = new Random();

    @Test
    public void randomTest() throws Exception {
        List<Point> list = new ArrayList<>();
        for (int i = 0; i < 20000; i++) {
            for (int j = 0; j < 20; j++) {
                list.add(new Point((random.nextFloat()) * 5d, (random.nextFloat()) * 5d));
            }
            Triangulation triangulation = new Triangulation(list);
            if(PRINTABLE){
                System.out.println("\n*********");
                System.out.println("RandomTest #" + i);
                for (Point aList : list) {
                    System.out.println(String.format("\"%+5.8f\"", aList.getX()) + "," + String.format("\"%+5.8f\",", aList.getY()));
                }
                System.out.println("Amount of triangles = " + triangulation.getTriangles().size());
            }
            Assert.assertTrue(triangulation.getTriangles().toString(),triangulation.getTriangles().size() > 0);
            list.clear();
        }
    }
}