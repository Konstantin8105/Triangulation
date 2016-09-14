package triangulation;

import java.math.BigDecimal;
import java.util.*;

/**
 * Triangulation data structure  "Nodes, ribs и triangles"
 *
 * @author Izyumov Konstantin
 *         book "Algoritm building and analyse triangulation", A.B.Skvorcov.
 */
public class TriangulationDelaunay {
    // Array of nodes - type: Point
    private final List<Point> nodes = new ArrayList<>();

    // Linked list of triangles
    private class TriangleList {

        private int amountNullableElements = 0;
        private final List<TriangleStructure> triangleStructureList = new LinkedList<>();
        private int maxAmountNullableElements = Integer.MAX_VALUE / 2;

        public void add(TriangleStructure triangle) {
            triangleStructureList.add(triangle);
        }

        public void addAll(TriangleStructure[] triangles) {
            Collections.addAll(triangleStructureList, triangles);
        }

        private void removeNullTriangles() {
            amountNullableElements = 0;
            Iterator<TriangleStructure> iterator = triangleStructureList.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().triangles == null)
                    iterator.remove();
            }
        }

        public int size() {
            return triangleStructureList.size();
        }

        public List<TriangleStructure> get() {
            removeNullTriangles();
            return triangleStructureList;
        }

        public void setMaxAmountNullableElements(int maxAmountNullableElements) {
            this.maxAmountNullableElements = maxAmountNullableElements;
        }

        public TriangleStructure getFirstNotNullableElement() {
            for (TriangleStructure triangle : triangleStructureList)
                if (triangle.triangles != null)
                    return triangle;
            return null;
        }

        private void NullableTriangle(TriangleStructure triangle) {
            triangle.triangles = null;
            amountNullableElements++;
            if (amountNullableElements > maxAmountNullableElements) {
                removeNullTriangles();
            }
        }
    }

    private TriangleList triangleList = new TriangleList();
    private Flipper flipper = new Flipper();


    private class Flipper {
        private final Stack<FlipStructure> buffer = new Stack<>();

        public void add(TriangleStructure triangle, int index) {
            buffer.add(new FlipStructure(triangle, index));
        }

        private class FlipStructure {
            public TriangleStructure triangle;
            public int side;

            public FlipStructure(TriangleStructure triangle, int side) {
                this.triangle = triangle;
                this.side = side;
            }
        }

        void run() {
            while (!buffer.empty()) {
                FlipStructure next = buffer.pop();

                if (next.triangle.triangles == null)
                    continue;

                if (next.triangle.triangles[next.side] == null)
                    continue;

                Point p0 = nodes.get(next.triangle.iNodes[normalizeSizeBy3(next.side - 1)]);
                Point p1 = nodes.get(next.triangle.triangles[next.side].iNodes[0]);
                Point p2 = nodes.get(next.triangle.triangles[next.side].iNodes[1]);
                Point p3 = nodes.get(next.triangle.triangles[next.side].iNodes[2]);

                double s1 = (p0.getX() - p1.getX()) * (p0.getY() - p3.getY()) - (p0.getX() - p3.getX()) * (p0.getY() - p1.getY());
                double s2 = (p2.getX() - p3.getX()) * (p2.getX() - p1.getX()) + (p2.getY() - p3.getY()) * (p2.getY() - p1.getY());
                double s3 = (p0.getX() - p1.getX()) * (p0.getX() - p3.getX()) + (p0.getY() - p1.getY()) * (p0.getY() - p3.getY());
                double s4 = (p2.getX() - p3.getX()) * (p2.getY() - p1.getY()) - (p2.getX() - p1.getX()) * (p2.getY() - p3.getY());
                if (s1 * s2 + s3 * s4 >= 0)
                    continue;
                else
                    flipTriangles(next.triangle, next.side);

            }
        }
    }

    public static double AMOUNT_SEARCHER_FACTOR = 0.1D;
    public static double AMOUNT_CLEANING_FACTOR_TRIANGLE_STRUCTURE = 1.0D;
    public static double RATIO_DELETING_CONVEX_POINT_FROM_POINT_LIST = 0.2D;
    public static int MINIMAL_POINTS_FOR_CLEANING = 100000;

    private Searcher searcher;

    private class Searcher {
        private final TriangleStructure[] searcher;
        private final double[] elevations;
        private int positionSearcher = 0;

        Searcher(TriangleStructure init, BorderBox box, int amountOfPoints) {
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

        TriangleStructure getSearcher() {
            return searcher[positionSearcher];
        }

        public void setSearcher(TriangleStructure searcher) {
            this.searcher[positionSearcher] = searcher;
        }

        public void chooseSearcher(Point point) {
            for (int i = searcher.length - 1; i >= 0; i--) {
                if (point.getY() > elevations[i]) {
                    positionSearcher = i;
                    break;
                }
            }

            if (searcher[positionSearcher].triangles != null)
                return;

            for (int i = 0; i < searcher.length; i++) {
                if (searcher[normalizeSizeByL(positionSearcher + i)].triangles != null) {
                    searcher[positionSearcher] = searcher[normalizeSizeByL(positionSearcher + i)];
                    return;
                }
            }

            searcher[positionSearcher] = triangleList.getFirstNotNullableElement();
        }

        private int normalizeSizeByL(int index) {
            if (0 <= index && index < searcher.length) {
                return index;
            }
            if (index < 0)
                return normalizeSizeBy3(index + searcher.length);
            return normalizeSizeBy3(index - searcher.length);
        }
    }

    private boolean isCounterClockwise(TriangleStructure triangle) {
        return TriangulationDelaunay.Geometry.isCounterClockwise(
                nodes.get(triangle.iNodes[0]),
                nodes.get(triangle.iNodes[1]),
                nodes.get(triangle.iNodes[2])
        );
    }

    private int idMaximalRibs = 0;

    private int getIdRib() {
        return idMaximalRibs++;
    }

    public TriangulationDelaunay() {
    }

    // constructor for create convexHull region at the base on points
    public TriangulationDelaunay(Point[] points) {
        run(points);
    }

    public void run(Point[] input) {
        List<Point>[] pointArray = Geometry.convexHullDouble(input);
        List<Point> convexPoints = pointArray[0];
        BorderBox box = createConvexHullTriangles(convexPoints);
        searcher = new Searcher(triangleList.getFirstNotNullableElement(), box, pointArray[1].size());

        int sqrtStep = (int) Math.sqrt(pointArray[1].size());

        if (pointArray[1].size() >= MINIMAL_POINTS_FOR_CLEANING) {
            int amount = max(1, (int) (AMOUNT_CLEANING_FACTOR_TRIANGLE_STRUCTURE * pointArray[1].size()));// * (double) sqrtStep));
            triangleList.setMaxAmountNullableElements(amount);
        }
        for (int i = 0; i < pointArray[1].size(); i++) {
            addNextPoint(pointArray[1].get(i));
            flipper.run();
//            if (i % 1000 == 0)
//                System.err.println(i);
        }
        flipper.run();
    }

    private int max(int a, int b) {
        if (a > b) return a;
        return b;
    }

    private int min(int a, int b) {
        if (a < b) return a;
        return b;
    }


    private BorderBox createConvexHullTriangles(List<Point> points) {
        int i = 0;
        i++;
        nodes.add(points.get(0));
        int indexPoint0 = nodes.size() - 1;
        i++;
        nodes.add(points.get(1));
        int indexPoint1 = nodes.size() - 1;
        int commonRib = getIdRib();
        TriangleStructure commonTriangle = null;

        int k = 0;
        while (i + k < points.size()) {
            i++;
            nodes.add(points.get(i - 1));
            int indexPoint2 = nodes.size() - 1;
            int rib12 = getIdRib();
            int rib20 = getIdRib();

            TriangleStructure triangle = new TriangleStructure();
            triangle.iNodes = new int[]{indexPoint0, indexPoint1, indexPoint2};
            triangle.iRibs = new int[]{commonRib, rib12, rib20};
            triangle.triangles = new TriangleStructure[]{commonTriangle, null, null};
            if (commonTriangle != null) {
                commonTriangle.triangles[1] = triangle;
            }

            triangleList.add(triangle);

            if (i + k >= points.size())
                break;

            int indexPoint0_next = indexPoint0;
            int indexPoint1_next = indexPoint2;
            k++;
            nodes.add(points.get(points.size() - k));
            int indexPoint2_next = nodes.size() - 1;

            int rib12_next = getIdRib();
            int rib20_next = getIdRib();

            TriangleStructure triangle2 = new TriangleStructure();
            triangle2.iNodes = new int[]{indexPoint0_next, indexPoint1_next, indexPoint2_next};
            triangle2.iRibs = new int[]{rib20, rib12_next, rib20_next};
            triangle2.triangles = new TriangleStructure[]{
                    triangle, null, null
            };
            triangle.triangles[2] = triangle2;
            triangleList.add(triangle2);


            indexPoint0 = indexPoint2_next;
            indexPoint1 = indexPoint1_next;
            commonRib = rib12_next;
            commonTriangle = triangle2;
        }

        BorderBox borderBox = new BorderBox();
        for (Point point : points) {
            borderBox.addPoint(point);
        }
        return borderBox;
    }


    private void flipTriangles(TriangleStructure triangle, int indexTriangle) {
//        if (triangle == null)
//            return;

        TriangleStructure[] region = new TriangleStructure[4];

        int pointNewTriangle = triangle.iNodes[normalizeSizeBy3(indexTriangle - 1)];
        int commonRib = triangle.iRibs[indexTriangle];
        TriangleStructure[] baseTriangles = new TriangleStructure[]{
                triangle,
                triangle.triangles[indexTriangle]
        };

        // global region
        int position = 0;
        for (int i = 0; i < 2; i++) {
            TriangleStructure internalTriangle = baseTriangles[i];
            int indexCommonRib = -1;
            for (int j = 0; j < 3; j++) {
                if (commonRib == internalTriangle.iRibs[j]) {
                    indexCommonRib = j;
                }
            }
            TriangleStructure t1 = new TriangleStructure();
            t1.iRibs = new int[]{
                    internalTriangle.iRibs[normalizeSizeBy3(indexCommonRib + 1)]
            };
            t1.iNodes = new int[]{
                    internalTriangle.iNodes[normalizeSizeBy3(indexCommonRib + 1)],
                    internalTriangle.iNodes[normalizeSizeBy3(indexCommonRib - 1)]
            };
            t1.triangles = new TriangleStructure[]{
                    internalTriangle.triangles[normalizeSizeBy3(indexCommonRib + 1)]
            };
            region[position++] = t1;
            TriangleStructure t2 = new TriangleStructure();
            t2.iRibs = new int[]{
                    internalTriangle.iRibs[normalizeSizeBy3(indexCommonRib - 1)]
            };
            t2.iNodes = new int[]{
                    internalTriangle.iNodes[normalizeSizeBy3(indexCommonRib - 1)],
                    internalTriangle.iNodes[normalizeSizeBy3(indexCommonRib)]
            };
            t2.triangles = new TriangleStructure[]{
                    internalTriangle.triangles[normalizeSizeBy3(indexCommonRib - 1)]
            };
            region[position++] = t2;
        }


        if (Geometry.isCounterClockwise(
                nodes.get(region[1].iNodes[0]),
                nodes.get(region[1].iNodes[1]),
                nodes.get(region[2].iNodes[1])))
            return;


        if (Geometry.isCounterClockwise(
                nodes.get(region[3].iNodes[0]),
                nodes.get(region[3].iNodes[1]),
                nodes.get(region[0].iNodes[1])
        ))
            return;

        //checking base point
        if (region[0].iNodes[1] != pointNewTriangle) {
            return;
        }

        //separate on 2 triangles

        int newCommonRib = getIdRib();

        TriangleStructure[] triangles = new TriangleStructure[2];
        for (int i = 0; i < 2; i++) {
            triangles[i] = new TriangleStructure();
        }

        triangles[0].iNodes = new int[]{
                region[1].iNodes[0],
                region[1].iNodes[1],
                region[2].iNodes[1]
        };
        triangles[0].iRibs = new int[]{
                region[1].iRibs[0],
                region[2].iRibs[0],
                newCommonRib
        };
        triangles[0].triangles = new TriangleStructure[]{
                region[1].triangles[0],
                region[2].triangles[0],
                triangles[1]
        };

        triangles[1].iNodes = new int[]{
                region[3].iNodes[0],
                region[3].iNodes[1],
                region[0].iNodes[1]
        };
        triangles[1].iRibs = new int[]{
                region[3].iRibs[0],
                region[0].iRibs[0],
                newCommonRib
        };
        triangles[1].triangles = new TriangleStructure[]{
                region[3].triangles[0],
                region[0].triangles[0],
                triangles[0]
        };

        //inverse link on triangle
        addInverseLinkOnTriangle(triangles);

        triangleList.add(triangles[0]);
        triangleList.add(triangles[1]);

        //move beginTriangle
        searcher.setSearcher(triangles[0]);

        //add null in old triangles
        for (TriangleStructure base : baseTriangles) {
            triangleList.NullableTriangle(base);
        }

        flipper.add(triangles[0], 0);
        flipper.add(triangles[0], 1);

        flipper.add(triangles[1], 0);
        flipper.add(triangles[1], 1);
    }

    private void addNextPoint(Point nextPoint) {

        searcher.chooseSearcher(nextPoint);

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
            case POINT_ON_CORNER:
                break;
            default:
                System.out.println("STRANGE POINT : " + nextPoint);
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

    private GeometryPointTriangle.PointTriangleState movingByConvexHull(Point point) {

        TriangleStructure beginTriangle = searcher.getSearcher();

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

        searcher.setSearcher(beginTriangle);

        return GeometryPointTriangle.statePointInTriangle(point, trianglePoint);
    }

    private void addNextPointInTriangle(Point nextPoint) {

        TriangleStructure beginTriangle = searcher.getSearcher();

        nodes.add(nextPoint);
        int pointIndex = nodes.size() - 1;
        int rib0 = getIdRib();
        int rib1 = getIdRib();
        int rib2 = getIdRib();

        TriangleStructure[] triangles = new TriangleStructure[3];
        for (int i = 0; i < 3; i++) {
            triangles[i] = new TriangleStructure();
        }

        triangles[0].iNodes = new int[]{beginTriangle.iNodes[0], beginTriangle.iNodes[1], pointIndex};
        triangles[0].iRibs = new int[]{beginTriangle.iRibs[0], rib1, rib0};

        triangles[1].iNodes = new int[]{beginTriangle.iNodes[1], beginTriangle.iNodes[2], pointIndex};
        triangles[1].iRibs = new int[]{beginTriangle.iRibs[1], rib2, rib1};

        triangles[2].iNodes = new int[]{beginTriangle.iNodes[2], beginTriangle.iNodes[0], pointIndex};
        triangles[2].iRibs = new int[]{beginTriangle.iRibs[2], rib0, rib2};

        triangles[0].triangles = new TriangleStructure[]{beginTriangle.triangles[0], triangles[1], triangles[2]};
        triangles[1].triangles = new TriangleStructure[]{beginTriangle.triangles[1], triangles[2], triangles[0]};
        triangles[2].triangles = new TriangleStructure[]{beginTriangle.triangles[2], triangles[0], triangles[1]};

        triangleList.NullableTriangle(beginTriangle);

        searcher.setSearcher(triangles[0]);
        addInverseLinkOnTriangle(triangles);

        for (int i = 0; i < 3; i++) {
            flipper.add(triangles[i], 0);
            triangleList.add(triangles[i]);
        }
    }


    private void addInverseLinkOnTriangle(TriangleStructure[] triangles) {
        for (TriangleStructure triangle : triangles) {
            if (triangle == null)
                continue;
            for (int j = 0; j < 3; j++) {
                TriangleStructure externalTriangle = triangle.triangles[j];
                int commonRib = triangle.iRibs[j];
                if (externalTriangle != null) {
                    for (int k = 0; k < 3; k++) {
                        if (externalTriangle.iRibs[k] == commonRib) {
                            externalTriangle.triangles[k] = triangle;
                        }
                    }
                }
            }
        }
        boolean inverseAgain = false;
        for (TriangleStructure triangle : triangles) {
            if (isCounterClockwise(triangle)) {
                triangle.changeClockwise();
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

        TriangleStructure beginTriangle = searcher.getSearcher();

        nodes.add(nextPoint);
        int pointIndex = nodes.size() - 1;

        int rib0 = getIdRib();
        int rib1 = getIdRib();
        int rib2 = getIdRib();
        int rib3 = getIdRib();

        TriangleStructure triangles[] = new TriangleStructure[4];
        for (int i = 0; i < 4; i++) {
            triangles[i] = new TriangleStructure();
        }


        triangles[0].iNodes = new int[]{
                beginTriangle.iNodes[normalizeSizeBy3(indexLineInTriangle)],
                pointIndex,
                beginTriangle.iNodes[normalizeSizeBy3(indexLineInTriangle - 1)]
        };

        triangles[1].iNodes = new int[]{
                pointIndex,
                beginTriangle.iNodes[normalizeSizeBy3(indexLineInTriangle + 1)],
                beginTriangle.iNodes[normalizeSizeBy3(indexLineInTriangle - 1)]
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


        triangles[0].triangles = new TriangleStructure[]{
                null,
                triangles[1],
                beginTriangle.triangles[normalizeSizeBy3(indexLineInTriangle - 1)]
        };
        triangles[1].triangles = new TriangleStructure[]{
                null,
                beginTriangle.triangles[normalizeSizeBy3(indexLineInTriangle + 1)],
                triangles[0]
        };

        if (beginTriangle.triangles[indexLineInTriangle] == null) {
            addInverseLinkOnTriangle(new TriangleStructure[]{triangles[0], triangles[1]});

            triangleList.NullableTriangle(beginTriangle);

            searcher.setSearcher(triangles[0]);

            flipper.add(triangles[0], 2);
            flipper.add(triangles[1], 1);

            triangleList.add(triangles[0]);
            triangleList.add(triangles[1]);
            return;
        }

        int ribConnectId = beginTriangle.iRibs[indexLineInTriangle];
        TriangleStructure nextTriangle = beginTriangle.triangles[indexLineInTriangle];
        triangleList.NullableTriangle(beginTriangle);
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
                beginTriangle.iNodes[normalizeSizeBy3(indexLineInTriangle - 1)]
        };
        triangles[3].iNodes = new int[]{
                beginTriangle.iNodes[indexLineInTriangle],
                pointIndex,
                beginTriangle.iNodes[normalizeSizeBy3(indexLineInTriangle - 1)]
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

        triangles[2].triangles = new TriangleStructure[]{
                triangles[0],
                beginTriangle.triangles[normalizeSizeBy3(indexLineInTriangle + 1)],
                triangles[3]
        };

        triangles[3].triangles = new TriangleStructure[]{
                triangles[1],
                triangles[2],
                beginTriangle.triangles[normalizeSizeBy3(indexLineInTriangle - 1)]
        };

        addInverseLinkOnTriangle(triangles);

        triangleList.NullableTriangle(beginTriangle);
        searcher.setSearcher(triangles[0]);

        flipper.add(triangles[0], 2);
        flipper.add(triangles[1], 1);
        flipper.add(triangles[2], 1);
        flipper.add(triangles[3], 2);

        triangleList.addAll(triangles);
    }


    public List<Point[]> getTriangles() {
        List<TriangleStructure> triangles = triangleList.get();
        List<Point[]> trianglesPoints = new ArrayList<>();
        for (TriangleStructure tri : triangles) {
            Point[] points = new Point[3];
            for (int i = 0; i < 3; i++) {
                points[i] = new Point(nodes.get(tri.iNodes[i]));
            }
            trianglesPoints.add(points);
        }
        return trianglesPoints;
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
            for (Point aTri : tri) {
                if (p.equals(aTri))
                    return PointTriangleState.POINT_ON_CORNER;
            }

            Value line1 = TriangulationDelaunay.Geometry.pointOnLine(tri[0], p, tri[1]);
            if (GeometryCoordinate.isPointInRectangle(p, tri[0], tri[1])) {
                if (TriangulationDelaunay.Geometry.is3pointsCollinear(line1))
                    return PointTriangleState.POINT_ON_LINE_0;
            }
            if (!TriangulationDelaunay.Geometry.isCounterClockwise(line1)) {
                return PointTriangleState.POINT_OUTSIDE_LINE_0;
            }

            Value line2 = TriangulationDelaunay.Geometry.pointOnLine(tri[1], p, tri[2]);
            if (GeometryCoordinate.isPointInRectangle(p, tri[1], tri[2])) {
                if (TriangulationDelaunay.Geometry.is3pointsCollinear(line2))
                    return PointTriangleState.POINT_ON_LINE_1;
            }
            if (!TriangulationDelaunay.Geometry.isCounterClockwise(line2)) {
                return PointTriangleState.POINT_OUTSIDE_LINE_1;
            }

            Value line3 = TriangulationDelaunay.Geometry.pointOnLine(tri[2], p, tri[0]);
            if (GeometryCoordinate.isPointInRectangle(p, tri[2], tri[0])) {
                if (TriangulationDelaunay.Geometry.is3pointsCollinear(line3))
                    return PointTriangleState.POINT_ON_LINE_2;
            }
            if (!TriangulationDelaunay.Geometry.isCounterClockwise(line3)) {
                return PointTriangleState.POINT_OUTSIDE_LINE_2;
            }

            boolean[] sign = new boolean[]{
                    TriangulationDelaunay.Geometry.isCounterClockwise(line1),
                    TriangulationDelaunay.Geometry.isCounterClockwise(line2),
                    TriangulationDelaunay.Geometry.isCounterClockwise(line3)
            };
            if (sign[0] && sign[1] && sign[2]) {
                return PointTriangleState.POINT_INSIDE;
            }

            return PointTriangleState.POINT_OUTSIDE;
        }
    }

    private static class GeometryCoordinate extends TriangulationDelaunay.Geometry {

        static boolean isPointInRectangle(Point point, Point... list) {
            //counter.addTime("GeometryCoordinate:isPointInRectangle");
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

    private static class Value {
        private double valueDouble;
        private BigDecimal valueBig;
        private final boolean isValueDouble;

        Value(double valueDouble) {
            this.valueDouble = valueDouble;
            isValueDouble = true;
        }

        Value(BigDecimal valueBig) {
            this.valueBig = valueBig;
            isValueDouble = false;
        }

        boolean isValueDouble() {
            return isValueDouble;
        }

        BigDecimal getValueBig() {
            return valueBig;
        }

        double getValueDouble() {
            return valueDouble;
        }
    }


    public static class Geometry {

        static Value pointOnLine(Point p1, Point p2, Point p3) {
            double value = (p2.getY() - p1.getY()) * (p3.getX() - p2.getX()) -
                    (p3.getY() - p2.getY()) * (p2.getX() - p1.getX());
            if (Math.abs(value) > Precision.valueFactor()) {
                return new Value(value);
            }

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

            return new Value(result);
        }

        static boolean is3pointsCollinear(Value result) {
            if (result.isValueDouble()) {
                return Math.abs(result.getValueDouble()) < Precision.epsilon();
            }
            return result.getValueBig().abs().compareTo(Precision.bigEpsilon()) < 0;
        }

        public static boolean is3pointsCollinear(Point p1, Point p2, Point p3) {
            return is3pointsCollinear(pointOnLine(p1, p2, p3));
        }

        static boolean isCounterClockwise(Value result) {
            if (result.isValueDouble()) {
                return result.getValueDouble() > 0;
            }
            return result.getValueBig().compareTo(BigDecimal.ZERO) > 0;
        }

        public static boolean isCounterClockwise(Point a, Point b, Point c) {
            return isCounterClockwise(pointOnLine(a, b, c));
        }


        public static boolean isAtRightOf(Point a, Point b, Point c) {
            return isCounterClockwise(a, b, c);
        }


        static double det(double a[][]) {
            return a[0][0] * a[1][1] * a[2][2] + a[1][0] * a[2][1] * a[0][2] + a[0][1] * a[1][2] * a[2][0]
                    - a[0][2] * a[1][1] * a[2][0] - a[0][1] * a[1][0] * a[2][2] - a[1][2] * a[2][1] * a[0][0];
        }

        static boolean isPointInCircle(Point point, Point circlePoints0, Point circlePoints1, Point circlePoints2) {
            double x1 = circlePoints0.getX();
            double y1 = circlePoints0.getY();
            double z1 = x1 * x1 + y1 * y1;

            double x2 = circlePoints1.getX();
            double y2 = circlePoints1.getY();
            double z2 = x2 * x2 + y2 * y2;

            double x3 = circlePoints2.getX();
            double y3 = circlePoints2.getY();
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

            return Math.signum(a) * (a * z0 - b * x0 + c * y0 - d) < -Precision.epsilon();
        }


        //Performance O(n*log(n)) in worst case
        public static Point[] convexHull(Point[] inputPoints) {
            if (inputPoints.length < 2)
                return inputPoints;

            List<Point> array = new ArrayList<>(Arrays.asList(inputPoints));

            Collections.sort(array, new Comparator<Point>() {
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

            List<Integer> removedIndex = new ArrayList<>();
            for (int i = 1; i < array.size(); i++) {
                if (array.get(i - 1).equals(array.get(i))) {
                    removedIndex.add(i);
                }
            }
            for (int i = removedIndex.size() - 1; i >= 0; i--) {
                int position = removedIndex.get(i);
                array.remove(position);
            }

            int n = array.size();
            Point[] P = new Point[n];
            for (int i = 0; i < n; i++) {
                P[i] = array.get(i);
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
                H = Arrays.copyOfRange(H, 0, k - 1); // remove non-hull vertices after k; remove k - 1 which is a duplicate
            }
            return H;
        }

        //Performance O(n*log(n)) in worst case
        // Point[0][] - convex points
        // Point[1][] - sorted list of all points
        static List<Point>[] convexHullDouble(Point[] inputPoints) {
            if (inputPoints.length < 2) {
                return null;
            }

            ArrayList<Point> array = new ArrayList<>(Arrays.asList(inputPoints));

            Collections.sort(array, new Comparator<Point>() {
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

            List<Integer> removedIndex = new ArrayList<>();
            for (int i = 1; i < array.size(); i++) {
                if (array.get(i - 1).equals(array.get(i))) {
                    removedIndex.add(i);
                }
            }
            for (int i = removedIndex.size() - 1; i >= 0; i--) {
                int position = removedIndex.get(i);
                array.remove(position);
            }

            int n = array.size();
            Point[] P = new Point[n];
            for (int i = 0; i < n; i++) {
                P[i] = array.get(i);
            }

            Point[] H = new Point[2 * n];
//            List<Point> H = new ArrayList<>(2 * n);

            int k = 0;
//             Build lower hull
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
            List<Point> convexPoints = new ArrayList<>();
            if (k > 1) {
                H = Arrays.copyOfRange(H, 0, k - 1); // remove non-hull vertices after k; remove k - 1 which is a duplicate
                boolean[] removed = new boolean[k - 1];
                for (int position0 = 0; position0 < removed.length; position0++) {
                    int position1 = position0 + 1 >= removed.length ? position0 + 1 - removed.length : position0 + 1;
                    int position2 = position0 + 2 >= removed.length ? position0 + 2 - removed.length : position0 + 2;
                    if (Geometry.is3pointsCollinear(
                            H[position0],
                            H[position1],
                            H[position2])) {
                        removed[position1] = true;
                    }
                }
                for (int i = 0; i < removed.length; i++) {
                    if (!removed[i])
                        convexPoints.add(H[i]);
                }
                if (array.size() > 5) {
                    if ((double) convexPoints.size() / (double) array.size() > RATIO_DELETING_CONVEX_POINT_FROM_POINT_LIST) {
                        boolean[] delete = new boolean[array.size()];
                        int position = 0;
                        for (int i = 0; i < array.size(); i++) {
                            if (array.get(i).equals(convexPoints.get(position))) {
                                delete[i] = true;
                                position++;
                            }
                        }
                        for (int i = array.size() - 1; i >= 0; i--) {
                            if (position >= convexPoints.size())
                                break;
                            if (array.get(i).equals(convexPoints.get(position))) {
                                delete[i] = true;
                                position++;
                            }
                        }
                        ArrayList<Point> newList = new ArrayList<>();
                        for (int i = 0; i < array.size(); i++) {
                            if (!delete[i])
                                newList.add(array.get(i));
                        }
                        array = newList;
                    }
                }
            }

            return new List[]{convexPoints, array};
        }


        static double distanceLineAndPoint(Point lineP1, Point lineP2, Point p) {
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

}
