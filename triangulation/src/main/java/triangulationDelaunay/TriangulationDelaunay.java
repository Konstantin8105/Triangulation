package triangulationDelaunay;

import triangulation.Triangulation;
import triangulation.elements.Collections.IDable;
import triangulation.elements.Line;
import triangulation.elements.Point;
import triangulation.elements.Triangle;

import java.util.List;

public class TriangulationDelaunay extends Triangulation {
    public TriangulationDelaunay(List<Point> points) throws Exception {
        super(points);
    }

    @Override
    protected void addNextPointInTriangle(IDable<Point>.Element<Point> nextPoint, IDable<Triangle>.Element<Triangle> triangle) throws Exception {
        super.addNextPointInTriangle(nextPoint, triangle);
        Line[] lines = triangle.value.getLines();
        Triangle[] outTriangles[] = new Triangle[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            IDable<Triangle>.Element<Triangle> triangles[] = mesh.getTrianglesByLine(lines[i]);
            for (int j = 0; j < triangles.length; j++) {

            }
            //outTriangles[i] =
        }
        int[] idPointAntagonists = triangle.value.getPointAntagonists();
        for (int i = 0; i < lines.length; i++) {

        }

    }
}
