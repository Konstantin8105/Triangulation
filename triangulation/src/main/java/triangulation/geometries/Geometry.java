package triangulation.geometries;

import triangulation.elements.Point;
import triangulation.elements.Precision;

import java.math.BigDecimal;
import java.util.*;

public class Geometry {


    static Value pointOnLine(Point p1, Point p2, Point p3) {
        double value = (p2.getY() - p1.getY()) * (p3.getX() - p2.getX()) -
                (p3.getY() - p2.getY()) * (p2.getX() - p1.getX());
        if (Math.abs(value) > Precision.valueFactor()) {
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
        return is3pointsCollinear(pointOnLine(p1, p2, p3));
    }

    static boolean isCounterClockwise(Value result) {
        if (result.isValueDouble()) {
            return result.getValueDouble() > 0;
        }
        return result.getValueBig().compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isCounterClockwise(Point a, Point b, Point c) {
        return isCounterClockwise(pointOnLine(a, b, c));
    }


    public static boolean isAtRightOf(Point a, Point b, Point c) {
        return isCounterClockwise(a, b, c);
    }


    static double det(double a[][]) {
        return a[0][0] * a[1][1] * a[2][2] + a[1][0] * a[2][1] * a[0][2] + a[0][1] * a[1][2] * a[2][0]
                - a[0][2] * a[1][1] * a[2][0] - a[0][1] * a[1][0] * a[2][2] - a[1][2] * a[2][1] * a[0][0];
    }

    static boolean isPointInCircle(Point point, Point circlePoints0, Point circlePoints1, Point circlePoints2) {
        double x1 = circlePoints0.getX();
        double y1 = circlePoints0.getY();
        double z1 = x1 * x1 + y1 * y1;

        double x2 = circlePoints1.getX();
        double y2 = circlePoints1.getY();
        double z2 = x2 * x2 + y2 * y2;

        double x3 = circlePoints2.getX();
        double y3 = circlePoints2.getY();
        double z3 = x3 * x3 + y3 * y3;

        double a = det(new double[][]{
                {x1, y1, 1},
                {x2, y2, 1},
                {x3, y3, 1}
        });

        double b = det(new double[][]{
                {z1, y1, 1},
                {z2, y2, 1},
                {z3, y3, 1}
        });

        double c = det(new double[][]{
                {z1, x1, 1},
                {z2, x2, 1},
                {z3, x3, 1}
        });

        double d = det(new double[][]{
                {z1, x1, y1},
                {z2, x2, y2},
                {z3, x3, y3}
        });

        double x0 = point.getX();
        double y0 = point.getY();
        double z0 = x0 * x0 + y0 * y0;

        return Math.signum(a) * (a * z0 - b * x0 + c * y0 - d) < -Precision.epsilon();
    }


    //Performance O(n*log(n)) in worst case
    public static Point[] convexHull(Point[] inputPoints) {
        if (inputPoints.length < 2)
            return inputPoints;

        List<Point> array = new ArrayList<>(Arrays.asList(inputPoints));

        Collections.sort(array, new Comparator<Point>() {
            @Override
            public int compare(Point first, Point second) {
                if ((first).getX() == (second).getX()) {
                    if ((first).getY() > (second).getY())
                        return 1;
                    if ((first).getY() < (second).getY())
                        return -1;
                    return 0;
                }
                if ((first).getX() > (second).getX())
                    return 1;
                if ((first).getX() < (second).getX())
                    return -1;
                return 0;
            }
        });

        List<Integer> removedIndex = new ArrayList<>();
        for (int i = 1; i < array.size(); i++) {
            if (array.get(i - 1).equals(array.get(i))) {
                removedIndex.add(i);
            }
        }
        for (int i = removedIndex.size() - 1; i >= 0; i--) {
            int position = removedIndex.get(i);
            array.remove(position);
        }

        int n = array.size();
        Point[] P = new Point[n];
        for (int i = 0; i < n; i++) {
            P[i] = array.get(i);
        }

        Point[] H = new Point[2 * n];

        int k = 0;
        // Build lower hull
        for (int i = 0; i < n; ++i) {
            while (k >= 2 && isCounterClockwise(H[k - 2], H[k - 1], P[i])) {
                k--;
            }
            H[k++] = P[i];
        }

        // Build upper hull
        for (int i = n - 2, t = k + 1; i >= 0; i--) {
            while (k >= t && isCounterClockwise(H[k - 2], H[k - 1], P[i])) {
                k--;
            }
            H[k++] = P[i];
        }
        if (k > 1) {
            H = Arrays.copyOfRange(H, 0, k - 1); // remove non-hull vertices after k; remove k - 1 which is a duplicate
        }
        return H;
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
