package triangulation.geometries;

import triangulation.elements.Point;
import triangulation.elements.Precision;

import java.math.BigDecimal;

public class Geometry {

//create checkin precition and perfomance
//    Math.abs(value) > Precision.valueFactor() ===> Math.abs(value/LENGHT) > Precision.valueFactor()

    static public Value calculateValuePointOnLine(Point p1, Point p2, Point p3) {
        double value = (p2.getY() - p1.getY()) * (p3.getX() - p2.getX()) -
                (p3.getY() - p2.getY()) * (p2.getX() - p1.getX());
        if (Math.abs(value/((p2.getY() - p1.getY())+(p2.getX() - p1.getX()))) > Precision.valueFactor()) {
            return new Value(value);
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

        return new Value(result);
    }

    static boolean is3pointsCollinear(Value result) {
        if (result.isValueDouble()) {
            return Math.abs(result.getValueDouble()) < Precision.epsilon();
        }
        return result.getValueBig().abs().compareTo(Precision.bigEpsilon()) < 0;
    }

    public static boolean is3pointsCollinear(Point p1, Point p2, Point p3) {
        return is3pointsCollinear(calculateValuePointOnLine(p1, p2, p3));
    }

    static boolean isCounterClockwise(Value result) {
        if (result.isValueDouble()) {
            return result.getValueDouble() > 0;
        }
        return result.getValueBig().compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isCounterClockwise(Point a, Point b, Point c) {
        return isCounterClockwise(calculateValuePointOnLine(a, b, c));
    }


    public static boolean isAtRightOf(Point a, Point b, Point c) {
        return isCounterClockwise(a, b, c);
    }
    public static boolean isAtRightOf(Value value) {
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
