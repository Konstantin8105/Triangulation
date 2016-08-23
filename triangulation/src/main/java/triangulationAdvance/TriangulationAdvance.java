package triangulationAdvance;

import imp.iTriangulation;
import triangulation.elements.Point;

import java.math.BigDecimal;
import java.util.*;

/**
 * triangulationAdvance.TriangulationAdvance
 * for step - triangulation convexHull: "Divide and Conquer"
 * Performance for worst-case: O(N*log(N)) todo: change to true O(n^2)
 * for step - triangulation with restrictions: "Simple interactive method"
 * Performance for worst-case: O(N^2)
 * Philosophy of triangulation:
 * 1. Add 4 "fake" point - for guarantee all othe point in "fake" region
 * 2. triangulation.Triangulation and delaunay checking
 * 3. Cut triangulation by convexHull region or external region
 *
 * @author Izyumov Konstantin
 *         book "Algoritm building and analyse triangulation", A.B.Skvorcov.
 */
public class TriangulationAdvance implements iTriangulation {
    //
    // triangulationAdvance.TriangulationAdvance data structure  "Nodes, ribs и triangles"
    //
    // Array of nodes - type: Point
    List<Point> nodes = new ArrayList<>();
    // Array of fake nodes
    List<Point> fakeNodes = new ArrayList<>();
    // Begin triangle - type: Triangle
    Triangle beginTriangle = new Triangle();


    // Triangle data structure
    private static class Triangle {
        // indexes of triangle points
        int[] iNodes;
        // indexes of near triangles
        Triangle[] triangles;
        // indexes of triangle ribs
        int[] iRibs;
        // indicator
        boolean mark;

        void changeClockwise() {
            int temp;
            temp = iNodes[0];
            iNodes[0] = iNodes[1];
            iNodes[1] = temp;
            temp = iRibs[1];
            iRibs[1] = iRibs[2];
            iRibs[2] = temp;
            Triangle tri = triangles[1];
            triangles[1] = triangles[2];
            triangles[2] = tri;
        }
    }

    private boolean isCounterClockwise(Triangle triangle) {
        return TriangulationAdvance.Geometry.isCounterClockwise(
                nodes.get(triangle.iNodes[0]),
                nodes.get(triangle.iNodes[1]),
                nodes.get(triangle.iNodes[2])
        );
    }

    private int idMaximalRibs = 0;

    private int getIdRib() {
        return idMaximalRibs++;
    }


    // constructor for create convexHull region at the base on points
    public TriangulationAdvance(Point[] points) {
        // TODO: 18.08.2016 fake points must be moved
        createFakeTriangles(points);
        for (int i = 0; i < points.length; i++) {
            addNextPoint(points[i]);

//        delaunayChecking();

        }
//        delaunayChecking();
//        for (int i = 0; i < 100; i++) {
//            delaunayChecking();
//        }
        //cutByRegion(convexHull(points));
        //removeFakeTriangles();
//        System.out.println(counter.toString());
        // TODO: 8/22/16 remove fake triangles or use convexHull like first element
    }

    private void delaunayChecking() {
        // un mark all triangles
        List<Triangle> triangles = new ArrayList<>();
        getTriangleWays(beginTriangle, triangles);
        for (Triangle triangle : triangles) {
            triangle.mark = false;
        }
        // Delaunay checking
        List<Triangle> nullElements = new ArrayList<>();
        for (int k = 0; k < triangles.size(); k++) {
            Triangle triangle = triangles.get(k);
            if (triangle == null)
                continue;
            if (triangle.mark == true)
                continue;
            boolean isNull = false;
            for (int i = 0; i < nullElements.size(); i++) {
                if (nullElements.get(i) == triangle) {
                    isNull = true;
                    i = nullElements.size();
                }
            }
            if (isNull)
                continue;
            boolean isAllGood = true;
            for (int i = 0; i < 3; i++) {
                if (!isGoodDelaunay(triangle, i)) {
                    if (flipTriangles(triangle, i) != null) {
                        isAllGood = false;
                        triangles.set(k, null);
                        nullElements.add(triangle.triangles[i]);
                        i = 3;
                    }
                }
            }
            if (isAllGood) {
                triangle.mark = true;
            } else {
                triangle.mark = false;
            }
        }
//        if (!allElementMark()) {
//            triangles.clear();
//            delaunayChecking();
//        }
    }


