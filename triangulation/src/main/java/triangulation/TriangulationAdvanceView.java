package triangulation;

import meshview.MeshView;
import research.ResearchTest;
import triangulation.elements.Point;

import java.util.List;

public class TriangulationAdvanceView {

    public static void main(String[] args) throws Exception {
        int AMOUNT_POINTS = 1_000;
        {
            List<Point> points = ResearchTest.getRandomPoints(AMOUNT_POINTS);
            TriangulationDelaunay triangulation = new TriangulationDelaunay((Point[]) points.toArray());
            MeshView meshView = new MeshView(triangulation.getTriangles());
        }
        {
            List<Point> points = ResearchTest.getCirclePoints(AMOUNT_POINTS);
            TriangulationDelaunay triangulation = new TriangulationDelaunay((Point[])points.toArray());
            MeshView meshView = new MeshView(triangulation.getTriangles());
        }
        {
            List<Point> points = ResearchTest.getLineOnLine(AMOUNT_POINTS);
            TriangulationDelaunay triangulation = new TriangulationDelaunay((Point[])points.toArray());
            MeshView meshView = new MeshView(triangulation.getTriangles());
        }
        {
            List<Point> points = ResearchTest.getInTriangles(AMOUNT_POINTS);
            TriangulationDelaunay triangulation = new TriangulationDelaunay((Point[])points.toArray());
            MeshView meshView = new MeshView(triangulation.getTriangles());
        }
    }
}
