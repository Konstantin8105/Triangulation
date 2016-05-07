import triangulation.Triangulation;
import triangulation.elements.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTest {

    static Random random = new Random();

    public static void main(String[] args) throws Exception {
        List<Point> list = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            for (int j = 0; j < 6; j++) {
                list.add(new Point((random.nextFloat()) * 5d, (random.nextFloat()) * 5d));
            }
            System.out.println("\n*********");
            System.out.println("RandomTest #" + i);
            for (int j = 0; j < list.size(); j++) {
                System.out.println(String.format("\"%+5.8f\"", list.get(j).getX()) + "," + String.format("\"%+5.8f\",", list.get(j).getY()));
            }
            Triangulation triangulation = new Triangulation(list);
            list.clear();
        }
    }
}