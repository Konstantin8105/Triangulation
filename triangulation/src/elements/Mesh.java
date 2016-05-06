package elements;

import elements.Collections.IDable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Mesh {
    private IDable<Point> points = new IDable<>();
    protected IDable<Line> lines = new IDable<>();
    protected IDable<Triangle> triangles = new IDable<>();

    public void addPoint(List<Point> point) {
        points.add(point);
    }

    public int addLine(Line line) throws Exception {
        return lines.add(line);
    }

    public int addTriangle(Triangle triangle) throws Exception {
        return triangles.add(triangle);
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
            Line[] lines = triangles.getByIndex(i).getLines();
            for (int j = 0; j < lines.length; j++) {
                if (((Line) line.value).equals(lines[j])) {
                    tri[presentPosition++] = triangles.getElement(i);
                    if (presentPosition == 2) {
                        return tri;
                    }
                }
            }
        }
        if (presentPosition == 0)
            throw new Exception("Line without triangle. line = " + (Line) line.value);
        return new IDable.Element[]{tri[0]};
    }

    public Point getPoints(int idPoint) {
        return points.getById(idPoint);
    }

    public List<IDable.Element> getLines(Point point) throws Exception {
        return lines.getListElements();
    }

    public List<elements.Collections.IDable.Element> getTriangles(Point point) throws Exception {
        return triangles.getListElements();
    }

    public List<elements.Collections.IDable.Element> getTriangles() throws Exception {
        return triangles.getListElements();
    }

    public Point[] getPointsByTriangle(Triangle triangle) {
        Point[] points = new Point[3];
        for (int i = 0; i < triangle.getPointsId().size(); i++) {
            points[i] = getPoints(triangle.getPointsId().get(i));
        }
        return points;
    }

    public List<Line> getBorderLine() throws Exception {
        List<Line> borderLines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (getTrianglesByLine(lines.getElement(i)).length == 1) {
                borderLines.add(lines.getByIndex(i));
            }
        }
        if(borderLines.size() == 0)
            throw new Exception("Border don`t found any lines");
        return borderLines;
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
}
