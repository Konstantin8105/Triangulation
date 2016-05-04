package elements;

import elements.Collections.IDable;
import triangulation.BorderLine;
import triangulation.BorderLineConvexRegion;

import java.util.List;

public class Mesh {
    private IDable<Point> points = new IDable<>();
    private IDable<Line> lines = new IDable<>();
    private IDable<Triangle> triangles = new IDable<>();
/*

    public void addPoint(Point point) {
        points.add(point);
    }
*/

    public void addPoint(List<Point> point) {
        points.add(point);
    }

    public void addLine(Line line) {
        lines.add(line);
    }

    public void addTriangle(Triangle triangle) {
        triangles.add(triangle);
    }

    /*

        public void deletePoint(int id) {
            points.remove(id);
        }

    */
    public IDable<Point> getPoints() {
        return points;
    }

    public void deleteLine(int id) {
        lines.remove(id);
    }

    public void deleteTriangle(int id) {
        triangles.remove(id);
    }
/*
    public void deleteTriangle(IDable.Element[] triangles) {
        for (int i = 0; i < triangles.length; i++) {
            if(triangles[i] != null)
                deleteTriangle(triangles[i].id);
        }
    }*/

    public void deleteSamePoints() {
        // TODO: 5/4/16
    }

    public IDable.Element[] getTrianglesByLine(IDable.Element line) {
        // TODO: 5/4/16
    }

    public BorderLineConvexRegion getBorderLines() {
        // TODO: 5/4/16
    }
}
