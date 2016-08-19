package triangulationAdvance;

import meshview.MeshView;
import research.ResearchTest;
import triangulation.Triangulation;
import triangulation.elements.Point;

import java.util.List;

public class TriangulationsCompare {

    public static void main(String[] args) throws Exception {
        int AMOUNT_POINTS = 100;
        List<Point> points = ResearchTest.getRandomPoints(AMOUNT_POINTS);
        {
            Triangulation triangulation = new Triangulation(points);
            MeshView meshView = new MeshView(triangulation.getTriangles());
        }
        {
            TriangulationAdvance triangulation = new TriangulationAdvance((Point[]) points.toArray());
            MeshView meshView = new MeshView(triangulation.getTriangles());
        }
    }
}
