package triangulationAdvance;

import meshview.MeshView;
import research.ResearchTest;

import java.util.List;

public class TriangulationAdvanceView {

    public static void main(String[] args) throws Exception {
        int AMOUNT_POINTS = 1_000;
        {
            List<Point> points = ResearchTest.getRandomPoints(AMOUNT_POINTS);
            TriangulationAdvance triangulation = new TriangulationAdvance((Point[]) points.toArray());
            MeshView meshView = new MeshView(triangulation.getTriangles());
        }
        {
            List<Point> points = ResearchTest.getCirclePoints(AMOUNT_POINTS);
            TriangulationAdvance triangulation = new TriangulationAdvance((Point[])points.toArray());
            MeshView meshView = new MeshView(triangulation.getTriangles());
        }
        {
            List<Point> points = ResearchTest.getLineOnLine(AMOUNT_POINTS);
            TriangulationAdvance triangulation = new TriangulationAdvance((Point[])points.toArray());
            MeshView meshView = new MeshView(triangulation.getTriangles());
        }
        {
            List<Point> points = ResearchTest.getInTriangles(AMOUNT_POINTS);
            TriangulationAdvance triangulation = new TriangulationAdvance((Point[])points.toArray());
            MeshView meshView = new MeshView(triangulation.getTriangles());
        }
    }
}