    private boolean allElementMark() {
        List<Triangle> triangles = new ArrayList<>();
        getTriangleWays(beginTriangle, triangles);
        for (Triangle triangle : triangles) {
            if (!triangle.mark)
                return false;
        }
        return true;
    }

    private Triangle[] flipTriangles(Triangle triangle, int indexTriangle) {

        int pointNewTriangle = triangle.iNodes[normalizeSizeBy3(indexTriangle + 2)];
        int commonRib = triangle.iRibs[indexTriangle];
        Triangle[] baseTriangles = new Triangle[]{
                triangle,
                triangle.triangles[indexTriangle]
        };

        // global region
        List<Triangle> region = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Triangle internalTriangle = baseTriangles[i];
            int indexCommonRib = -1;
            for (int j = 0; j < 3; j++) {
                if (commonRib == internalTriangle.iRibs[j]) {
                    indexCommonRib = j;
                }
            }
            Triangle t1 = new Triangle();
            t1.iRibs = new int[]{
                    internalTriangle.iRibs[normalizeSizeBy3(indexCommonRib + 1)]
            };
            t1.iNodes = new int[]{
                    internalTriangle.iNodes[normalizeSizeBy3(indexCommonRib + 1)],
                    internalTriangle.iNodes[normalizeSizeBy3(indexCommonRib + 2)]
            };
            t1.triangles = new Triangle[]{
                    internalTriangle.triangles[normalizeSizeBy3(indexCommonRib + 1)]
            };
            region.add(t1);
            Triangle t2 = new Triangle();
            t2.iRibs = new int[]{
                    internalTriangle.iRibs[normalizeSizeBy3(indexCommonRib + 2)]
            };
            t2.iNodes = new int[]{
                    internalTriangle.iNodes[normalizeSizeBy3(indexCommonRib + 2)],
                    internalTriangle.iNodes[normalizeSizeBy3(indexCommonRib + 3)]
            };
            t2.triangles = new Triangle[]{
                    internalTriangle.triangles[normalizeSizeBy3(indexCommonRib + 2)]
            };
            region.add(t2);
        }

        //loopization
        boolean isLoop = true;
        for (int i = 0; i < region.size(); i++) {
            int last = i;
            int next = i + 1 > region.size() - 1 ? 0 : i + 1;
            if (region.get(last).iNodes[1] != region.get(next).iNodes[0]) {
                isLoop = false;
            }
        }
        if (!isLoop) {
            return null;
        }

        //checking base point
        if (region.get(0).iNodes[1] != pointNewTriangle) {
            return null;
        }

        //check - new triangle is not on 1 line
        if (Geometry.is3pointsCollinear(
                nodes.get(region.get(0).iNodes[1]),
                nodes.get(region.get(1).iNodes[1]),
                nodes.get(region.get(2).iNodes[1])) ||
                Geometry.is3pointsCollinear(
                        nodes.get(region.get(3).iNodes[0]),
                        nodes.get(region.get(3).iNodes[1]),
                        nodes.get(region.get(0).iNodes[1]))) {
            return null;
        }

        //convexHull checking
        if (Geometry.convexHull(new Point[]{
                nodes.get(region.get(0).iNodes[0]),
                nodes.get(region.get(1).iNodes[0]),
                nodes.get(region.get(2).iNodes[0]),
                nodes.get(region.get(3).iNodes[0]),
        }).length < 4) {
            return null;
        }

        //separate on 2 triangles

        int newCommonRib = getIdRib();

        Triangle[] triangles = new Triangle[2];
        for (int i = 0; i < 2; i++) {
            triangles[i] = new Triangle();
        }

        triangles[0].iNodes = new int[]{
                region.get(1).iNodes[0],
                region.get(1).iNodes[1],
                region.get(2).iNodes[1]
        };
        triangles[0].iRibs = new int[]{
                region.get(1).iRibs[0],
                region.get(2).iRibs[0],
                newCommonRib
        };
        triangles[0].triangles = new Triangle[]{
                region.get(1).triangles[0],
                region.get(2).triangles[0],
                triangles[1]
        };

