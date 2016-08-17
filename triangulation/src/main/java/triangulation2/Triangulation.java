package triangulation2;

import triangulation.border.BorderBox;
import triangulation.elements.Point;
import triangulation.geometry.GeometryPointTriangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Triangulation
 * for step - triangulation convexHull: "Divide and Conquer"
 * Performance for worst-case: O(N*log(N))
 * for step - triangulation with restrictions: "Simple interactive method"
 * Performance for worst-case: O(N^2)
 *
 * @author Izyumov Konstantin
 * @see book "Algoritm building and analyse triangulation", A.B.Skvorcov.
 */
public class Triangulation {

    //
    // Triangulation data structure  "Nodes, ribs и triangles"
    //
    // Array of nodes - type: Point
    List<Point> nodes = new ArrayList<>();
    // Begin triangle - type: Triangle
    Triangle beginTriangle = new Triangle();

    // Triangle data structure
    private static class Triangle {
        // indexes of triangle points
        public int[] iNodes;
        // indexes of near triangles
        public Triangle[] triangles;
        // indexes of triangle ribs
        public int[] iRibs;

        @Override
        public String toString() {
            return "Triangle{" +
                    "iNodes=" + Arrays.toString(iNodes) +
                    ", iRibs=" + Arrays.toString(iRibs) +
                    '}';
        }
    }

    private int idMaximalRibs = 0;

    private int getIdRib() {
        return idMaximalRibs++;
    }


