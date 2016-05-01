package main;

import un.api.collection.Sequence;
import un.impl.geometry.Point;
import un.impl.geometry.s2d.Triangle;
import un.impl.geometry.triangulate.Delaunay;

public class Area {
    public static double area(Delaunay delaunay) {
        double area = 0;
        Sequence triangles = delaunay.computeTriangles();
        for (int i = 0; i < triangles.getSize(); i++) {
            Point[] points = (Point[]) triangles.get(i);
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
        if (points[0].getDimension() == 0)
            return -1;
        if (points[0].getDimension() == 3) {
            double a = Math.sqrt(Math.pow(points[0].getX() - points[1].getX(), 2.) + Math.pow(points[0].getY() - points[1].getY(), 2.) + Math.pow(points[0].getZ() - points[1].getZ(), 2.));
            double b = Math.sqrt(Math.pow(points[2].getX() - points[1].getX(), 2.) + Math.pow(points[2].getY() - points[1].getY(), 2.) + Math.pow(points[2].getZ() - points[1].getZ(), 2.));
            double c = Math.sqrt(Math.pow(points[2].getX() - points[0].getX(), 2.) + Math.pow(points[2].getY() - points[0].getY(), 2.) + Math.pow(points[0].getZ() - points[2].getZ(), 2.));
            double p = (a + b + c) / 2;
            return Math.sqrt(p * Math.abs((p - a) * (p - b) * (p - c)));
        }
        double a = Math.sqrt(Math.pow(points[0].getX() - points[1].getX(), 2.) + Math.pow(points[0].getY() - points[1].getY(), 2.));
        double b = Math.sqrt(Math.pow(points[2].getX() - points[1].getX(), 2.) + Math.pow(points[2].getY() - points[1].getY(), 2.));
        double c = Math.sqrt(Math.pow(points[2].getX() - points[0].getX(), 2.) + Math.pow(points[2].getY() - points[0].getY(), 2.));
        double p = (a + b + c) / 2;
        return Math.sqrt(p * Math.abs((p - a) * (p - b) * (p - c)));
    }
}
