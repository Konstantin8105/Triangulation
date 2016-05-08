package triangulation.elements;

import triangulation.border.BorderLine;
import triangulation.elements.Collections.IDable;
import triangulation.geometry.GeometryPointLine;
import triangulation.geometry.GeometryPointTriangle;

import java.util.List;

public class Mesh {
    private final IDable<Point> points = new IDable<>();
    private final IDable<Line> lines = new IDable<>();
    private final IDable<Triangle> triangles = new IDable<>();

    private final BorderLine borderLine = new BorderLine(this);

    public void addPoint(List<Point> point) {
        points.add(point);
        deleteSamePoints();
    }

    private void deleteSamePoints() {
        // TODO: 5/7/16
    }

    public int addLine(Line line) throws Exception {
        borderLine.addLine(line);
        return lines.add(line);
    }

    public int addTriangle(Triangle triangle) {
        return triangles.add(triangle);
    }

    public IDable<Point> getPoints() {
        return points;
    }

    public IDable<Line> getLines() {
        return lines;
    }

    public IDable<Triangle> getTriangulate() {
        return triangles;
    }

    public void deleteLine(int idLine) {
        borderLine.removeLine(lines.getById(idLine).value);
        lines.remove(idLine);
    }

    public void deleteTriangle(int idTriangle) {
        triangles.remove(idTriangle);
    }

    public IDable.Element[] getTrianglesByLine(Line line) throws Exception {
        IDable.Element[] tri = new IDable.Element[2];
        int presentPosition = 0;

        for (IDable<Triangle>.Element<Triangle> triangle : triangles) {
            Line[] lines = triangle.value.getLines();
            for (Line singleLine : lines) {
                if (line.equals(singleLine)) {
                    tri[presentPosition++] = triangle;
                    if (presentPosition == 2) {
                        return tri;
                    }
                }
            }
        }
        if (presentPosition == 0)
            throw new Exception("Line without triangle. line = " + line);
        return new IDable.Element[]{tri[0]};
    }

    private Point[] getPointsByTriangle(Triangle triangle) {
        Point[] points = new Point[3];
        for (int i = 0; i < triangle.getPointsId().size(); i++) {
            points[i] = this.points.getById(triangle.getPointsId().get(i)).value;
        }
        return points;
    }


    public int sizePoints() {
        return points.size();
    }

    public int sizeLines() {
        return lines.size();
    }

    public int sizeTriangles() {
        return triangles.size();
    }

    public IDable<Line>.Element<Line> getPointOnLine(IDable<Point>.Element<Point> nextPoint) {
        if (lines == null)
            return null;
        if (lines.isEmpty())
            return null;

        for (IDable<Line>.Element<Line> line : lines) {
            Point pointA = this.points.getById(line.value.getIdPointA()).value;
            Point pointB = this.points.getById(line.value.getIdPointB()).value;
            if (GeometryPointLine.statePointOnLine(nextPoint.value.getX(), nextPoint.value.getY(),
                    pointA, pointB)
                    == GeometryPointLine.PointLineState.POINT_ON_LINE) {
                return line;
            }
        }

        return null;
    }

    public IDable.Element getPointInTriangle(IDable<Point>.Element<Point> nextPoint) {
        if (triangles == null)
            return null;
        if (triangles.isEmpty())
            return null;

        for (IDable<Triangle>.Element<Triangle> triangle : triangles) {
            Point[] points = getPointsByTriangle(triangle.value);
            if (GeometryPointTriangle.isPointInTriangle(nextPoint.value, points) ==
                    GeometryPointTriangle.PointTriangleState.POINT_INSIDE) {
                return triangle;
            }
        }

        return null;
    }

    public Point getPoints(int idPoint) {
        return points.getById(idPoint).value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Mesh :\n");

        for (int i = 0; i < 20; i++) {
            builder.append("-");
        }
        builder.append("\n");

        builder.append("Points :\n");
        for (IDable<Point>.Element<Point> point : points) {
            builder.append("ID").append(point.id).append(" : ").append(point.value);
        }

        for (int i = 0; i < 20; i++) {
            builder.append("-");
        }
        builder.append("\n");

        builder.append("Line :\n");
        for (IDable<Line>.Element<Line> line : lines) {
            builder.append("ID").append(line.id).append(" : ").append(line.value).append("\n");
        }

        for (int i = 0; i < 20; i++) {
            builder.append("-");
        }
        builder.append("\n");

        builder.append("Triangles :\n");
        for (IDable<Triangle>.Element<Triangle> triangle : triangles) {
            builder.append("ID").append(triangle.id).append(" : ").append(triangle.value).append("\n");
        }

        return builder.toString();
    }

    public List<Line> getBorderSegment(Point nextPoint) throws Exception {
        return borderLine.getBorderSegment(nextPoint);
    }
}
