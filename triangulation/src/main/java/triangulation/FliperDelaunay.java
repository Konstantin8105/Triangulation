package triangulation;

import triangulation.elements.ArrayIndexCorrection;
import triangulation.elements.Point;
import triangulation.flipers.FlipStructure;
import triangulation.flipers.Fliper;

import java.util.Stack;

public class FliperDelaunay implements Fliper {
    private final Stack<FlipStructure> buffer = new Stack<>();
    private TriangulationDelaunay triangulation = new TriangulationDelaunay();

    public FliperDelaunay(TriangulationDelaunay triangulation) {
        this.triangulation = triangulation;
    }

    @Override
    public void add(TriangleStructure triangle, int index) {
        buffer.add(new FlipStructure(triangle,index));
    }

    @Override
    public void run() {
        while (!buffer.empty()) {
            FlipStructure next = buffer.pop();

            if (next.triangle.triangles == null)
                continue;

            if (next.triangle.triangles[next.side] == null)
                continue;

            Point p0 = triangulation.getNode(next.triangle.iNodes[ArrayIndexCorrection.normalizeSizeBy3(next.side - 1)]);
            Point p1 = triangulation.getNode(next.triangle.triangles[next.side].iNodes[0]);
            Point p2 = triangulation.getNode(next.triangle.triangles[next.side].iNodes[1]);
            Point p3 = triangulation.getNode(next.triangle.triangles[next.side].iNodes[2]);

            double s1 = (p0.getX() - p1.getX()) * (p0.getY() - p3.getY()) - (p0.getX() - p3.getX()) * (p0.getY() - p1.getY());
            double s2 = (p2.getX() - p3.getX()) * (p2.getX() - p1.getX()) + (p2.getY() - p3.getY()) * (p2.getY() - p1.getY());
            double s3 = (p0.getX() - p1.getX()) * (p0.getX() - p3.getX()) + (p0.getY() - p1.getY()) * (p0.getY() - p3.getY());
            double s4 = (p2.getX() - p3.getX()) * (p2.getY() - p1.getY()) - (p2.getX() - p1.getX()) * (p2.getY() - p3.getY());
            if (s1 * s2 + s3 * s4 >= 0)
                continue;
            else
                triangulation.flipTriangles(next.triangle, next.side);
        }
    }
}
