package triangulation;

import elements.Collections.IDable;
import elements.Line;
import elements.Mesh;
import elements.Point;
import elements.Triangle;
import geometry.GeometryCoordinate;
import geometry.GeometryLineLine;
import geometry.GeometryPointLine;
import geometry.GeometryPointTriangle;

import java.util.*;

public class Triangulation {
    private Mesh mesh;
    private BorderBox bBox = new BorderBox();

    public Triangulation(List<Point> points) throws Exception {
        // Delete same points
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

        addNextPointOutside(nextPoint);

        bBox.addPoint((Point) nextPoint.value);
    }

    private void addNextPointWithoutBorder() throws Exception {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < lastPoints.size(); i++) {
            points.add((Point) lastPoints.get(i).value);
        }
        if (GeometryCoordinate.isPointsOnOneLine(points)) {
            return;
        }
        isNeedLastPointsSaving = false;

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

        List<Line> lines = getBorderLinesForNewConvex((Point) nextPoint.value, borderLinesConvexRegion);
        mesh.addLine(new Line(nextPoint.id, lines.get(0).getIdPointA()));
        for (int i = 0; i < lines.size(); i++) {
            mesh.addLine(new Line(nextPoint.id, lines.get(i).getIdPointB()));
            mesh.addTriangle(new Triangle(
                    nextPoint.id,
                    lines.get(i).getIdPointA(),
                    lines.get(i).getIdPointB()));
        }
    }

    private IDable.Element getPointOnLine(IDable.Element nextPoint) {
        List<IDable.Element> listLines = mesh.getLines((Point) nextPoint.value);
        if (listLines == null)
            return null;
        if (listLines.size() == 0)
            return null;

        for (IDable.Element line : listLines) {
            Point pointA = mesh.getPoints(((Line) line.value).getIdPointA());
            Point pointB = mesh.getPoints(((Line) line.value).getIdPointB());
            if (GeometryPointLine.statePointOnLine(((Point) nextPoint.value).getX(), ((Point) nextPoint.value).getY(),
                    pointA, pointB)
                    == GeometryPointLine.PointLineState.POINT_ON_LINE) {
                return line;
            }
        }
        return null;
    }

    private IDable.Element getPointInTriangle(IDable.Element nextPoint) {
        List<IDable.Element> listTriangles = mesh.getTriangles((Point) nextPoint.value);
        if (listTriangles == null)
            return null;
        if (listTriangles.size() == 0)
            return null;

        for (IDable.Element triangle : listTriangles) {
            Point[] points = mesh.getPointsByTriangle((Triangle)triangle.value);
            if (GeometryPointTriangle.isPointInTriangle((Point) nextPoint.value, points) ==
                    GeometryPointTriangle.PointTriangleState.POINT_INSIDE) {
                return triangle;
            }
        }
        return null;
    }

    private List<Line> getBorderLinesForNewConvex(Point nextPoint, BorderLineConvexRegion border) throws Exception {
        List<Line> lines = border.getBorderLine();

        Point[] pointsOfLine = new Point[lines.size() + 1];
        pointsOfLine[0] = mesh.getPoints(lines.get(0).getIdPointA());
        for (int i = 0; i < lines.size(); i++) {
            pointsOfLine[i + 1] = mesh.getPoints(lines.get(i).getIdPointB());
        }
        pointsOfLine[lines.size()] = pointsOfLine[0];

        Point[] pointsMiddleOfLine = new Point[lines.size()];
        for (int i = 0; i < pointsMiddleOfLine.length; i++) {
            pointsMiddleOfLine[i] = Point.middlePoint(pointsOfLine[i], pointsOfLine[i + 1]);
        }

        List<Integer> indexLinesDelete = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            GeometryLineLine.IntersectState state = GeometryLineLine.stateLineLine(
                    nextPoint,
                    pointsMiddleOfLine[i],
                    pointsOfLine[i],
                    pointsOfLine[i + 1]);
            if (state == GeometryLineLine.IntersectState.INTERSECT ||
                    state == GeometryLineLine.IntersectState.INTERSECT_POINT_ON_LINE ||
                    state == GeometryLineLine.IntersectState.LINE_IN_LINE) {
                indexLinesDelete.add(i);
            }
        }

        for (int i = indexLinesDelete.size() - 1; i >= 0; i++) {
            lines.remove(indexLinesDelete.get(i));
        }

        return lines;
    }

}