        triangles[1].iNodes = new int[]{
                region.get(3).iNodes[0],
                region.get(3).iNodes[1],
                region.get(0).iNodes[1]
        };
        triangles[1].iRibs = new int[]{
                region.get(3).iRibs[0],
                region.get(0).iRibs[0],
                newCommonRib
        };
        triangles[1].triangles = new Triangle[]{
                region.get(3).triangles[0],
                region.get(0).triangles[0],
                triangles[0]
        };

        //inverse link on triangle
        addInverseLinkOnTriangle(triangles);

        //move beginTriangle
        beginTriangle = triangles[0];

        //add null in old triangles
        for (int i = 0; i < baseTriangles.length; i++) {
            baseTriangles[i] = null;
        }

        return triangles;
    }

    private boolean isGoodDelaunay(Triangle triangle, int indexTriangle) {
        if (triangle.triangles[indexTriangle] == null) {
            return true;
        }
        if (Geometry.isPointInCircle(
                new Point[]{
                        nodes.get(triangle.iNodes[0]),
                        nodes.get(triangle.iNodes[1]),
                        nodes.get(triangle.iNodes[2])
                },
                nodes.get(triangle.triangles[indexTriangle].iNodes[normalizeSizeBy3(indexTriangle + 2)])))
            return false;
        return true;
    }


    private void createFakeTriangles(Point[] points) {
        // create Fake region
        //TODO: remove that because O(n^2)
        BorderBox borderBox = new BorderBox();
        for (int i = 0; i < points.length; i++) {
            borderBox.addPoint(points[i]);
        }
        double scaleFactor = 3.0D;
        borderBox.scale(scaleFactor, borderBox.getCenter());
        //add fake points
        double factor = 0.1D;
        nodes.add(new Point(borderBox.getX_min() * (1.0D - factor), borderBox.getY_min() * (1.0D - factor)));
        nodes.add(new Point(borderBox.getX_min(), borderBox.getY_max()));
        nodes.add(new Point(borderBox.getX_max() * (1.0D + factor), borderBox.getY_max() * (1.0D + factor)));
        nodes.add(new Point(borderBox.getX_max(), borderBox.getY_min()));
        int indexPointStart = nodes.size() - 4;
        for (int i = 0; i < 4; i++) {
            fakeNodes.add(nodes.get(indexPointStart + i));
        }

        Triangle triangles[] = new Triangle[2];

        triangles[0] = new Triangle();
        triangles[1] = new Triangle();


        int commonRib = getIdRib();
        triangles[0].iNodes = new int[]{indexPointStart, indexPointStart + 1, indexPointStart + 2};
        triangles[0].iRibs = new int[]{getIdRib(), getIdRib(), commonRib};
        triangles[0].triangles = new Triangle[]{null, null, triangles[1]};

        triangles[1].iNodes = new int[]{indexPointStart + 2, indexPointStart + 3, indexPointStart};
        triangles[1].iRibs = new int[]{getIdRib(), getIdRib(), commonRib};
        triangles[1].triangles = new Triangle[]{null, null, triangles[0]};

        addInverseLinkOnTriangle(triangles);

        beginTriangle = triangles[0];
    }

    private void addNextPoint(Point nextPoint) {
        // ignore same points
        //TODO: remove O(n^2)
        for (int j = 0; j < nodes.size(); j++) {
            if (nextPoint.equals(nodes.get(j))) {
                return;
            }
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
        }
    }

    private Point centerOfPoints(Point... points) {
        double x = 0.0D, y = 0.0D;
        for (int i = 0; i < points.length; i++) {
            x += points[i].getX();
            y += points[i].getY();
        }
        return new Point(x / points.length, y / points.length);
    }


    private GeometryPointTriangle.PointTriangleState movingByConvexHull(Point point) {

        while (true) {
            if (Geometry.isAtRightOf(nodes.get(beginTriangle.iNodes[0]), nodes.get(beginTriangle.iNodes[1]), point)) {
                beginTriangle = beginTriangle.triangles[0];
            } else {
                int whichOp = 0;
                if (Geometry.isAtRightOf(nodes.get(beginTriangle.iNodes[1]), nodes.get(beginTriangle.iNodes[2]), point)) {
                    whichOp += 1;
                }
                if (Geometry.isAtRightOf(nodes.get(beginTriangle.iNodes[2]), nodes.get(beginTriangle.iNodes[0]), point)) {
                    whichOp += 2;
                }
                if (whichOp == 0) {
                    break;
                } else if (whichOp == 1) {
                    beginTriangle = beginTriangle.triangles[1];
                } else if (whichOp == 2) {
                    beginTriangle = beginTriangle.triangles[2];
                } else {
                    if (Geometry.distanceLineAndPoint(nodes.get(beginTriangle.iNodes[1]), nodes.get(beginTriangle.iNodes[2]), point) >
                            Geometry.distanceLineAndPoint(nodes.get(beginTriangle.iNodes[2]), nodes.get(beginTriangle.iNodes[0]), point)) {
                        beginTriangle = beginTriangle.triangles[1];
                    } else {
                        beginTriangle = beginTriangle.triangles[2];
                    }
                }
            }
        }

        Point[] trianglePoint = new Point[]{
                nodes.get(beginTriangle.iNodes[0]),
                nodes.get(beginTriangle.iNodes[1]),
                nodes.get(beginTriangle.iNodes[2])
        };
        return GeometryPointTriangle.statePointInTriangle(point, trianglePoint);
    }

    private void addNextPointInTriangle(Point nextPoint) {
        nodes.add(nextPoint);
        int pointIndex = nodes.size() - 1;
        int rib0 = getIdRib();
        int rib1 = getIdRib();
        int rib2 = getIdRib();

        Triangle[] triangles = new Triangle[3];
        for (int i = 0; i < 3; i++) {
            triangles[i] = new Triangle();
        }

        triangles[0].iNodes = new int[]{beginTriangle.iNodes[0], beginTriangle.iNodes[1], pointIndex};
        triangles[0].iRibs = new int[]{beginTriangle.iRibs[0], rib1, rib0};

        triangles[1].iNodes = new int[]{beginTriangle.iNodes[1], beginTriangle.iNodes[2], pointIndex};
        triangles[1].iRibs = new int[]{beginTriangle.iRibs[1], rib2, rib1};

        triangles[2].iNodes = new int[]{beginTriangle.iNodes[2], beginTriangle.iNodes[0], pointIndex};
        triangles[2].iRibs = new int[]{beginTriangle.iRibs[2], rib0, rib2};

        triangles[0].triangles = new Triangle[]{beginTriangle.triangles[0], triangles[1], triangles[2]};
        triangles[1].triangles = new Triangle[]{beginTriangle.triangles[1], triangles[2], triangles[0]};
        triangles[2].triangles = new Triangle[]{beginTriangle.triangles[2], triangles[0], triangles[1]};

        beginTriangle = triangles[0];
        addInverseLinkOnTriangle(triangles);

        for (int i = 0; i < 3; i++) {
            if (!isGoodDelaunay(triangles[i], 0)) {
                flipTriangles(triangles[i], 0);
            }
        }
    }

    private void addInverseLinkOnTriangle(Triangle[] triangles) {
        for (int i = 0; i < triangles.length; i++) {
            for (int j = 0; j < 3; j++) {
                Triangle externalTriangle = triangles[i].triangles[j];
                int commonRib = triangles[i].iRibs[j];
                if (externalTriangle != null) {
                    for (int k = 0; k < 3; k++) {
                        if (externalTriangle.iRibs[k] == commonRib) {
                            externalTriangle.triangles[k] = triangles[i];
                        }
                    }
                }
            }
        }
        boolean inverseAgain = false;
        for (int i = 0; i < triangles.length; i++) {
            if (isCounterClockwise(triangles[i])) {
                triangles[i].changeClockwise();
                inverseAgain = true;
            }
        }
        if (inverseAgain) {
            addInverseLinkOnTriangle(triangles);
        }
    }


    private static int normalizeSizeBy3(int index) {
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
            addInverseLinkOnTriangle(new Triangle[]{triangles[0], triangles[1]});
            beginTriangle = triangles[0];
            if (!isGoodDelaunay(triangles[0], 2)) {
                flipTriangles(triangles[0], 2);
            }
            if (!isGoodDelaunay(triangles[1], 1)) {
                flipTriangles(triangles[1], 1);
            }
            return;
        }

        int ribConnectId = beginTriangle.iRibs[indexLineInTriangle];
        Triangle nextTriangle = beginTriangle.triangles[indexLineInTriangle];
        beginTriangle = nextTriangle;
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

        addInverseLinkOnTriangle(triangles);

