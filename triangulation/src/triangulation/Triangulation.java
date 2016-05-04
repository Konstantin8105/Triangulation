package triangulation;

import elements.Collections.IDable;
import elements.Line;
import elements.Mesh;
import elements.Point;
import elements.Triangle;
import geometry.GeometryCoordinate;

import java.util.*;

public class Triangulation {
    private Mesh mesh;
    private BorderBox bBox = new BorderBox();

    public Triangulation(List<Point> points) throws Exception {
        // ----
        // Delete same points
        // ----
        Set<Point> set = new HashSet<>(points);
        points = new ArrayList<>(set);

        mesh.addPoint(points);
        Iterator<IDable.Element> iterator = mesh.getPoints().iterator();
        while (iterator.hasNext()) {
            addNextPoint(iterator.next());
        }
    }

    private List<IDable.Element> lastPoints = new ArrayList<>();
    private boolean isNeedLastPointsSaving = true;

    private void addNextPoint(IDable.Element nextPoint) throws Exception {
        if (isNeedLastPointsSaving) {
            lastPoints.add(nextPoint);
            if (lastPoints.size() < 3)
                return;
            addNextPointWithoutBorder();
        }

        if (bBox.isInBox((Point) nextPoint.value)) {

            IDable.Element line = getPointOnLine(nextPoint);
            if (line != null) {
                addNextPointOnLine(nextPoint, line);
                return;
            }

            IDable.Element triangle = getPointInTriangle(nextPoint);
            if (triangle != null) {
                addNextPointInTriangle(nextPoint, triangle);
                return;
            }
        }
        // point is outside of triangles
        addNextPointOutside(nextPoint);

        bBox.addPoint((Point) nextPoint.value);
    }

    private void addNextPointWithoutBorder() throws Exception {
        // if all points on NOT one line
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < lastPoints.size(); i++) {
            points.add((Point) lastPoints.get(i).value);
        }
        if (GeometryCoordinate.isPointsOnOneLine(points)) {
            return;
        }
        isNeedLastPointsSaving = false;
        // create line, triangles between all points
        List<IDable.Element> point = new ArrayList<>();
        point.add(lastPoints.get(lastPoints.size() - 1));
        point.add(lastPoints.get(lastPoints.size() - 2));
        point.add(lastPoints.get(lastPoints.size() - 3));

        addSimpleTriangle(point);

        for (int i = 0; i < lastPoints.size() - 3; i++) {
            addNextPoint(lastPoints.get(i));
        }
    }

    private void addSimpleTriangle(List<IDable.Element> point) throws Exception {
        mesh.addLine(new Line(point.get(0).id, point.get(1).id));
        mesh.addLine(new Line(point.get(1).id, point.get(2).id));
        mesh.addLine(new Line(point.get(2).id, point.get(0).id));
        mesh.addTriangle(new Triangle(
                point.get(0).id,
                point.get(1).id,
                point.get(2).id));
    }

    private void addNextPointOnLine(IDable.Element nextPoint, IDable.Element line) throws Exception {

        IDable.Element[] triangles = mesh.getTrianglesByLine(line);
        if (triangles.length > 2 || triangles.length < 1)
            throw new Exception("Cannot more 2 triangles for 1 line. triangles" + triangles + " . line = " + line);

        mesh.addLine(new Line(nextPoint.id, ((Line) line.value).getIdPointA()));
        mesh.addLine(new Line(nextPoint.id, ((Line) line.value).getIdPointB()));
        for (int i = 0; i < triangles.length; i++) {
            Triangle triangle = ((Triangle) triangles[i].value);
            if (triangle.getIdPoint1() != ((Line) line.value).getIdPointA() || triangle.getIdPoint1() != ((Line) line.value).getIdPointB()) {
                mesh.addTriangle(new Triangle(nextPoint.id, triangle.getIdPoint1(), ((Line) line.value).getIdPointA()));
                mesh.addTriangle(new Triangle(nextPoint.id, triangle.getIdPoint1(), ((Line) line.value).getIdPointB()));
            } else if (triangle.getIdPoint2() != ((Line) line.value).getIdPointA() || triangle.getIdPoint2() != ((Line) line.value).getIdPointB()) {
                mesh.addTriangle(new Triangle(nextPoint.id, triangle.getIdPoint2(), ((Line) line.value).getIdPointA()));
                mesh.addTriangle(new Triangle(nextPoint.id, triangle.getIdPoint2(), ((Line) line.value).getIdPointB()));
            } else {
                mesh.addTriangle(new Triangle(nextPoint.id, triangle.getIdPoint3(), ((Line) line.value).getIdPointA()));
                mesh.addTriangle(new Triangle(nextPoint.id, triangle.getIdPoint3(), ((Line) line.value).getIdPointB()));
            }
            mesh.deleteTriangle(triangles[i].id);
        }
        mesh.deleteLine(line.id);
    }

    private void addNextPointInTriangle(IDable.Element nextPoint, IDable.Element triangle) throws Exception {
        Triangle oldTriangle = (Triangle) triangle.value;
        mesh.addLine(new Line(nextPoint.id, oldTriangle.getIdPoint1()));
        mesh.addLine(new Line(nextPoint.id, oldTriangle.getIdPoint2()));
        mesh.addLine(new Line(nextPoint.id, oldTriangle.getIdPoint3()));

        mesh.addTriangle(new Triangle(nextPoint.id, oldTriangle.getIdPoint1(), oldTriangle.getIdPoint2()));
        mesh.addTriangle(new Triangle(nextPoint.id, oldTriangle.getIdPoint2(), oldTriangle.getIdPoint3()));
        mesh.addTriangle(new Triangle(nextPoint.id, oldTriangle.getIdPoint3(), oldTriangle.getIdPoint1()));

        mesh.deleteTriangle(triangle.id);
    }

    private void addNextPointOutside(IDable.Element nextPoint) throws Exception {
        BorderLineConvexRegion borderLinesConvexRegion = mesh.getBorderLines();

        List<IDable.Element> lines = getBorderLinesForNewConvex((Point) nextPoint.value, borderLinesConvexRegion);
        for (int i = 0; i < lines.size(); i++) {
            mesh.addLine(new Line(nextPoint.id, ((Line) lines.get(i).value).getIdPointA()));
            mesh.addLine(new Line(nextPoint.id, ((Line) lines.get(i).value).getIdPointB()));
            mesh.addTriangle(new Triangle(
                    nextPoint.id,
                    ((Line) lines.get(i).value).getIdPointA(),
                    ((Line) lines.get(i).value).getIdPointB()));
        }
    }

    private List<IDable.Element> getBorderLinesForNewConvex(Point nextPoint, BorderLineConvexRegion border) {
        // TODO: 5/4/16
    }


    private IDable.Element getPointOnLine(IDable.Element nextPoint) {
        // TODO: 5/4/16
    }

    private IDable.Element getPointInTriangle(IDable.Element nextPoint) {
        // TODO: 5/4/16
    }

}
