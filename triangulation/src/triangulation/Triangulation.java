package triangulation;

import triangulation.border.BorderBox;
import triangulation.elements.Collections.IDable;
import triangulation.elements.Line;
import triangulation.elements.Mesh;
import triangulation.elements.Point;
import triangulation.elements.Triangle;
import triangulation.geometry.GeometryCoordinate;

import java.util.ArrayList;
import java.util.List;

public class Triangulation {
    private Mesh mesh = new Mesh();
    private BorderBox bBox = new BorderBox();

    public Triangulation(List<Point> points) throws Exception {
        mesh.addPoint(points);

        for (IDable<Point>.Element<Point> pointElement : mesh.getPoints()) {
            addNextPoint(pointElement);
        }
    }

    private List<IDable.Element> lastPoints = new ArrayList<>();
    private boolean isNeedLastPointsSaving = true;

    private void addNextPoint(IDable<Point>.Element<Point> nextPoint) throws Exception {
        if (isNeedLastPointsSaving) {
            lastPoints.add(nextPoint);
            if (lastPoints.size() < 3) {
                bBox.addPoint(nextPoint.value);
                return;
            }
            addNextPointWithoutBorder();
            bBox.addPoint(nextPoint.value);
            return;
        }

        if (bBox.isInBox(nextPoint.value)) {

            IDable<Line>.Element<Line> line = mesh.getPointOnLine(nextPoint);
            if (line != null) {
                addNextPointOnLine(nextPoint, line);
                bBox.addPoint(nextPoint.value);
                return;
            }

            IDable<Triangle>.Element<Triangle> triangle = mesh.getPointInTriangle(nextPoint);
            if (triangle != null) {
                addNextPointInTriangle(nextPoint, triangle);
                bBox.addPoint(nextPoint.value);
                return;
            }
        }

        addNextPointOutside(nextPoint);

        bBox.addPoint(nextPoint.value);
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
        if (triangles.length > 2 || triangles.length < 1) {
            StringBuilder str = new StringBuilder();
            for(IDable.Element triangle: triangles) {
                str.append("ID").append(triangle.id);
                str.append(" : ");
                str.append(triangle.value);
            }
            throw new Exception(
                    "Cannot more 2 triangles for 1 line. triangles" + str
                            + " . line = " + line.toString());
        }

        mesh.addLine(new Line(nextPoint.id, ((Line) line.value).getIdPointA()));
        mesh.addLine(new Line(nextPoint.id, ((Line) line.value).getIdPointB()));
        for (IDable.Element triangle1 : triangles) {
            Triangle triangle = ((Triangle) triangle1.value);
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
            mesh.deleteTriangle(triangle1.id);
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
        List<Line> lines = mesh.getBorderSegment((Point) nextPoint.value);
        mesh.addLine(new Line(nextPoint.id, lines.get(0).getIdPointA()));
        for (Line line : lines) {
            mesh.addLine(new Line(nextPoint.id, line.getIdPointB()));
            mesh.addTriangle(new Triangle(
                    nextPoint.id,
                    line.getIdPointA(),
                    line.getIdPointB()));
        }
    }

    public Mesh getMesh() {
        return mesh;
    }
}
