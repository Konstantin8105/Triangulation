package triangulation;

import meshview.MeshView;
import research.ResearchTriangulation;
import triangulation.elements.Point;

import java.util.List;

public class TriangulationAdvanceView {

    public static void main(String[] args) throws Exception {
        int AMOUNT_POINTS = 1_000;
        {
            List<Point> points = ResearchTriangulation.getRandomPoints(AMOUNT_POINTS);
            TriangulationDelaunay triangulation = new TriangulationDelaunay((Point[]) points.toArray());
            MeshView meshView = new MeshView(triangulation.getTriangles());
        }
        {
            List<Point> points = ResearchTriangulation.getCirclePoints(AMOUNT_POINTS);
            TriangulationDelaunay triangulation = new TriangulationDelaunay((Point[])points.toArray());
            MeshView meshView = new MeshView(triangulation.getTriangles());
        }
        {
            List<Point> points = ResearchTriangulation.getLineOnLine(AMOUNT_POINTS);
            TriangulationDelaunay triangulation = new TriangulationDelaunay((Point[])points.toArray());
            MeshView meshView = new MeshView(triangulation.getTriangles());
        }
        {
            List<Point> points = ResearchTriangulation.getInTriangles(AMOUNT_POINTS);
            TriangulationDelaunay triangulation = new TriangulationDelaunay((Point[])points.toArray());
            MeshView meshView = new MeshView(triangulation.getTriangles());
        }
    }
}
