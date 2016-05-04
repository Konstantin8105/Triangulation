package elements;

import elements.Collections.IDable;

public class Mesh {
    private IDable<Point> points;
    private IDable<Line> lines;
    private IDable<Triangle> triangles;

    public void addPoint(Point point) {
        points.add(point);
    }

    public void addLine(Line line) {
        lines.add(line);
    }

    public void addTriangle(Triangle triangle) {
        triangles.add(triangle);
    }

    public void deletePoint(int id) {
        points.remove(id);
    }

    public void deleteLine(int id) {
        lines.remove(id);
    }

    public void deleteTriangle(int id) {
        triangles.remove(id);
    }

    public void deleteSamePoints(){
        // TODO: 5/4/16
    }
}
