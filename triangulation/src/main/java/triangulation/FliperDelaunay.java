package triangulation;

import triangulation.elements.Point;
import triangulation.flipers.FlipStructure;
import triangulation.flipers.Fliper;
import triangulation.geometries.Geometry;

import java.util.Stack;

import static triangulation.elements.ArrayIndexCorrection.normalizeSizeBy3;

public class FliperDelaunay implements Fliper {
    private final Stack<FlipStructure> buffer = new Stack<>();
    private TriangulationDelaunay triangulation = new TriangulationDelaunay();

    public FliperDelaunay(TriangulationDelaunay triangulation) {
        this.triangulation = triangulation;
    }

    @Override
    public void add(TriangleStructure triangle, int index) {
        buffer.add(new FlipStructure(triangle, index));
    }

    @Override
    public void run() {
        while (!buffer.empty()) {
            FlipStructure next = buffer.pop();

            if (next.triangle.triangles == null)
                continue;

            if (next.triangle.triangles[next.side] == null)
                continue;

            if (!Geometry.isPointInCircle(
                    new Point[]{
                            triangulation.getNode(next.triangle.triangles[next.side].iNodes[0]),
                            triangulation.getNode(next.triangle.triangles[next.side].iNodes[1]),
                            triangulation.getNode(next.triangle.triangles[next.side].iNodes[2])},
                    triangulation.getNode(next.triangle.iNodes[normalizeSizeBy3(next.side - 1)])))
                continue;
            triangulation.flipTriangles(next.triangle, next.side);
        }
    }
}
