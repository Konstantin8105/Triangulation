package elements;

import elements.Collections.IDable;
import triangulation.BorderLineConvexRegion;

import java.util.List;

public class Mesh {
    private IDable<Point> points = new IDable<>();
    private IDable<Line> lines = new IDable<>();
    private IDable<Triangle> triangles = new IDable<>();

    public void addPoint(List<Point> point) {
        points.add(point);
    }

    public void addLine(Line line) {
        lines.add(line);
    }

    public void addTriangle(Triangle triangle) {
        triangles.add(triangle);
    }

    public IDable<Point> getPoints() {
        return points;
    }

    public void deleteLine(int id) {
        lines.remove(id);
    }

    public void deleteTriangle(int id) {
        triangles.remove(id);
    }

    public IDable.Element[] getTrianglesByLine(IDable.Element line) throws Exception {
        IDable.Element[] tri = new IDable.Element[2];
        int presentPosition = 0;
        for (int i = 0; i < triangles.size(); i++) {
            Line[] lines = triangles.get(i).getLines();
            for (int j = 0; j < lines.length; j++) {
                if (((Line) line.value).equals(lines[j])) {
                    tri[presentPosition++] = triangles.getElement(i);
                    if (presentPosition == 2) {
                        return tri;
                    }
                }
            }
        }
        return new IDable.Element[]{tri[0]};
    }

    public Point getPoints(int idPoint) {
        return points.get(idPoint);
    }

    public List<IDable.Element> getLines(Point point) {
        return lines.getListElements();
    }

    public List<IDable.Element> getTriangles(Point point) {
        return triangles.getListElements();
    }

    public Point[] getPointsByTriangle(Triangle triangle) {
        Point[] points = new Point[3];
        for (int i = 0; i < triangle.getPointsId().size(); i++) {
            points[i] = getPoints(triangle.getPointsId().get(i));
        }
        return points;
    }

    public BorderLineConvexRegion getBorderLines() {
        // TODO: 5/4/16
    }
}
