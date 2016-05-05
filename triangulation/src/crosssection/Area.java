package crosssection;

import elements.Collections.IDable;
import elements.Mesh;
import elements.Triangle;
import un.api.collection.Sequence;
import un.impl.geometry.Point;

import java.util.List;

public class Area {
    public static double area(Sequence triangles) {
        double area = 0;
        if (triangles.getSize() < 1)
            return -1;
        for (int i = 0; i < triangles.getSize(); i++) {
            Point[] points = (Point[]) triangles.get(i);
            area += area_3point(points);
        }
        return area;
    }

    public static double area(Mesh mesh) throws Exception {
        double area = 0;
        if (mesh.sizeTriangles() < 1)
            return -1;
        List<IDable.Element> list = mesh.getTriangles();
        for (int i = 0; i < list.size(); i++) {
            int[] idPoint = new int[]{
                    ((Triangle) list.get(i).value).getIdPoint1(),
                    ((Triangle) list.get(i).value).getIdPoint2(),
                    ((Triangle) list.get(i).value).getIdPoint3()
            };
            elements.Point[] points = new elements.Point[3];
            for (int j = 0; j < idPoint.length; j++) {
                points[j] = mesh.getPoints(idPoint[j]);
            }
            area += area_3point(points);
        }
        return area;
    }

    static private double area_3point(Point[] points) {
        //
        // Area of triangle
        // https://en.wikipedia.org/wiki/Triangle#Computing_the_area_of_a_triangle
        // Using Heron's formula
        //
        if (points[0].getDimension() == 2) {
            double a = Math.sqrt(Math.pow(points[0].getX() - points[1].getX(), 2.) + Math.pow(points[0].getY() - points[1].getY(), 2.));
            double b = Math.sqrt(Math.pow(points[2].getX() - points[1].getX(), 2.) + Math.pow(points[2].getY() - points[1].getY(), 2.));
            double c = Math.sqrt(Math.pow(points[2].getX() - points[0].getX(), 2.) + Math.pow(points[2].getY() - points[0].getY(), 2.));
            double p = (a + b + c) / 2;
            return Math.sqrt(p * Math.abs((p - a) * (p - b) * (p - c)));
        }
        if (points[0].getDimension() == 3) {
            double a = Math.sqrt(Math.pow(points[0].getX() - points[1].getX(), 2.) + Math.pow(points[0].getY() - points[1].getY(), 2.) + Math.pow(points[0].getZ() - points[1].getZ(), 2.));
            double b = Math.sqrt(Math.pow(points[2].getX() - points[1].getX(), 2.) + Math.pow(points[2].getY() - points[1].getY(), 2.) + Math.pow(points[2].getZ() - points[1].getZ(), 2.));
            double c = Math.sqrt(Math.pow(points[2].getX() - points[0].getX(), 2.) + Math.pow(points[2].getY() - points[0].getY(), 2.) + Math.pow(points[0].getZ() - points[2].getZ(), 2.));
            double p = (a + b + c) / 2;
            return Math.sqrt(p * Math.abs((p - a) * (p - b) * (p - c)));
        }
        return -1;
    }

    static private double area_3point(elements.Point[] points) {
        //
        // Area of triangle
        // https://en.wikipedia.org/wiki/Triangle#Computing_the_area_of_a_triangle
        // Using Heron's formula
        //
        double a = Math.sqrt(Math.pow(points[0].getX() - points[1].getX(), 2.) + Math.pow(points[0].getY() - points[1].getY(), 2.));
        double b = Math.sqrt(Math.pow(points[2].getX() - points[1].getX(), 2.) + Math.pow(points[2].getY() - points[1].getY(), 2.));
        double c = Math.sqrt(Math.pow(points[2].getX() - points[0].getX(), 2.) + Math.pow(points[2].getY() - points[0].getY(), 2.));
        double p = (a + b + c) / 2;
        return Math.sqrt(p * Math.abs((p - a) * (p - b) * (p - c)));
    }
}
