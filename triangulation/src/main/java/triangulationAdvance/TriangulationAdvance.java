package triangulationAdvance;

import java.math.BigDecimal;
import java.util.*;

/**
 * triangulationAdvance.TriangulationAdvance
 * for step - triangulation convexHull: "Divide and Conquer"
 * Performance for worst-case: O(N^2)
 * for step - triangulation with restrictions: "Simple interactive method"
 * Performance for worst-case: O(N^2)
 * Philosophy of triangulation:
 * 1. Add 4 "fake" point - for guarantee all other point in "fake" region
 * 2. triangulation.Triangulation and delaunay checking
 * 3. Cut triangulation by convexHull region or external region
 *
 * @author Izyumov Konstantin
 *         book "Algoritm building and analyse triangulation", A.B.Skvorcov.
 */
public class TriangulationAdvance {
    //
    // triangulationAdvance.TriangulationAdvance data structure  "Nodes, ribs и triangles"
    //
    // Array of nodes - type: Point
    private final List<Point> nodes = new ArrayList<>();
    // Begin triangle - type: Triangle
    private TriangleStructure beginTriangle = new TriangleStructure();
    // Linked list of triangles
    private final List<TriangleStructure> triangleStructureList = new LinkedList<>();

    private boolean isCounterClockwise(TriangleStructure triangle) {
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
    public TriangulationAdvance(final Point[] points) {
        createConvexHullTriangles(createConvexHullWithoutPointInLine(points));
        for (Point point : points) {
            addNextPoint(point);
        }
    }

    private void createConvexHullTriangles(List<Point> points) {
        nodes.add(points.get(0));
        int indexPoint0 = nodes.size() - 1;
        nodes.add(points.get(1));
        int indexPoint1 = nodes.size() - 1;
        int commonRib = getIdRib();
        TriangleStructure commonTriangle = null;

        for (int i = 2; i < points.size(); i++) {
            nodes.add(points.get(i));
            int indexPoint2 = nodes.size() - 1;
            int rib12 = getIdRib();
            int rib20 = getIdRib();

            TriangleStructure triangle = new TriangleStructure();
            triangle.iNodes = new int[]{
                    indexPoint0, indexPoint1, indexPoint2
            };
            triangle.iRibs = new int[]{
                    commonRib, rib12, rib20
            };
            triangle.triangles = new TriangleStructure[]{commonTriangle, null, null};

            if (commonTriangle != null) {
                commonTriangle.triangles[2] = triangle;
            }

            indexPoint1 = i;
            commonRib = rib20;
            commonTriangle = triangle;

            triangleStructureList.add(triangle);
        }
        beginTriangle = commonTriangle;
    }

    private List<Point> createConvexHullWithoutPointInLine(final Point[] points) {
        List<Point> convexPoints = new ArrayList<>(Arrays.asList(Geometry.convexHull(points)));

        List<Integer> removesIndex = new ArrayList<>();
        for (int i = 0; i < convexPoints.size(); i++) {
            int position0 = i;
            int position1 = i + 1 >= convexPoints.size() ? i + 1 - convexPoints.size() : i + 1;
            int position2 = i + 2 >= convexPoints.size() ? i + 2 - convexPoints.size() : i + 2;
            if (Geometry.is3pointsCollinear(
                    convexPoints.get(position0),
                    convexPoints.get(position1),
                    convexPoints.get(position2))) {
                removesIndex.add(position1);
            }
        }

        Collections.sort(removesIndex);
        for (int i = removesIndex.size() - 1; i >= 0; i--) {
            convexPoints.remove((int) removesIndex.get(i));
        }

        return convexPoints;
    }

    private void flipTriangles(TriangleStructure triangle, int indexTriangle) {

        int pointNewTriangle = triangle.iNodes[normalizeSizeBy3(indexTriangle + 2)];
        int commonRib = triangle.iRibs[indexTriangle];
        TriangleStructure[] baseTriangles = new TriangleStructure[]{
                triangle,
                triangle.triangles[indexTriangle]
        };

        // global region
        List<TriangleStructure> region = new ArrayList<>();
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
                    internalTriangle.iNodes[normalizeSizeBy3(indexCommonRib + 2)]
            };
            t1.triangles = new TriangleStructure[]{
                    internalTriangle.triangles[normalizeSizeBy3(indexCommonRib + 1)]
            };
            region.add(t1);
            TriangleStructure t2 = new TriangleStructure();
            t2.iRibs = new int[]{
                    internalTriangle.iRibs[normalizeSizeBy3(indexCommonRib + 2)]
            };
            t2.iNodes = new int[]{
                    internalTriangle.iNodes[normalizeSizeBy3(indexCommonRib + 2)],
                    internalTriangle.iNodes[normalizeSizeBy3(indexCommonRib + 3)]
            };
            t2.triangles = new TriangleStructure[]{
                    internalTriangle.triangles[normalizeSizeBy3(indexCommonRib + 2)]
            };
            region.add(t2);
        }

        //loopization
        boolean isLoop = true;
        for (int i = 0; i < region.size(); i++) {
            int next = i + 1 > region.size() - 1 ? 0 : i + 1;
            if (region.get(i).iNodes[1] != region.get(next).iNodes[0]) {
                isLoop = false;
            }
        }
        if (!isLoop) {
            return;
        }

        //checking base point
        if (region.get(0).iNodes[1] != pointNewTriangle) {
            return;
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
            return;
        }

