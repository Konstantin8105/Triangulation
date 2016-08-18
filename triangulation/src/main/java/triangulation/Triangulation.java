package triangulation;

import imp.iTriangulation;
import triangulation.border.BorderBox;
import triangulation.elements.Collections.IDable;
import triangulation.elements.Line;
import triangulation.elements.Mesh;
import triangulation.elements.Point;
import triangulation.elements.Triangle;
import triangulation.geometry.GeometryCoordinate;

import java.util.ArrayList;
import java.util.List;

public class Triangulation implements iTriangulation {
    protected Mesh mesh = new Mesh();
    private BorderBox bBox = new BorderBox();

    public Triangulation(List<Point> points) throws Exception {
        mesh.addPoint(points);

        for (IDable<Point>.Element<Point> pointElement : mesh.getPoints()) {
            addNextPoint(pointElement);
        }
    }

    private final boolean MAY_BE_BORDER = true;
    private final boolean NOT_BORDER = true;

    private List<IDable.Element> lastPoints = new ArrayList<>();
    private boolean isNeedLastPointsSaving = true;

    protected void addNextPoint(IDable<Point>.Element<Point> nextPoint) throws Exception {
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

            IDable<?>.Element<?> result = mesh.pointInRegion(nextPoint);
            if (result != null) {
                if (result.value instanceof Triangle) {
                    IDable<Triangle>.Element<Triangle> triangle = (IDable<Triangle>.Element<Triangle>) result;
                    addNextPointInTriangle(nextPoint, triangle);
                    bBox.addPoint(nextPoint.value);
                    return;
                } else if (result.value instanceof Line) {
                    IDable<Line>.Element<Line> line = (IDable<Line>.Element<Line>) result;
                    addNextPointOnLine(nextPoint, line);
                    bBox.addPoint(nextPoint.value);
                    return;
                }
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
        mesh.addLine(new Line(point.get(0).id, point.get(1).id), MAY_BE_BORDER);
        mesh.addLine(new Line(point.get(1).id, point.get(2).id), MAY_BE_BORDER);
        mesh.addLine(new Line(point.get(2).id, point.get(0).id), MAY_BE_BORDER);
        mesh.addTriangle(new Triangle(
                point.get(0).id,
                point.get(1).id,
                point.get(2).id));
    }

    private void addNextPointOnLine(IDable.Element nextPoint, IDable.Element line) throws Exception {

        IDable.Element[] triangles = mesh.getTrianglesByLine((Line) line.value);
        if (triangles.length > 2 || triangles.length < 1) {
            StringBuilder str = new StringBuilder();
            for (IDable.Element triangle : triangles) {
                str.append("ID").append(triangle.id);
                str.append(" : ");
                str.append(triangle.value);
            }
            throw new Exception(
                    "Cannot more 2 triangles for 1 line. triangles" + str
                            + " . line = " + line.toString());
        }

        mesh.addLine(new Line(nextPoint.id, ((Line) line.value).getIdPointA()), MAY_BE_BORDER);
        mesh.addLine(new Line(nextPoint.id, ((Line) line.value).getIdPointB()), MAY_BE_BORDER);
        for (IDable.Element triangle1 : triangles) {
            Triangle triangle = ((Triangle) triangle1.value);
            if (triangle.getIdPoint1() != ((Line) line.value).getIdPointA() && triangle.getIdPoint1() != ((Line) line.value).getIdPointB()) {
                mesh.addTriangle(new Triangle(nextPoint.id, triangle.getIdPoint1(), ((Line) line.value).getIdPointA()));
                mesh.addTriangle(new Triangle(nextPoint.id, triangle.getIdPoint1(), ((Line) line.value).getIdPointB()));
                mesh.addLine(new Line(nextPoint.id, triangle.getIdPoint1()), NOT_BORDER);
            } else if (triangle.getIdPoint2() != ((Line) line.value).getIdPointA() && triangle.getIdPoint2() != ((Line) line.value).getIdPointB()) {
                mesh.addTriangle(new Triangle(nextPoint.id, triangle.getIdPoint2(), ((Line) line.value).getIdPointA()));
                mesh.addTriangle(new Triangle(nextPoint.id, triangle.getIdPoint2(), ((Line) line.value).getIdPointB()));
                mesh.addLine(new Line(nextPoint.id, triangle.getIdPoint2()), NOT_BORDER);
            } else {
                mesh.addTriangle(new Triangle(nextPoint.id, triangle.getIdPoint3(), ((Line) line.value).getIdPointA()));
                mesh.addTriangle(new Triangle(nextPoint.id, triangle.getIdPoint3(), ((Line) line.value).getIdPointB()));
                mesh.addLine(new Line(nextPoint.id, triangle.getIdPoint3()), NOT_BORDER);
            }
            mesh.deleteTriangle(triangle1.id);
        }
        mesh.deleteLine(line.id);
    }

    protected void addNextPointInTriangle(IDable<Point>.Element<Point> nextPoint, IDable<Triangle>.Element<Triangle> triangle) throws Exception {
        Triangle oldTriangle = (Triangle) triangle.value;
        mesh.addLine(new Line(nextPoint.id, oldTriangle.getIdPoint1()), NOT_BORDER);
        mesh.addLine(new Line(nextPoint.id, oldTriangle.getIdPoint2()), NOT_BORDER);
        mesh.addLine(new Line(nextPoint.id, oldTriangle.getIdPoint3()), NOT_BORDER);

        mesh.addTriangle(new Triangle(nextPoint.id, oldTriangle.getIdPoint1(), oldTriangle.getIdPoint2()));
        mesh.addTriangle(new Triangle(nextPoint.id, oldTriangle.getIdPoint2(), oldTriangle.getIdPoint3()));
        mesh.addTriangle(new Triangle(nextPoint.id, oldTriangle.getIdPoint3(), oldTriangle.getIdPoint1()));

        mesh.deleteTriangle(triangle.id);
    }

    private void addNextPointOutside(IDable.Element nextPoint) throws Exception {
        List<Line> lines = mesh.getBorderSegment((Point) nextPoint.value);
        mesh.addLine(new Line(nextPoint.id, lines.get(0).getIdPointA()), MAY_BE_BORDER);
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            if (i == lines.size() - 1) {
                mesh.addLine(new Line(nextPoint.id, line.getIdPointB()), MAY_BE_BORDER);
            } else {
                mesh.addLine(new Line(nextPoint.id, line.getIdPointB()), NOT_BORDER);
            }
            mesh.addTriangle(new Triangle(
                    nextPoint.id,
                    line.getIdPointA(),
                    line.getIdPointB()));
        }
    }

    @Override
    public List<Point[]> getTriangles() {
        List<Point[]> triangles = new ArrayList<>();
        for (IDable<Triangle>.Element<Triangle> triangle : mesh.getTriangulate()) {
            Point[] points = new Point[3];
            for (int i = 0; i < 3; i++) {
                points[i] = new Point(mesh.getPoints((int) triangle.value.getPointsId().get(i)));
            }
        }
        return triangles;
    }
}