    // constructor for create convexHull region at the base on points
    public Triangulation(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            addNextPoint(points[i]);
        }
    }

    // boundingBox for simplification of finding outside point
    BorderBox boundingBox = new BorderBox();

    // saving points at the begin of triangulation
    private List<Point> lastPoints = new ArrayList<>();
    private boolean isNeedLastPointsSaving = true;

    private void addNextPoint(Point nextPoint) {
        // ignore same points
        for (int j = 0; j < nodes.size(); j++) {
            if (nextPoint.equals(nodes.get(j)))
                continue;
        }

        if (isNeedLastPointsSaving) {
            lastPoints.add(nextPoint);
            if (lastPoints.size() < 3) {
                boundingBox.addPoint(nextPoint);
                return;
            }
            addNextPointWithoutBorder();
            boundingBox.addPoint(nextPoint);
            return;
        }

        GeometryPointTriangle.PointTriangleState state = movingByConvexHull(nextPoint);
        switch (state) {
            case POINT_INSIDE:
                addNextPointInTriangle(nextPoint);
                break;
            case POINT_ON_LINE_0:
                addNextPointOnLine(nextPoint, 0);
                break;
            case POINT_ON_LINE_1:
                addNextPointOnLine(nextPoint, 1);
                break;
            case POINT_ON_LINE_2:
                addNextPointOnLine(nextPoint, 2);
                break;
            case POINT_OUTSIDE:
                addNextPointOutside(nextPoint);
                break;
        }
        boundingBox.addPoint(nextPoint);
    }

    GeometryPointTriangle.PointTriangleState movingByConvexHull(Point point) {
        Point[] trianglePoint = new Point[]{
                (Point) nodes.get(beginTriangle.iNodes[0]),
                (Point) nodes.get(beginTriangle.iNodes[1]),
                (Point) nodes.get(beginTriangle.iNodes[2])
        };
        GeometryPointTriangle.PointTriangleState state = GeometryPointTriangle.isPointInTriangle(point, trianglePoint);
        if (state == GeometryPointTriangle.PointTriangleState.POINT_OUTSIDE) {

            Point triangleCenter = new Point(0, 0);
            for (int i = 0; i < 3; i++) {
                triangleCenter.setX(triangleCenter.getX() + trianglePoint[i].getX());
                triangleCenter.setY(triangleCenter.getY() + trianglePoint[i].getY());
            }
            triangleCenter.setX(triangleCenter.getX() / 3.);
            triangleCenter.setY(triangleCenter.getY() / 3.);

            int ribPosition = -1;
            for (int i = 0; i < 3; i++) {
                int last = (i == 0) ? 2 : i - 1;
                int next = i;
                Point ribPoint1 = (Point) nodes.get(beginTriangle.iNodes[last]);
                Point ribPoint2 = (Point) nodes.get(beginTriangle.iNodes[next]);
                Intersects.IntersectState intersectState = Intersects.stateLineLine(ribPoint1, ribPoint2, triangleCenter, point);
                if (intersectState == Intersects.IntersectState.INTERSECT ||
                        intersectState == Intersects.IntersectState.INTERSECT_POINT_ON_LINE) {
                    ribPosition = i;
                    break;
                }
            }

            // moving to next triangle
            if (beginTriangle.triangles[ribPosition] != null) {
                beginTriangle = beginTriangle.triangles[ribPosition];
                state = movingByConvexHull(point);
            } else {
                state = GeometryPointTriangle.PointTriangleState.POINT_OUTSIDE;
            }
        }
        return state;
    }

    private void addNextPointWithoutBorder() {
        if (Geometry.isOnLine(lastPoints)) {
            return;
        }
        isNeedLastPointsSaving = false;

        nodes.add(lastPoints.get(0));
        nodes.add(lastPoints.get(1));
        nodes.add(lastPoints.get(2));
        int pointIndexEndPoint = nodes.size() - 1;

        beginTriangle.iNodes = new int[]{
                pointIndexEndPoint - 2,
                pointIndexEndPoint - 1,
                pointIndexEndPoint
        };
        beginTriangle.triangles = new Triangle[]{
                null,
                null,
                null
        };

        beginTriangle.iRibs = new int[]{
                getIdRib(),
                getIdRib(),
                getIdRib()
        };

        for (int i = 3; i < lastPoints.size(); i++) {
            addNextPoint((Point) lastPoints.get(i));
        }
    }

    protected void addNextPointInTriangle(Point nextPoint) {
        nodes.add(nextPoint);
        int pointIndex = nodes.size() - 1;
        int rib0 = getIdRib();
        int rib1 = getIdRib();
        int rib2 = getIdRib();

        Triangle triangle0 = new Triangle();
        triangle0.iNodes = new int[]{
                beginTriangle.iNodes[0],
                beginTriangle.iNodes[1],
                pointIndex
        };
        triangle0.iRibs = new int[]{
                beginTriangle.iRibs[0],
                rib1,
                rib0
        };

        Triangle triangle1 = new Triangle();
        triangle1.iNodes = new int[]{
                beginTriangle.iNodes[1],
                beginTriangle.iNodes[2],
                pointIndex
        };
        triangle1.iRibs = new int[]{
                beginTriangle.iRibs[1],
                rib2,
                rib1
        };

        Triangle triangle2 = new Triangle();
        triangle2.iNodes = new int[]{
                beginTriangle.iNodes[2],
                beginTriangle.iNodes[0],
                pointIndex
        };
        triangle2.iRibs = new int[]{
                beginTriangle.iRibs[2],
                rib0,
                rib2
        };

        triangle0.triangles = new Triangle[]{
                beginTriangle.triangles[0],
                triangle1,
                triangle2
        };
        if (beginTriangle.triangles[0] != null) {
            for (int i = 0; i < 3; i++) {
                if (beginTriangle.triangles[0].triangles[i] == beginTriangle) {
                    beginTriangle.triangles[0].triangles[i] = triangle0;
                    break;
                }
            }
        }

        triangle1.triangles = new Triangle[]{
                beginTriangle.triangles[1],
                triangle2,
                triangle0
        };
        if (beginTriangle.triangles[1] != null) {
            for (int i = 0; i < 3; i++) {
                if (beginTriangle.triangles[1].triangles[i] == beginTriangle) {
                    beginTriangle.triangles[1].triangles[i] = triangle1;
                    break;
                }
            }
        }

        triangle2.triangles = new Triangle[]{
                beginTriangle.triangles[2],
                triangle0,
                triangle1
        };
        if (beginTriangle.triangles[2] != null) {
            for (int i = 0; i < 3; i++) {
                if (beginTriangle.triangles[2].triangles[i] == beginTriangle) {
                    beginTriangle.triangles[2].triangles[i] = triangle2;
                    break;
                }
            }
        }

        beginTriangle = triangle0;
    }


    int normalizeSizeBy3(int index) {
        if (0 <= index && index < 3) {
            return index;
        }
        if (index < 0)
            return normalizeSizeBy3(index + 3);
        return normalizeSizeBy3(index - 3);
    }

    private void addNextPointOnLine(Point nextPoint, int indexLineInTriangle) {

        nodes.add(nextPoint);
        int pointIndex = nodes.size() - 1;

        int rib0 = getIdRib();
        int rib1 = getIdRib();
        int rib2 = getIdRib();
        int rib3 = getIdRib();

        Triangle triangles[] = new Triangle[4];
        for (int i = 0; i < 4; i++) {
            triangles[i] = new Triangle();
        }


        triangles[0].iNodes = new int[]{
                beginTriangle.iNodes[normalizeSizeBy3(indexLineInTriangle)],
                pointIndex,
                beginTriangle.iNodes[normalizeSizeBy3(indexLineInTriangle + 2)]
        };

        triangles[1].iNodes = new int[]{
                pointIndex,
                beginTriangle.iNodes[normalizeSizeBy3(indexLineInTriangle + 1)],
                beginTriangle.iNodes[normalizeSizeBy3(indexLineInTriangle + 2)]
        };

        triangles[0].iRibs = new int[]{
                rib0,
                rib2,
                beginTriangle.iRibs[normalizeSizeBy3(indexLineInTriangle - 1)]
        };
        triangles[1].iRibs = new int[]{
                rib1,
                beginTriangle.iRibs[normalizeSizeBy3(indexLineInTriangle + 1)],
                rib2
        };


        triangles[0].triangles = new Triangle[]{
                null,
                triangles[1],
                beginTriangle.triangles[normalizeSizeBy3(indexLineInTriangle - 1)]
        };
        triangles[1].triangles = new Triangle[]{
                null,
                beginTriangle.triangles[normalizeSizeBy3(indexLineInTriangle + 1)],
                triangles[0]
        };

        if (beginTriangle.triangles[indexLineInTriangle] == null) {
            beginTriangle = triangles[0];
            return;
        }

        int ribConnectId = beginTriangle.iRibs[indexLineInTriangle];
        beginTriangle = beginTriangle.triangles[indexLineInTriangle];
        for (int i = 0; i < 3; i++) {
            if (beginTriangle.iRibs[i] == ribConnectId) {
                indexLineInTriangle = i;
            }
        }

        triangles[0].triangles[0] = triangles[2];
        triangles[1].triangles[0] = triangles[3];

        triangles[2].iNodes = new int[]{
                pointIndex,
                beginTriangle.iNodes[normalizeSizeBy3(indexLineInTriangle + 1)],
                beginTriangle.iNodes[normalizeSizeBy3(indexLineInTriangle + 2)]
        };
        triangles[3].iNodes = new int[]{
                beginTriangle.iNodes[indexLineInTriangle],
                pointIndex,
                beginTriangle.iNodes[normalizeSizeBy3(indexLineInTriangle + 2)]
        };


        triangles[2].iRibs = new int[]{
                rib0,
                beginTriangle.iRibs[normalizeSizeBy3(indexLineInTriangle + 1)],
                rib3
        };
        triangles[3].iRibs = new int[]{
                rib1,
                rib3,
                beginTriangle.iRibs[normalizeSizeBy3(indexLineInTriangle - 1)]
        };

        triangles[2].triangles = new Triangle[]{
                triangles[0],
                beginTriangle.triangles[normalizeSizeBy3(indexLineInTriangle + 1)],
                triangles[3]
        };

        triangles[3].triangles = new Triangle[]{
                triangles[1],
                triangles[2],
                beginTriangle.triangles[normalizeSizeBy3(indexLineInTriangle - 1)]
        };
    }

    private void getTriangleWays(Triangle triangle, List<Triangle> triangles) {
        triangles.add(triangle);
        for (int i = 0; i < triangle.triangles.length; i++) {
            if (triangle.triangles[i] != null) {
                boolean isFound = false;
                for (int j = 0; j < triangles.size(); j++) {
                    if (triangles.get(j) == triangle.triangles[i]) {
                        j = triangles.size();
                        isFound = true;
                    }
                }
                if (!isFound) {
                    getTriangleWays(triangle.triangles[i], triangles);
                }
            }
        }
    }

    public List<Triangle> getTriangulation() {
        List<Triangle> triangles = new ArrayList<>();
        getTriangleWays(beginTriangle, triangles);
        return triangles;
    }

    @Override
    public String toString() {
        return "Triangulation{" +
                "beginTriangle=" + beginTriangle +
                ", nodes=" + nodes +
                ", boundingBox=" + boundingBox +
                ", lastPoints=" + lastPoints +
                ", isNeedLastPointsSaving=" + isNeedLastPointsSaving +
                '}';
    }

    private void addNextPointOutside(Point nextPoint) {
        List<Triangle> borderSegment = getBorderSegment(nextPoint);

        Triangle[] triangles = new Triangle[borderSegment.size()];
        for (int i = 0; i < triangles.length; i++) {
            triangles[i] = new Triangle();
        }

        int rib_before = getIdRib();
        int rib_after;
        nodes.add(nextPoint);
        int pointIndex = nodes.size() - 1;
        for (int i = 0; i < triangles.length; i++) {
            rib_after = getIdRib();
            triangles[i].triangles = new Triangle[]{
                    ((Triangle) borderSegment.get(i)).triangles[0],
                    null,
                    null
            };
            triangles[i].iNodes = new int[]{
                    ((Triangle) borderSegment.get(i)).iNodes[0],
                    ((Triangle) borderSegment.get(i)).iNodes[1],
                    pointIndex
            };
            triangles[i].iRibs = new int[]{
                    ((Triangle) borderSegment.get(i)).iRibs[0],
                    rib_before,
                    rib_after
            };
            rib_before = rib_after;
            if (i >= 1 && i <= borderSegment.size() - 2) {
                triangles[i - 1].triangles[2] = triangles[i];
            }
        }
        beginTriangle = triangles[0];
    }

    private List<Triangle> getBorderSegment(Point point) {
        // create list of border
        List<Triangle> border = getBorder();
        // looping
        // create convexHull
        // looping minus convexHull
        return null;
    }

    private List<Triangle> getBorder() {
        List<Triangle> borders = new ArrayList<>();
        int indexStartPoint;
        int finishStartPoint;
        boolean findBegin = false;
        for (int i = 0; i < beginTriangle.triangles.length; i++) {
            if (beginTriangle.triangles[i] == null) {
                if(!findBegin){
                    indexStartPoint = beginTriangle.iNodes[i];
                }
                // add border in array
                Triangle borderTriangle = new Triangle();
                borderTriangle.triangles = new Triangle[]{
                        beginTriangle.triangles[i]
                };
                borderTriangle.iNodes = new int[]{
                        beginTriangle.iNodes[i],
                        beginTriangle.iNodes[normalizeSizeBy3(i + 1)]
                };
                borderTriangle.iRibs = new int[]{
                        beginTriangle.iRibs[i]
                };
            }
        }
        while (true) {
        }
        return borders;
    }

    ///////
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    /////////////////
    public static class Contour {
        Sequence lines;
        boolean isHole;

        public Contour(Sequence lines, boolean isHole) {
            this.lines = lines;
            this.isHole = isHole;
        }
    }

    public static Sequence getTriangles(Contour contourNotIntersect) {
        if (contourNotIntersect == null)
            return null;

        // checking contour
        // TODO: 15.08.2016


        // create first iteration of delaunay triangulation
        Delaunay delaunay = new Delaunay();
        for (int i = 0; i < contourNotIntersect.lines.getSize(); i++) {
            Point[] line = (Point[]) contourNotIntersect.lines.get(i);
            delaunay.insertPoint(line[0]);
            delaunay.insertPoint(line[1]);
        }

        // adapted edge to lines
        adaptedEdge(delaunay, contourNotIntersect.lines);

        // remove triangles
        // TODO: 15.08.2016

        return delaunay.computeTriangles();
    }

    private static void adaptedEdge(Delaunay delaunay, Sequence lines) {
        Sequence edge = delaunay.computeEdges();
        Sequence linePosition = new ArraySequence();
        for (int i = 0; i < lines.getSize(); i++) {
            boolean isFound = false;
            Point[] line = (Point[]) lines.get(i);
            for (int j = 0; j < edge.getSize(); j++) {
                if (sameLines(line, (Point[]) edge.get(j))) {
                    isFound = true;
                    j = edge.getSize();
                }
            }
            if (!isFound) {
                linePosition.add(i);
            }
        }
        if (linePosition.getSize() > 0) {
            un.api.collection.Collections.sort(linePosition);
            for (int i = linePosition.getSize() - 1; i >= 0; i--) {
                Point[] line = (Point[]) lines.get((int) (Integer) linePosition.get(i));
                Point middle = new Point(
                        (line[0].getX() + line[1].getX()) / 2.,
                        (line[0].getY() + line[1].getY()) / 2.
                );
                lines.add(new Point[]{line[0], middle});
                lines.add(new Point[]{line[1], middle});
                lines.remove((int) (Integer) linePosition.get(i));
                delaunay.insertPoint(middle);
            }
            adaptedEdge(delaunay, lines);
        }
    }

    private static boolean sameLines(Point[] line1, Point[] line2) {
        if (!line1[0].equals(line2[0]) && !line1[0].equals(line2[1])) {
            return false;
        }
        if (line1[0].equals(line2[0]) && line1[1].equals(line2[1])) {
            return true;
        }
        if (line1[1].equals(line2[0]) && line1[0].equals(line2[1])) {
            return true;
        }
        return false;
    }

}
