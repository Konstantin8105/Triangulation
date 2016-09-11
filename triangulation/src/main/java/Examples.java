import meshview.MeshView;
import triangulation.Point;
import triangulation.TriangulationDelaunay;

public class Examples {

    public static void main(String[] args) throws Exception {

        Point[] coordinates = new Point[7];
        coordinates[0] = new Point(0f, 0f);
        coordinates[1] = new Point(100f, 100f);
        coordinates[2] = new Point(200f, 200f);
        coordinates[3] = new Point(300f, 300f);
        coordinates[4] = new Point(400f, 400f);
        coordinates[5] = new Point(500f, 500f);
        coordinates[6] = new Point(100f, 0f);

        TriangulationDelaunay triangulation = new TriangulationDelaunay(coordinates);

        MeshView meshView = new MeshView(triangulation.getTriangles());
    }
}
