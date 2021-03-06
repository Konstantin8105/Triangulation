package triangulation.statistic;

import crosssection.Area;
import triangulation.TriangulationDelaunay;
import triangulation.elements.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticTriangulation {
    public StatisticTriangulation(TriangulationDelaunay triangulation) {
        List<Point[]> triangles = triangulation.getTriangles();
        area(triangles);
        angle(triangles);
    }

    private void angle(List<Point[]> triangles) {
        List<Double> angles = new ArrayList<>();
        for (Point[] points:triangles) {
            double[] angle = Area.angleBetween(points);
            for (double anAngle : angle) {
                angles.add(anAngle);
            }
        }
        double [] areaLimit = new double[]{
                0,10,20,30,40,50,60,
                70,80,90,100,110,120,
                130,140,150,160,170,180
        };
        System.out.println("Angle histogram:");
        Statistic statisticArea = new Statistic();
        statisticArea.histogram(angles,areaLimit);
    }

    private void area(List<Point[]> triangles){
        List<Double> areas = triangles.stream().map(Area::area_3point).collect(Collectors.toList());
        double [] areaLimit = new double[]{
                0,1.5,2,2.5,3,
                4,6,10,15,25,50,
                100,300,1000
        };
        System.out.println("Triangle area ratio histogram:");
        Statistic statisticArea = new Statistic();
        statisticArea.histogram(areas,areaLimit);
    }
}
