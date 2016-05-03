package elements;

import geometry.Precisions;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Point{"
                + " x = " + String.format("%.6f", x)
                + ","
                + " y = " + String.format("%.6f", y) +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Point that = (Point) o;
        if (Math.abs(that.x - x) > Precisions.epsilon())
            return false;
        if (Math.abs(that.y - y) > Precisions.epsilon())
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
