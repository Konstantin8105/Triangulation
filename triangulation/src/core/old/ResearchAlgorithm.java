package core.old;

import core.elements.Coordinate;

import java.util.*;

public class ResearchAlgorithm {

    static Random random = new Random();

    public static void main(String[] args) throws Exception {
        List<Coordinate> list = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            for (int j = 0; j < 6; j++) {
                list.add(new Coordinate((random.nextFloat()) * 5d, (random.nextFloat()) * 5d));
            }
            System.out.println("\n*********");
            System.out.println("old.core.old.ResearchAlgorithm #"+i);
            for (int j = 0; j < list.size(); j++) {
                System.out.println(String.format("\"%+5.8f\"",list.get(j).getX())+","+String.format("\"%+5.8f\",",list.get(j).getY()));
            }
            Triangulation triangulation = new Triangulation();
            triangulation.add(list);
            triangulation.run();
            list.clear();
        }
    }
}