package triangulation.geometry;

import triangulation.elements.Point;

public class Geometry {

    public static double normalizeAngle(final double angle) {
        double result = angle;
        if (angle >= 2d * Math.PI) result = normalizeAngle(angle - 2d * Math.PI);
        if (angle < 0d) result = normalizeAngle(angle + 2d * Math.PI);
        return result;
    }

    public static double angleBetween2Line(
            double vertexX, double vertexY,
            double x1, double y1,
            double x2, double y2) {
        double dx1 = x1 - vertexX;
        double dx2 = x2 - vertexX;
        double dy1 = y1 - vertexY;
        double dy2 = y2 - vertexY;
        double d1 = Math.sqrt(dx1 * dx1 + dy1 * dy1);
        double d2 = Math.sqrt(dx2 * dx2 + dy2 * dy2);
        double angle = Math.acos((dx1 * dx2 + dy1 * dy2) / (d1 * d2));
//        logger.info("angleBetween2Line = "+ Math.toDegrees(angle));
        return normalizeAngle(angle);
    }


    public static double angle(
            double x1, double y1,
            double x2, double y2) {
        double angle;
        if (Math.abs(x2 - x1) < Precisions.epsilon()) {
            if ((y2 - y1) > 0) angle = Math.PI / 2d;
            else angle = Math.PI * 1.5d;
            return normalizeAngle(angle);
        } else if (Math.abs(y2 - y1) < Precisions.epsilon()) {
            if ((x2 - x1) > 0) angle = 0d;
            else angle = Math.PI;
            return normalizeAngle(angle);
        }

        int kvadrant;
        if (x2 > x1) {
            if (y2 > y1) kvadrant = 1;
            else kvadrant = 4;
        } else {
            if (y2 > y1) kvadrant = 2;
            else kvadrant = 3;
        }
        angle = Math.atan(Math.abs((y2 - y1) / (x2 - x1)));
        if (kvadrant == 1) ;
        else if (kvadrant == 2) angle = Math.PI - angle;
        else if (kvadrant == 3) angle = Math.PI + angle;
        else if (kvadrant == 4) angle = 2d * Math.PI - angle;

        return normalizeAngle(angle);
    }

    public static double lengthBetweenPoints(
            double x1, double y1,
            double x2, double y2
    ) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public static boolean isPointBetween2Border(double p, double pBorder1, double pBorder2) {
        if (p < Math.min(pBorder1, pBorder2) - Precisions.epsilon())
            return false;
        return p <= Math.max(pBorder1, pBorder2) + Precisions.epsilon();
    }

    public static boolean isPointInRectangle(double x, double y,
                                             double Line1X, double Line1Y,
                                             double Line2X, double Line2Y) {
        if (!isPointBetween2Border(x, Line1X, Line2X))
            return false;
        return isPointBetween2Border(y, Line1Y, Line2Y);
    }

    public static boolean is3pointsCollinear(
            double x1, double y1,
            double x2, double y2,
            double x3, double y3
    ) {
        boolean result = false;
        if (Math.abs(x1 - x2) < Precisions.epsilon() && Math.abs(x3 - x2) < Precisions.epsilon()) {
            return true;
        }

        if (Math.abs(y1 - y2) < Precisions.epsilon() && Math.abs(y3 - y2) < Precisions.epsilon()) {
            return true;
        }

        double factor = Math.abs((y2 - y1) * (x3 - x2) - (y3 - y2) * (x2 - x1));
        if (factor < Precisions.epsilon())
            result = true;
        return result;
    }

    public static double distancePoints(Point point1, Point point2) {
        return Math.sqrt(Math.pow(point1.getX() - point2.getX(), 2.0D) + Math.pow(point1.getY() - point2.getY(), 2.0D));
    }


    public static double det(double a[][]) {
        double det = a[0][0] * a[1][1] * a[2][2] + a[1][0] * a[2][1] * a[0][2] + a[0][1]*a[1][2]*a[2][0]
                - a[0][2]*a[1][1]*a[2][0] - a[0][1]*a[1][0]*a[2][2] - a[1][2]*a[2][1]*a[0][0];
        return det;
    }


    public static boolean isPointInCircle(Point[] circlePoints, Point point) {
        double x1 = circlePoints[0].getX();
        double y1 = circlePoints[0].getY();
        double z1 = x1 * x1 + y1 * y1;

        double x2 = circlePoints[1].getX();
        double y2 = circlePoints[1].getY();
        double z2 = x2 * x2 + y2 * y2;

        double x3 = circlePoints[2].getX();
        double y3 = circlePoints[2].getY();
        double z3 = x3 * x3 + y3 * y3;

        double a = det(new double[][]{
                {x1,y1,1},
                {x2,y2,1},
                {x3,y3,1}
        });

        double b = det(new double[][]{
                {z1,y1,1},
                {z2,y2,1},
                {z3,y3,1}
        });

        double c = det(new double[][]{
                {z1,x1,1},
                {z2,x2,1},
                {z3,x3,1}
        });

        double d = det(new double[][]{
                {z1,x1,y1},
                {z2,x2,y2},
                {z3,x3,y3}
        });

        double x0 = point.getX();
        double y0 = point.getY();
        double z0 = x0*x0+y0*y0;

        return Math.signum(a)*(a*z0-b*x0+c*y0-d)<0;
    }

}
