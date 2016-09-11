package crosssection;

import triangulation.Point;

public class Area {
    static public double area_3point(Point[] points) {
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

    public static double[] angleBetween(Point[] points) {
        return new double []{
                normalizeAngle(Math.toDegrees(Math.atan2(points[1].getX() - points[0].getX(),points[1].getY() - points[0].getY())-
                Math.atan2(points[2].getX()- points[0].getX(),points[2].getY()- points[0].getY()))),

                normalizeAngle(Math.toDegrees(Math.atan2(points[0].getX() - points[2].getX(),points[0].getY() - points[2].getY())-
                Math.atan2(points[1].getX()- points[2].getX(),points[1].getY()- points[2].getY()))),

                normalizeAngle(Math.toDegrees(Math.atan2(points[2].getX() - points[1].getX(),points[2].getY() - points[1].getY())-
                Math.atan2(points[0].getX()- points[1].getX(),points[0].getY()- points[1].getY()))),
        };
    }

    private static double normalizeAngle(double angle) {
        if(angle < 0){
            return normalizeAngle(angle + 360);
        }
        return angle;
    }

}
