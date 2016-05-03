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


}