//        beginTriangle = null;
        beginTriangle = triangles[0];

        if (!isGoodDelaunay(triangles[0], 2)) {
            flipTriangles(triangles[0], 2);
        }
        if (!isGoodDelaunay(triangles[1], 1)) {
            flipTriangles(triangles[1], 1);
        }
        if (!isGoodDelaunay(triangles[2], 1)) {
            flipTriangles(triangles[2], 1);
        }
        if (!isGoodDelaunay(triangles[3], 2)) {
            flipTriangles(triangles[3], 2);
        }
    }

    private void getTriangleWays(Triangle triangle, List<Triangle> triangles) {
        if (triangle == null)
            return;
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

    @Override
    public List<Point[]> getTriangles() {
        List<Triangle> triangles = new ArrayList<>();
        getTriangleWays(beginTriangle, triangles);
        List<Point[]> trianglesPoints = new ArrayList<>();
        for (Triangle tri : triangles) {
            Point[] points = new Point[3];
            for (int i = 0; i < 3; i++) {
                points[i] = new Point(nodes.get(tri.iNodes[i]));
            }
            trianglesPoints.add(points);
        }

        return trianglesPoints;
    }

    /**
     * Create rectangle for future check point outside rectangle.
     * Dimension: 2D
     *
     * @author Izyumov Konstantin
     * @since 05/04/2016
     */
    private static class BorderBox {
        private double x_min;
        private double x_max;
        private double y_min;
        private double y_max;

        BorderBox() {
            x_min = Double.MAX_VALUE;
            x_max = -Double.MAX_VALUE;
            y_min = Double.MAX_VALUE;
            y_max = -Double.MAX_VALUE;
        }

        /**
         * Box change the size by X or Y and input point will be inside in rectangle or on border of rectangle
         *
         * @param point input data
         * @see Point
         */
        void addPoint(Point point) {
            if (point == null)
                return;
            x_min = Math.min(x_min, point.getX());
            x_max = Math.max(x_max, point.getX());
            y_min = Math.min(y_min, point.getY());
            y_max = Math.max(y_max, point.getY());
        }

        double getX_min() {
            return x_min;
        }

        double getX_max() {
            return x_max;
        }

        double getY_min() {
            return y_min;
        }

        double getY_max() {
            return y_max;
        }

        Point getCenter() {
            return new Point(
                    (x_min + x_max) / 2.0,
                    (y_min + y_max) / 2.0
            );
        }

        void scale(double scaleFactor, Point center) {
            x_min = center.getX() - (center.getX() - x_min) * scaleFactor;
            x_max = center.getX() + (x_max - center.getX()) * scaleFactor;
            y_min = center.getY() - (center.getY() - y_min) * scaleFactor;
            y_max = center.getY() + (y_max - center.getY()) * scaleFactor;
        }

        public boolean isInBoxWithBorder(Point point) {
            if (x_min - Precisions.epsilon() <= point.getX() && point.getX() <= x_max + Precisions.epsilon())
                if (y_min - Precisions.epsilon() <= point.getY() && point.getY() <= y_max + Precisions.epsilon())
                    return true;
            return false;
        }
    }

    private static class GeometryPointTriangle {

        enum PointTriangleState {
            POINT_ON_LINE_0,
            POINT_ON_LINE_1,
            POINT_ON_LINE_2,
            POINT_ON_CORNER,
            POINT_INSIDE,
            POINT_OUTSIDE,
            POINT_OUTSIDE_LINE_0,
            POINT_OUTSIDE_LINE_1,
            POINT_OUTSIDE_LINE_2,
        }

        static GeometryPointTriangle.PointTriangleState statePointInTriangle(Point p, Point[] tri) {
            for (triangulation.elements.Point aTri : tri) {
                if (p.equals(aTri))
                    return PointTriangleState.POINT_ON_CORNER;
            }

            BigDecimal line1 = Geometry.pointOnLine(tri[0], p, tri[1]);
            if (GeometryCoordinate.isPointInRectangle(p, tri[0], tri[1])) {
                if (Geometry.is3pointsCollinear(line1))
                    return PointTriangleState.POINT_ON_LINE_0;
            }
            if (!Geometry.isCounterClockwise(line1)) {
                return PointTriangleState.POINT_OUTSIDE_LINE_0;
            }

            BigDecimal line2 = Geometry.pointOnLine(tri[1], p, tri[2]);
            if (GeometryCoordinate.isPointInRectangle(p, tri[1], tri[2])) {
                if (Geometry.is3pointsCollinear(line2))
                    return PointTriangleState.POINT_ON_LINE_1;
            }
            if (!Geometry.isCounterClockwise(line2)) {
                return PointTriangleState.POINT_OUTSIDE_LINE_1;
            }

            BigDecimal line3 = Geometry.pointOnLine(tri[2], p, tri[0]);
            if (GeometryCoordinate.isPointInRectangle(p, tri[2], tri[0])) {
                if (Geometry.is3pointsCollinear(line3))
                    return PointTriangleState.POINT_ON_LINE_2;
            }
            if (!Geometry.isCounterClockwise(line3)) {
                return PointTriangleState.POINT_OUTSIDE_LINE_2;
            }

            int[] sign = new int[]{
                    line1.compareTo(Precisions.bigEpsilon()),
                    line2.compareTo(Precisions.bigEpsilon()),
                    line3.compareTo(Precisions.bigEpsilon())
            };
            if (sign[0] == sign[1] && sign[1] == sign[2] && sign[0] != 0) {
                return PointTriangleState.POINT_INSIDE;
            }

            return PointTriangleState.POINT_OUTSIDE;

        }
    }

    private static class GeometryCoordinate extends triangulationAdvance.TriangulationAdvance.Geometry {

        static boolean isPointInRectangle(Point point, Point... list) {
            BorderBox borderBox = new BorderBox();
            for (int i = 0; i < list.length; i++) {
                borderBox.addPoint(list[i]);
                if (i > 2 && borderBox.isInBoxWithBorder(point)) {
                    return true;
                }
            }
            return borderBox.isInBoxWithBorder(point);
        }
    }

    private static class Geometry {

        static BigDecimal pointOnLine(Point p1, Point p2, Point p3) {

            BigDecimal bdX1 = new BigDecimal(p1.getX());
            BigDecimal bdX2 = new BigDecimal(p2.getX());
            BigDecimal bdX3 = new BigDecimal(p3.getX());
            BigDecimal bdY1 = new BigDecimal(p1.getY());
            BigDecimal bdY2 = new BigDecimal(p2.getY());
            BigDecimal bdY3 = new BigDecimal(p3.getY());
            BigDecimal dX21 = bdX2.add(bdX1.negate());
            BigDecimal dY21 = bdY2.add(bdY1.negate());
            BigDecimal dX32 = bdX3.add(bdX2.negate());
            BigDecimal dY32 = bdY3.add(bdY2.negate());

            BigDecimal left = dY21.multiply(dX32);
            BigDecimal right = dY32.multiply(dX21).negate();

            BigDecimal result = left.add(right);

//            BigDecimal result = BigDecimal.valueOf(
//                    (p2.getY() - p1.getY()) * (p3.getX() - p2.getX()) - (p3.getY() - p2.getY()) * (p2.getX() - p1.getX())
//            );

            return result;
        }

        static boolean is3pointsCollinear(BigDecimal result) {
            return result.abs().compareTo(Precisions.bigEpsilon()) < 0;
        }

        static boolean is3pointsCollinear(Point p1, Point p2, Point p3) {
            return is3pointsCollinear(pointOnLine(p1, p2, p3));
        }

        static double det(double a[][]) {
            double det = a[0][0] * a[1][1] * a[2][2] + a[1][0] * a[2][1] * a[0][2] + a[0][1] * a[1][2] * a[2][0]
                    - a[0][2] * a[1][1] * a[2][0] - a[0][1] * a[1][0] * a[2][2] - a[1][2] * a[2][1] * a[0][0];
            return det;
        }


        static boolean isPointInCircle(Point[] circlePoints, Point point) {

            double x1 = circlePoints[0].getX();
            double y1 = circlePoints[0].getY();
            double z1 = x1 * x1 + y1 * y1;

            double x2 = circlePoints[1].getX();
            double y2 = circlePoints[1].getY();
            double z2 = x2 * x2 + y2 * y2;

            double x3 = circlePoints[2].getX();
            double y3 = circlePoints[2].getY();
            double z3 = x3 * x3 + y3 * y3;

            double a = det(new double[][]{
                    {x1, y1, 1},
                    {x2, y2, 1},
                    {x3, y3, 1}
            });

            double b = det(new double[][]{
                    {z1, y1, 1},
                    {z2, y2, 1},
                    {z3, y3, 1}
            });

            double c = det(new double[][]{
                    {z1, x1, 1},
                    {z2, x2, 1},
                    {z3, x3, 1}
            });

            double d = det(new double[][]{
                    {z1, x1, y1},
                    {z2, x2, y2},
                    {z3, x3, y3}
            });

            double x0 = point.getX();
            double y0 = point.getY();
            double z0 = x0 * x0 + y0 * y0;

            return Math.signum(a) * (a * z0 - b * x0 + c * y0 - d) < 0;
        }

        static boolean isCounterClockwise(BigDecimal result) {
            return result.compareTo(BigDecimal.ZERO) > 0;
        }

        static boolean isCounterClockwise(Point a, Point b, Point c) {

            BigDecimal result = pointOnLine(a, b, c);
            return isCounterClockwise(result);
        }


        static Point[] convexHull(Point[] inputPoints) {

            if (inputPoints.length < 2)
                return inputPoints;

            Set<Point> uniquePoints = new HashSet<>();
            for (int i = 0; i < inputPoints.length; i++) {
                uniquePoints.add(inputPoints[i]);
            }

            List<Point> arrayUnique = new ArrayList<>(uniquePoints);

            Collections.sort(arrayUnique, new Comparator<Point>() {
                @Override
                public int compare(Point first, Point second) {
                    if ((first).getX() == (second).getX()) {
                        if ((first).getY() > (second).getY())
                            return 1;
                        if ((first).getY() < (second).getY())
                            return -1;
                        return 0;
                    }
                    if ((first).getX() > (second).getX())
                        return 1;
                    if ((first).getX() < (second).getX())
                        return -1;
                    return 0;
                }
            });

            int n = arrayUnique.size();
            Point[] P = new Point[n];
            for (int i = 0; i < n; i++) {
                P[i] = arrayUnique.get(i);
            }

            Point[] H = new Point[2 * n];

            int k = 0;
            // Build lower hull
            for (int i = 0; i < n; ++i) {
                while (k >= 2 && isCounterClockwise(H[k - 2], H[k - 1], P[i])) {
                    k--;
                }
                H[k++] = P[i];
            }

            // Build upper hull
            for (int i = n - 2, t = k + 1; i >= 0; i--) {
                while (k >= t && isCounterClockwise(H[k - 2], H[k - 1], P[i])) {
                    k--;
                }
                H[k++] = P[i];
            }
            if (k > 1) {
                H = (Point[]) Arrays.copyOfRange(H, 0, k - 1); // remove non-hull vertices after k; remove k - 1 which is a duplicate
            }
            return H;
        }

        public static boolean isAtRightOf(Point a, Point b, Point c) {
            return isCounterClockwise(a, b, c);
        }

        public static double distanceLineAndPoint(Point lineP1, Point lineP2, Point p) {
            double A;
            double B = 1;
            double C;
            double distance;
            if (Math.abs(lineP2.getY() - lineP1.getY()) < Math.abs(lineP2.getX() - lineP1.getX())) {
                A = -(lineP2.getY() - lineP1.getY()) / (lineP2.getX() - lineP1.getX());
                C = -lineP1.getY() - A * lineP1.getX();
                distance = Math.abs((A * p.getX() + B * p.getY() + C) / Math.sqrt(A * A + B * B));
            } else {
                A = -(lineP2.getX() - lineP1.getX()) / (lineP2.getY() - lineP1.getY());
                C = -lineP1.getX() - A * lineP1.getY();
                distance = Math.abs((A * p.getY() + B * p.getX() + C) / Math.sqrt(A * A + B * B));
            }

            return distance;
        }
    }

    private static class Precisions {

        private static double epsilon = 1.0E-9;
        private static BigDecimal bigEpsilon = BigDecimal.valueOf(epsilon);

        static double epsilon() {
            return epsilon;
        }

        static BigDecimal bigEpsilon() {
            return bigEpsilon;
        }
    }
}
