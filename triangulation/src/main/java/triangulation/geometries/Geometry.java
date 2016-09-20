package triangulation.geometries;

import triangulation.elements.Point;
import triangulation.elements.Precision;

import java.math.BigDecimal;

public class Geometry {

//todo create checkin precition and perfomance
//    Math.abs(value) > Precision.valueFactor() ===> Math.abs(value/LENGHT) > Precision.valueFactor()

    // if return -1 - result is less 0
    // if return 0 - result is 0
    // if return 1 - result is more 0
//    private static final int RESULT_IS_LESS_ZERO = -1;
//    private static final int RESULT_IS_ZERO = 0;
//    private static final int RESULT_IS_MORE_ZERO = 1;

    public enum POINT_ON_LINE{
        RESULT_IS_LESS_ZERO(-1),
        RESULT_IS_ZERO(0),
        RESULT_IS_MORE_ZERO(1);

        private final int data;
        POINT_ON_LINE(int input) {
            data = input;
        }
    }


    static public POINT_ON_LINE calculateValuePointOnLine(Point p1, Point p2, Point p3) {
        double value = (p2.getY() - p1.getY()) * (p3.getX() - p2.getX()) -
                (p3.getY() - p2.getY()) * (p2.getX() - p1.getX());
        if (Math.abs(value / ((p2.getY() - p1.getY()) + (p2.getX() - p1.getX()))) > Precision.valueFactor()) {
            if (value > Precision.epsilon())
                return POINT_ON_LINE.RESULT_IS_MORE_ZERO;
            if (Math.abs(value) > Precision.epsilon())
                return POINT_ON_LINE.RESULT_IS_LESS_ZERO;
            return POINT_ON_LINE.RESULT_IS_ZERO;
        }

        BigDecimal bdX1 = new BigDecimal(p1.getX());
        BigDecimal bdX2 = new BigDecimal(p2.getX());
        BigDecimal bdX3 = new BigDecimal(p3.getX());
        BigDecimal bdY1 = new BigDecimal(p1.getY());
        BigDecimal bdY2 = new BigDecimal(p2.getY());
        BigDecimal bdY3 = new BigDecimal(p3.getY());
        BigDecimal dX21 = bdX2.add(bdX1.negate());
        BigDecimal dY21 = bdY2.add(bdY1.negate());
        BigDecimal dX32 = bdX3.add(bdX2.negate());
        BigDecimal dY32 = bdY3.add(bdY2.negate());

        BigDecimal left = dY21.multiply(dX32);
        BigDecimal right = dY32.multiply(dX21).negate();

        BigDecimal result = left.add(right);

        if (result.compareTo(Precision.bigEpsilon()) > 0)
            return POINT_ON_LINE.RESULT_IS_MORE_ZERO;
        if (result.abs().compareTo(Precision.bigEpsilon()) > 0)
            return POINT_ON_LINE.RESULT_IS_LESS_ZERO;
        return POINT_ON_LINE.RESULT_IS_ZERO;
    }

    static boolean is3pointsCollinear(POINT_ON_LINE result) {
        if(result == POINT_ON_LINE.RESULT_IS_ZERO)
            return true;
        return false;
    }

    public static boolean is3pointsCollinear(Point p1, Point p2, Point p3) {
        return is3pointsCollinear(calculateValuePointOnLine(p1, p2, p3));
    }

    static boolean isCounterClockwise(POINT_ON_LINE result) {
        if(result == POINT_ON_LINE.RESULT_IS_MORE_ZERO)
            return true;
        return false;
    }

    public static boolean isCounterClockwise(Point a, Point b, Point c) {
        return isCounterClockwise(calculateValuePointOnLine(a, b, c));
    }


    public static boolean isAtRightOf(Point a, Point b, Point c) {
        return isCounterClockwise(a, b, c);
    }

    public static boolean isAtRightOf(POINT_ON_LINE value) {
        return isCounterClockwise(value);
    }

    public static double distanceLineAndPoint(Point lineP1, Point lineP2, Point p) {
        double A;
        double B = 1;
        double C;
        double distance;
        if (Math.abs(lineP2.getY() - lineP1.getY()) < Math.abs(lineP2.getX() - lineP1.getX())) {
            A = -(lineP2.getY() - lineP1.getY()) / (lineP2.getX() - lineP1.getX());
            C = -lineP1.getY() - A * lineP1.getX();
            distance = Math.abs((A * p.getX() + B * p.getY() + C) / Math.sqrt(A * A + B * B));
        } else {
            A = -(lineP2.getX() - lineP1.getX()) / (lineP2.getY() - lineP1.getY());
            C = -lineP1.getX() - A * lineP1.getY();
            distance = Math.abs((A * p.getY() + B * p.getX() + C) / Math.sqrt(A * A + B * B));
        }

        return distance;
    }
}
