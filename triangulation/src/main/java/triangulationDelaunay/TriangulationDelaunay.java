package triangulationDelaunay;

import triangulation.Triangulation;
import triangulation.elements.Collections.IDable;
import triangulation.elements.Line;
import triangulation.elements.Point;
import triangulation.elements.Triangle;
import triangulation.geometry.Geometry;
import triangulation.geometry.GeometryLineLine;

import java.util.List;

public class TriangulationDelaunay extends Triangulation {
    public TriangulationDelaunay(List<Point> points) throws Exception {
        super(points);
    }

    @Override
    protected void addNextPoint(IDable<Point>.Element<Point> nextPoint) throws Exception {
        super.addNextPoint(nextPoint);
        //todo delaunayChecking();
    }

    @Override
    protected void addNextPointInTriangle(IDable<Point>.Element<Point> nextPoint, IDable<Triangle>.Element<Triangle> triangle) throws Exception {
        Line[] lines = triangle.value.getLines();
        super.addNextPointInTriangle(nextPoint, triangle);
        for (int i = 0; i < lines.length; i++) {
            IDable<Triangle>.Element<Triangle> triangles[] = mesh.getTrianglesByLine(lines[i]);
            if (triangles.length == 2) {
                if (Geometry.isPointInCircle(new Point[]
                                {
                                        getMesh().getPoints(triangles[0].value.getIdPoint1()),
                                        getMesh().getPoints(triangles[0].value.getIdPoint2()),
                                        getMesh().getPoints(triangles[0].value.getIdPoint3())
                                },
                        getMesh().getPoints(triangles[1].value.getPointAntogonist(lines[i])))) {
                    flip(lines[i], triangles);
                }
            }
        }
    }

    private void flip(Line line, IDable<Triangle>.Element<Triangle>[] triangles) throws Exception {
        if (GeometryLineLine.stateLineLine(
                getMesh().getPoints(line.getIdPointA()),
                getMesh().getPoints(line.getIdPointB()),
                getMesh().getPoints(triangles[0].value.getPointAntogonist(line)),
                getMesh().getPoints(triangles[1].value.getPointAntogonist(line))) != GeometryLineLine.IntersectState.INTERSECT) {
            return;
        }
        Triangle triangle1 = new Triangle(triangles[0].value.getPointAntogonist(line), triangles[1].value.getPointAntogonist(line), line.getIdPointA());
        Triangle triangle2 = new Triangle(triangles[0].value.getPointAntogonist(line), triangles[1].value.getPointAntogonist(line), line.getIdPointB());

        getMesh().addLine(new Line(
                triangles[0].value.getPointAntogonist(line), triangles[1].value.getPointAntogonist(line)), false);
        getMesh().deleteLine(line);

        getMesh().addTriangle(triangle1);
        getMesh().addTriangle(triangle2);
        getMesh().deleteTriangle(triangles[0].id);
        getMesh().deleteTriangle(triangles[1].id);
    }


}
