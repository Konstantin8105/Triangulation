package triangulation.elements;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point that) {
        this.x = that.x;
        this.y = that.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
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
        //if (Math.abs(that.x - x) > Precisions.epsilon())
        //    return false;
        return x == that.x && y == that.y; //Math.abs(that.y - y) <= Precisions.epsilon();
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

    public static Point middlePoint(Point pointA, Point pointB) {
        return new Point((pointA.x + pointB.x) / 2d, (pointA.y + pointB.y) / 2d);
    }
}
