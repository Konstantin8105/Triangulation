package triangulation.border.borderSegment.elements;

import triangulation.elements.Line;
import triangulation.elements.Point;

public class LineWithPoints extends Line {
    private Point pointA;
    private Point pointB;
    private Point pointMiddle;

    public LineWithPoints(int idPointA, int idPointB) throws Exception {
        super(idPointA, idPointB);
    }

    public LineWithPoints(Line line) {
        super(line);
    }

    public boolean isPointNull() {
        return pointA == null || pointB == null || pointMiddle == null;
    }

    public void setPoints(Point A, Point B) {
        pointA = A;
        pointB = B;
        pointMiddle = Point.middlePoint(pointA,pointB);
    }

    public Point getPointA() {
        return pointA;
    }

    public Point getPointB() {
        return pointB;
    }

    public Point getPointMiddle() {
        return pointMiddle;
    }

    @Override
    public String toString() {
        return "LineWithPoints{" +
                "pointA=" + pointA +
                ", pointB=" + pointB +
                ", pointMiddle=" + pointMiddle +
                ", other = "+super.toString() +
                '}';
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (pointA != null ? pointA.hashCode() : 0);
        result = 31 * result + (pointB != null ? pointB.hashCode() : 0);
        result = 31 * result + (pointMiddle != null ? pointMiddle.hashCode() : 0);
        return result;
    }
}