        //convexHull checking
        if (Geometry.convexHull(new Point[]{
                nodes.get(region.get(0).iNodes[0]),
                nodes.get(region.get(1).iNodes[0]),
                nodes.get(region.get(2).iNodes[0]),
                nodes.get(region.get(3).iNodes[0]),
        }).length < 4) {
            return;
        }

        //separate on 2 triangles

        int newCommonRib = getIdRib();

        TriangleStructure[] triangles = new TriangleStructure[2];
        for (int i = 0; i < 2; i++) {
            triangles[i] = new TriangleStructure();
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
        triangles[0].triangles = new TriangleStructure[]{
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
        triangles[1].triangles = new TriangleStructure[]{
                region.get(3).triangles[0],
                region.get(0).triangles[0],
                triangles[0]
        };

        //inverse link on triangle
        addInverseLinkOnTriangle(triangles);

        triangleStructureList.add(triangles[0]);
        triangleStructureList.add(triangles[1]);

        //move beginTriangle
        beginTriangle = triangles[0];

        //add null in old triangles
        for (TriangleStructure base: baseTriangles) {
            NullableTriangle(base);
        }
    }

    private boolean isGoodDelaunay(TriangleStructure triangle, int indexTriangle) {
        if (triangle.triangles[indexTriangle] == null) {
            return true;
        }
        return !Geometry.isPointInCircle(
                new Point[]{
                        nodes.get(triangle.iNodes[0]),
                        nodes.get(triangle.iNodes[1]),
                        nodes.get(triangle.iNodes[2])},
                nodes.get(triangle.triangles[indexTriangle].iNodes[normalizeSizeBy3(indexTriangle + 2)]));
    }

    private void addNextPoint(Point nextPoint) {
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
                System.out.println("STRANGE");
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

        NullableTriangle(beginTriangle);

        beginTriangle = triangles[0];
        addInverseLinkOnTriangle(triangles);

        for (int i = 0; i < 3; i++) {
            if (!isGoodDelaunay(triangles[i], 0)) {
                flipTriangles(triangles[i], 0);
            }
            triangleStructureList.add(triangles[i]);
        }
    }

    private void NullableTriangle(TriangleStructure triangle) {
        triangle.triangles = null;
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

            NullableTriangle(beginTriangle);

            beginTriangle = triangles[0];
            if (!isGoodDelaunay(triangles[0], 2)) {
                flipTriangles(triangles[0], 2);
            }
            if (!isGoodDelaunay(triangles[1], 1)) {
                flipTriangles(triangles[1], 1);
            }
            triangleStructureList.add(triangles[0]);
            triangleStructureList.add(triangles[1]);
            return;
        }

        int ribConnectId = beginTriangle.iRibs[indexLineInTriangle];
        TriangleStructure nextTriangle = beginTriangle.triangles[indexLineInTriangle];
        NullableTriangle(beginTriangle);
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

        NullableTriangle(beginTriangle);
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
        Collections.addAll(triangleStructureList, triangles);
    }

    public List<Point[]> getTriangles() {
        Iterator<TriangleStructure> iterator = triangleStructureList.iterator();
        while(iterator.hasNext()){
            if(iterator.next().triangles == null)
                iterator.remove();
        }
        List<TriangleStructure> triangles = new ArrayList<>(triangleStructureList);
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

            Value line1 = triangulationAdvance.TriangulationAdvance.Geometry.pointOnLine(tri[0], p, tri[1]);
            if (GeometryCoordinate.isPointInRectangle(p, tri[0], tri[1])) {
                if (triangulationAdvance.TriangulationAdvance.Geometry.is3pointsCollinear(line1))
                    return PointTriangleState.POINT_ON_LINE_0;
            }
            if (!triangulationAdvance.TriangulationAdvance.Geometry.isCounterClockwise(line1)) {
                return PointTriangleState.POINT_OUTSIDE_LINE_0;
            }

            Value line2 = triangulationAdvance.TriangulationAdvance.Geometry.pointOnLine(tri[1], p, tri[2]);
            if (GeometryCoordinate.isPointInRectangle(p, tri[1], tri[2])) {
                if (triangulationAdvance.TriangulationAdvance.Geometry.is3pointsCollinear(line2))
                    return PointTriangleState.POINT_ON_LINE_1;
            }
            if (!triangulationAdvance.TriangulationAdvance.Geometry.isCounterClockwise(line2)) {
                return PointTriangleState.POINT_OUTSIDE_LINE_1;
            }

            Value line3 = triangulationAdvance.TriangulationAdvance.Geometry.pointOnLine(tri[2], p, tri[0]);
            if (GeometryCoordinate.isPointInRectangle(p, tri[2], tri[0])) {
                if (triangulationAdvance.TriangulationAdvance.Geometry.is3pointsCollinear(line3))
                    return PointTriangleState.POINT_ON_LINE_2;
            }
            if (!triangulationAdvance.TriangulationAdvance.Geometry.isCounterClockwise(line3)) {
                return PointTriangleState.POINT_OUTSIDE_LINE_2;
            }

            boolean[] sign = new boolean[]{
                    triangulationAdvance.TriangulationAdvance.Geometry.isCounterClockwise(line1),
                    triangulationAdvance.TriangulationAdvance.Geometry.isCounterClockwise(line2),
                    triangulationAdvance.TriangulationAdvance.Geometry.isCounterClockwise(line3)
            };
            if (sign[0] && sign[1] && sign[2]) {
                return PointTriangleState.POINT_INSIDE;
            }

            return PointTriangleState.POINT_OUTSIDE;
        }
    }

    private static class GeometryCoordinate extends triangulationAdvance.TriangulationAdvance.Geometry {

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

        static Point[] convexHull(Point[] inputPoints) {
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
