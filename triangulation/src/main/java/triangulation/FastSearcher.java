package triangulation;

import triangulation.elements.ArrayIndexCorrection;
import triangulation.elements.BorderBox;
import triangulation.elements.Point;
import triangulation.elements.Precision;
import triangulation.geometries.Geometry;
import triangulation.geometries.GeometryPointTriangle;
import triangulation.searchers.Searcher;

public class FastSearcher implements Searcher {

    public static double AMOUNT_SEARCHER_FACTOR = 0.5D;

    private final TriangleStructure[] searcher;
    private final double[] elevations;
    private int positionSearcher = 0;
    private TriangulationDelaunay triangulation = new TriangulationDelaunay();

    public FastSearcher(TriangulationDelaunay triangulation, TriangleStructure init, BorderBox box, int amountOfPoints) {
        this.triangulation = triangulation;
        searcher = new TriangleStructure[(int) Math.max(1.0D, AMOUNT_SEARCHER_FACTOR * Math.sqrt((double) amountOfPoints))];
        for (int i = 0; i < searcher.length; i++) {
            searcher[i] = init;
        }
        double heightStep = (box.getY_max() - box.getY_min()) / (double) searcher.length;
        elevations = new double[searcher.length];
        for (int i = 0; i < elevations.length; i++) {
            elevations[i] = box.getY_min() + i * heightStep;
        }
    }

    @Override
    public TriangleStructure getSearcher() {
        return searcher[positionSearcher];
    }

    @Override
    public void setSearcher(TriangleStructure searcher) {
        this.searcher[positionSearcher] = searcher;
    }

    @Override
    public void chooseSearcher(Point point) {
        for (int i = searcher.length - 1; i >= 0; i--) {
            if (point.getY() > elevations[i] - Precision.epsilon()) {
                positionSearcher = i;
                break;
            }
        }

        if (searcher[positionSearcher].triangles != null)
            return;

        for (int i = 0; i < searcher.length; i++) {
            if (searcher[ArrayIndexCorrection.normalize(positionSearcher + i, searcher.length)].triangles != null) {
                searcher[positionSearcher] = searcher[ArrayIndexCorrection.normalize(positionSearcher + i, searcher.length)];
                return;
            }
        }
    }

    /**
     * Found next triangle
     * Performance - O(n) in worst case and O(sqrt(n)) is average case.
     *
     * @param point - next point
     * @return GeometryPointTriangle.PointTriangleState
     * @see Point
     * @see GeometryPointTriangle.PointTriangleState
     */
    @Override
    public GeometryPointTriangle.PointTriangleState movingByConvexHull(Point point) {
        TriangleStructure beginTriangle = getSearcher();
        while (true) {
            //add reserve searching
            if (Geometry.isAtRightOf(triangulation.getNode(beginTriangle.iNodes[0]), triangulation.getNode(beginTriangle.iNodes[1]), point)) {
                beginTriangle = beginTriangle.triangles[0];
            } else {
                int whichOp = 0;
                if (Geometry.isAtRightOf(triangulation.getNode(beginTriangle.iNodes[1]), triangulation.getNode(beginTriangle.iNodes[2]), point)) {
                    whichOp += 1;
                }
                if (Geometry.isAtRightOf(triangulation.getNode(beginTriangle.iNodes[2]), triangulation.getNode(beginTriangle.iNodes[0]), point)) {
                    whichOp += 2;
                }
                if (whichOp == 0) {
                    break;
                } else if (whichOp == 1) {
                    beginTriangle = beginTriangle.triangles[1];
                } else if (whichOp == 2) {
                    beginTriangle = beginTriangle.triangles[2];
                } else {
                    if (Geometry.distanceLineAndPoint(triangulation.getNode(beginTriangle.iNodes[1]), triangulation.getNode(beginTriangle.iNodes[2]), point) >
                            Geometry.distanceLineAndPoint(triangulation.getNode(beginTriangle.iNodes[2]), triangulation.getNode(beginTriangle.iNodes[0]), point)) {
                        beginTriangle = beginTriangle.triangles[1];
                    } else {
                        beginTriangle = beginTriangle.triangles[2];
                    }
                }
            }
        }
        Point[] trianglePoint = new Point[]{
                triangulation.getNode(beginTriangle.iNodes[0]),
                triangulation.getNode(beginTriangle.iNodes[1]),
                triangulation.getNode(beginTriangle.iNodes[2])
        };
        setSearcher(beginTriangle);
        return GeometryPointTriangle.statePointInTriangle(point, trianglePoint);
    }

    public TriangleStructure[] allTrianglesFromSearchers() {
        return searcher;
    }
}
