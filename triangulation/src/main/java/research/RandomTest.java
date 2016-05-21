package research;

import triangulation.Triangulation;
import triangulation.elements.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTest {

    private static Random random = new Random();

    public static void main(String[] args) throws Exception {
        List<Point> list = new ArrayList<>();
        for (int i = 0; i < 20000; i++) {
            for (int j = 0; j < 6; j++) {
                list.add(new Point((random.nextFloat()) * 5d, (random.nextFloat()) * 5d));
            }
            System.out.println("\n*********");
            System.out.println("RandomTest #" + i);
            for (Point aList : list) {
                System.out.println(String.format("\"%+5.8f\"", aList.getX()) + "," + String.format("\"%+5.8f\",", aList.getY()));
            }
            Triangulation triangulation = new Triangulation(list);
            System.out.println("Amount of triangles = " + triangulation.getMesh().sizeTriangles());
            list.clear();
        }
    }
}