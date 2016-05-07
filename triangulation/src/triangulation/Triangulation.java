package triangulation;

import triangulation.elements.Collections.IDable;
import triangulation.elements.Line;
import triangulation.elements.Mesh;
import triangulation.elements.Point;
import triangulation.elements.Triangle;
import triangulation.geometry.GeometryCoordinate;
import triangulation.geometry.GeometryLineLine;
import triangulation.geometry.GeometryPointLine;
import triangulation.geometry.GeometryPointTriangle;

import java.util.ArrayList;
import java.util.List;

public class Triangulation {
    private Mesh mesh = new Mesh();
    private BorderBox bBox = new BorderBox();

    public Triangulation(List<Point> points) throws Exception {
        mesh.addPoint(points);

        List<triangulation.elements.Collections.IDable.Element> pointArray = mesh.getPoints().getListElements();
        for (IDable.Element aPointArray : pointArray) {
            addNextPoint(aPointArray);
        }
    }

    private List<IDable.Element> lastPoints = new ArrayList<>();
    private boolean isNeedLastPointsSaving = true;

    private void addNextPoint(IDable.Element nextPoint) throws Exception {
        if (isNeedLastPointsSaving) {
            lastPoints.add(nextPoint);
            if (lastPoints.size() < 3) {
                bBox.addPoint((Point) nextPoint.value);
                return;
            }
            addNextPointWithoutBorder();
            bBox.addPoint((Point) nextPoint.value);
            return;
        }

        if (bBox.isInBox((Point) nextPoint.value)) {

            IDable.Element line = getPointOnLine(nextPoint);
            if (line != null) {
                addNextPointOnLine(nextPoint, line);
                bBox.addPoint((Point) nextPoint.value);
                return;
            }

            IDable.Element triangle = getPointInTriangle(nextPoint);
            if (triangle != null) {
                addNextPointInTriangle(nextPoint, triangle);
                bBox.addPoint((Point) nextPoint.value);
                return;
            }
        }

        addNextPointOutside(nextPoint);

        bBox.addPoint((Point) nextPoint.value);
    }

    private void addNextPointWithoutBorder() throws Exception {
        List<Point> points = new ArrayList<>();
        for (IDable.Element lastPoint : lastPoints) {
            points.add((Point) lastPoint.value);
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

        IDable.Element[] triangles = mesh.getTrianglesByLine((Line) line.value);
        if (triangles.length > 2 || triangles.length < 1)
            throw new Exception(
                    "Cannot more 2 triangles for 1 line. triangles" + triangles
                            + " . line = " + line.toString());

        mesh.addLine(new Line(nextPoint.id, ((Line) line.value).getIdPointA()));
        mesh.addLine(new Line(nextPoint.id, ((Line) line.value).getIdPointB()));
        for (int i = 0; i < triangles.length; i++) {
            Triangle triangle = ((Triangle) triangles[i].value);
            if (triangle.getIdPoint1() != ((Line) line.value).getIdPointA() && triangle.getIdPoint1() != ((Line) line.value).getIdPointB()) {
                mesh.addTriangle(new Triangle(nextPoint.id, triangle.getIdPoint1(), ((Line) line.value).getIdPointA()));
                mesh.addTriangle(new Triangle(nextPoint.id, triangle.getIdPoint1(), ((Line) line.value).getIdPointB()));
                mesh.addLine(new Line(nextPoint.id, triangle.getIdPoint1()));
            } else if (triangle.getIdPoint2() != ((Line) line.value).getIdPointA() && triangle.getIdPoint2() != ((Line) line.value).getIdPointB()) {
                mesh.addTriangle(new Triangle(nextPoint.id, triangle.getIdPoint2(), ((Line) line.value).getIdPointA()));
                mesh.addTriangle(new Triangle(nextPoint.id, triangle.getIdPoint2(), ((Line) line.value).getIdPointB()));
                mesh.addLine(new Line(nextPoint.id, triangle.getIdPoint2()));
            } else {
                mesh.addTriangle(new Triangle(nextPoint.id, triangle.getIdPoint3(), ((Line) line.value).getIdPointA()));
                mesh.addTriangle(new Triangle(nextPoint.id, triangle.getIdPoint3(), ((Line) line.value).getIdPointB()));
                mesh.addLine(new Line(nextPoint.id, triangle.getIdPoint3()));
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
        List<Line> lines = getBorderLinesForNewConvex((Point) nextPoint.value);
        mesh.addLine(new Line(nextPoint.id, lines.get(0).getIdPointA()));
        for (Line line : lines) {
            mesh.addLine(new Line(nextPoint.id, line.getIdPointB()));
            mesh.addTriangle(new Triangle(
                    nextPoint.id,
                    line.getIdPointA(),
                    line.getIdPointB()));
        }
    }

    private IDable.Element getPointOnLine(IDable.Element nextPoint) throws Exception {
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

    private IDable.Element getPointInTriangle(IDable.Element nextPoint) throws Exception {
        List<IDable.Element> listTriangles = mesh.getTriangles((Point) nextPoint.value);
        if (listTriangles == null)
            return null;
        if (listTriangles.size() == 0)
            return null;

        for (IDable.Element triangle : listTriangles) {
            Point[] points = mesh.getPointsByTriangle((Triangle) triangle.value);
            if (GeometryPointTriangle.isPointInTriangle((Point) nextPoint.value, points) ==
                    GeometryPointTriangle.PointTriangleState.POINT_INSIDE) {
                return triangle;
            }
        }
        return null;
    }

    private List<Line> getBorderLinesForNewConvex(Point nextPoint) throws Exception {
        List<Line> borderLine = Mesh.createLoop(mesh.getBorderLine());

        Point[] pointsOfLine = new Point[borderLine.size() + 1];
        pointsOfLine[0] = mesh.getPoints(borderLine.get(0).getIdPointA());
        for (int i = 0; i < borderLine.size(); i++) {
            pointsOfLine[i + 1] = mesh.getPoints(borderLine.get(i).getIdPointB());
        }
        pointsOfLine[borderLine.size()] = pointsOfLine[0];

        Point[] pointsMiddleOfLine = new Point[borderLine.size()];
        for (int i = 0; i < pointsMiddleOfLine.length; i++) {
            pointsMiddleOfLine[i] = Point.middlePoint(pointsOfLine[i], pointsOfLine[i + 1]);
        }

        List<Integer> indexLinesDelete = new ArrayList<>();
        for (int i = 0; i < borderLine.size(); i++) {
            for (int j = 0; j < borderLine.size(); j++) {
                if (i != j) {
                    GeometryLineLine.IntersectState state = GeometryLineLine.stateLineLine(
                            nextPoint,
                            pointsMiddleOfLine[i],
                            pointsOfLine[j],
                            pointsOfLine[j + 1]);
                    if (state == GeometryLineLine.IntersectState.INTERSECT ||
                            state == GeometryLineLine.IntersectState.INTERSECT_POINT_ON_LINE ||
                            state == GeometryLineLine.IntersectState.LINE_IN_LINE) {
                        indexLinesDelete.add(i);
                        j = borderLine.size();
                    }
                }
            }

        }

        for (int i = indexLinesDelete.size() - 1; i >= 0; i--) {
            borderLine.remove((int) indexLinesDelete.get(i));
        }

        return Mesh.createSequence(borderLine);
    }

    public Mesh getMesh() {
        return mesh;
    }
}
