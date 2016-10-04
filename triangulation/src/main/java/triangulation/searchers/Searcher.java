package triangulation.searchers;

import triangulation.TriangleStructure;
import triangulation.elements.Point;
import triangulation.geometries.GeometryPointTriangle;

public interface Searcher {
    TriangleStructure getSearcher();

    void setSearcher(TriangleStructure searcher);

    void chooseSearcher(Point point);

    GeometryPointTriangle.PointTriangleState movingByConvexHull(Point point);
}
