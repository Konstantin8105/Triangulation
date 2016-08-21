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


    static boolean PRINTABLE = false;
//    private static Counter counter = new Counter("Analyze");

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
            if (PRINTABLE) System.out.println("Triangle::changeClockwise");
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

//            if (PRINTABLE)
//                for (int i = 0; i < 3; i++) {
//                    if (triangles[i] != null) {
//                        if (triangles[i].triangles[0] == this ||
//                                triangles[i].triangles[1] == this ||
//                                triangles[i].triangles[2] == this) {
//                            System.out.println("FAIL Triangle::changeClockwise\"" + this);
//                            for (int j = 0; j < 3; j++) {
//                                System.out.println(triangles[i].triangles[j]);
//                            }
//                        }
//                    }
//                }
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

        if (PRINTABLE) {
            System.out.println("INIT points");
            for (int i = 0; i < points.length; i++) {
                System.out.println(i + ":" + points[i]);
            }
        }

//        counter.clear();
        // TODO: 18.08.2016 fake points must be moved
        createFakeTriangles(points);
        if (PRINTABLE) System.out.println("check fake");
        if (PRINTABLE) check();
        for (int i = 0; i < points.length; i++) {
            if (PRINTABLE)
                System.out.println("\n\n\n\n\n=================================================================");
            if (PRINTABLE) System.out.println("add point - " + points[i]);
            addNextPoint(points[i]);

//        delaunayChecking();
            if (PRINTABLE)
                check();

        }
//        delaunayChecking();
//        for (int i = 0; i < 100; i++) {
//            delaunayChecking();
//        }
        //cutByRegion(convexHull(points));
        //removeFakeTriangles();
        if (PRINTABLE) {
            outTriangles();
        }
//        System.out.println(counter.toString());
    }

    private void outTriangles() {
        System.out.println("\n\n\nPOINTS:");
        for (int i = 0; i < nodes.size(); i++) {
            System.out.println("i:" + i + " " + nodes.get(i).toString());
        }
        List<Triangle> triangles = new ArrayList<>();
        getTriangleWays(beginTriangle, triangles);
        for (int i = 0; i < triangles.size(); i++) {
            System.out.println("\n\n\n NEXT TRIANGLE: " + i);
            for (int j = 0; j < 3; j++) {
                System.out.println(nodes.get(triangles.get(i).iNodes[j]));
            }
        }
    }


    private int amountTriangle = -1;

    private void check() {
        if (PRINTABLE) System.out.println("\n\n\n\n\n\n\n\n\n\n== CHECK ==");
        boolean isPrint = false;
        List<Triangle> triangles = new ArrayList<>();
        getTriangleWays(beginTriangle, triangles);
        for (int p = 0; p < triangles.size(); p++) {
            Triangle triangle = triangles.get(p);
            if (isCounterClockwise(triangle)) {
                isPrint = true;
                System.out.println("check() ---> triangle .... FAIL isCounterClockwise");
            }
            for (int i = 0; i < 3; i++) {
                if (triangle.triangles[i] != null) {
                    int commonRib = triangle.iRibs[i];
                    Triangle externalTriangle = triangle.triangles[i];
                    boolean found = false;
                    for (int j = 0; j < 3; j++) {
                        if (externalTriangle.iRibs[j] == commonRib) {

                            if (triangle.iNodes[i] != externalTriangle.iNodes[j] || triangle.iNodes[normalizeSizeBy3(i + 1)] != externalTriangle.iNodes[j]) {
                                found = true;
                            }
                            if (triangle.iRibs[i] != externalTriangle.iRibs[j] || triangle.iRibs[normalizeSizeBy3(i + 1)] != externalTriangle.iRibs[j]) {
                                found = true;
                            }
                        }
                    }
                    if (!found) {
                        isPrint = true;
                        System.out.println("check() ---> triangle .... FAIL not found");
                    }
                }
            }
            //null area checking
            Point[] points = new Point[]{
                    nodes.get(triangle.iNodes[0]),
                    nodes.get(triangle.iNodes[1]),
                    nodes.get(triangle.iNodes[2])
            };
            if (Geometry.is3pointsCollinear(points[0], points[1], points[2])) {
                isPrint = true;
                System.out.println("=====================");
                for (int i = 0; i < 3; i++) {
                    System.out.println(points[i]);
                }
                System.out.println("check() ---> triangle .... FAIL three points on one line");
            }
            if (area_3point(points) < Precisions.epsilon()) {
                isPrint = true;
                System.out.println("=====================");
                for (int i = 0; i < 3; i++) {
                    System.out.println(points[i]);
                }
                System.out.println("check() ---> triangle .... FAIL area is small");
            }

            for (int pp = p + 1; pp < triangles.size(); pp++) {
                //check triangle in triangle
                for (int i = 0; i < 3; i++) {
                    Point p1 = nodes.get(triangle.iNodes[i]);
                    Point p2 = nodes.get(triangle.iNodes[normalizeSizeBy3(i + 1)]);
                    for (int j = 0; j < 3; j++) {
                        Point p3 = nodes.get(triangles.get(pp).iNodes[j]);
                        if (GeometryPointLine.statePointOnLine(p3, p1, p2) == GeometryPointLine.PointLineState.POINT_ON_LINE) {
                            isPrint = true;
                            System.out.println("check() ---> triangle .... FAIL triangle in triangle");
                        }
                    }
                }
            }

        }
        if (amountTriangle <= triangles.size()) {
            amountTriangle = triangles.size();
        } else {
            System.out.println("Before :" + amountTriangle);
            System.out.println("After  :" + triangles.size());
            System.out.println("check() ---> triangle .... FAIL amount triangles");
            isPrint = true;
        }
        if (isPrint) {
            for (int i = 0; i < nodes.size(); i++) {
                System.out.println(i + ":" + nodes.get(i));
            }
        }
    }

    static private double area_3point(Point[] points) {
        //
        // Area of triangle
        // https://en.wikipedia.org/wiki/Triangle#Computing_the_area_of_a_triangle
        // Using Heron's formula
        //
        double a = Math.sqrt(Math.pow(points[0].getX() - points[1].getX(), 2.) + Math.pow(points[0].getY() - points[1].getY(), 2.));
        double b = Math.sqrt(Math.pow(points[2].getX() - points[1].getX(), 2.) + Math.pow(points[2].getY() - points[1].getY(), 2.));
        double c = Math.sqrt(Math.pow(points[2].getX() - points[0].getX(), 2.) + Math.pow(points[2].getY() - points[0].getY(), 2.));
        double p = (a + b + c) / 2;
        return Math.sqrt(p * Math.abs((p - a) * (p - b) * (p - c)));
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

    // todo create good flip
    private Triangle[] flipTriangles(Triangle triangle, int indexTriangle) {

        if (PRINTABLE) {

            System.out.println("flipTriangles");
        }


        if (PRINTABLE) {
            System.out.println("\n\n\n\nFLIP TRIANGLES");
            System.out.println("Triangle :" + triangle);
            System.out.println("Index    :" + indexTriangle);
            for (int i = 0; i < 3; i++) {
                System.out.println("Point : ==>" + triangle.iNodes[i] + "==>" + nodes.get(triangle.iNodes[i]));
            }
            for (int i = 0; i < 3; i++) {
                System.out.println("Rib : ==>" + triangle.iRibs[i]);
            }
            System.out.println(triangle);

            System.out.println("Triangle :" + triangle.triangles[indexTriangle]);
            for (int i = 0; i < 3; i++) {
                System.out.println("Point : ==>" + triangle.triangles[indexTriangle].iNodes[i] + "==>" + nodes.get(triangle.triangles[indexTriangle].iNodes[i]));
            }
            for (int i = 0; i < 3; i++) {
                System.out.println("Rib : ==>" + triangle.triangles[indexTriangle].iRibs[i]);
            }
            System.out.println(triangle.triangles[indexTriangle]);
        }

        int pointNewTriangle = triangle.iNodes[normalizeSizeBy3(indexTriangle + 2)];
        int commonRib = triangle.iRibs[indexTriangle];
        Triangle[] baseTriangles = new Triangle[]{
                triangle,
                triangle.triangles[indexTriangle]
        };

        // checking
        if (PRINTABLE) {
            System.out.println("pointNewTriangle = " + pointNewTriangle + "\n\n");
            System.out.println("CommonRib = " + commonRib + "\n\n");
            boolean[] isOk = new boolean[]{false, false};
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.println("i:" + i + " j:" + j + " -- " + (baseTriangles[i].iRibs[j] == commonRib));
                    if (baseTriangles[i].iRibs[j] == commonRib) {
                        isOk[i] = true;
                    }
                }
            }
            System.out.println(isOk[0]);
            System.out.println(isOk[1]);
        }

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
                    internalTriangle.triangles[normalizeSizeBy3(i + 1)]
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
                    internalTriangle.triangles[normalizeSizeBy3(i + 2)]
            };
            region.add(t2);
        }

        if (PRINTABLE) {
            for (int i = 0; i < region.size(); i++) {
                System.out.println("\n\nRegion " + i);
                System.out.println("triangles:");
                for (int j = 0; j < region.get(i).triangles.length; j++) {
                    System.out.println(region.get(i).triangles[j]);
                }
                System.out.println("ribs:");
                for (int j = 0; j < region.get(i).iRibs.length; j++) {
                    System.out.println(region.get(i).iRibs[j]);
                }
                System.out.println("nodes:");
                for (int j = 0; j < region.get(i).iNodes.length; j++) {
                    System.out.println(region.get(i).iNodes[j]);
                }
            }
        }


        //todo continue here

        return null;

        /*

        Triangle[] triangles = new Triangle[2];
        for (int i = 0; i < triangles.length; i++) {
            triangles[i] = new Triangle();
        }
        int commonRib = triangle.iRibs[indexTriangle];
        int indexInvertTriangle = -1;
        for (int i = 0; i < 3; i++) {
            if (commonRib == triangle.triangles[indexTriangle].iRibs[i]) {
                indexInvertTriangle = i;
                break;
            }
        }

        if (isCounterClockwise(triangle) != isCounterClockwise(triangle.triangles[indexTriangle])) {
            addInverseLinkOnTriangle(triangles);
//            triangle.changeClockwise();
//            for (int i = 0; i < 3; i++) {
//                if (triangle.iRibs[i] == commonRib) {
//                    indexTriangle = i;
////                    System.out.println("TRIANGLE Clockwise");
//                    break;
//                }
//            }
        }

        int[] ribs = new int[]{
                triangle.iRibs[normalizeSizeBy3(indexTriangle + 1)],
                triangle.iRibs[normalizeSizeBy3(indexTriangle + 2)],
                triangle.triangles[indexTriangle].iRibs[normalizeSizeBy3(indexInvertTriangle + 1)],
                triangle.triangles[indexTriangle].iRibs[normalizeSizeBy3(indexInvertTriangle + 2)]
        };
        Triangle[] outsideTriangles = new Triangle[]{
                triangle.triangles[normalizeSizeBy3(indexTriangle + 1)],
                triangle.triangles[normalizeSizeBy3(indexTriangle + 2)],
                triangle.triangles[indexTriangle].triangles[normalizeSizeBy3(indexInvertTriangle + 1)],
                triangle.triangles[indexTriangle].triangles[normalizeSizeBy3(indexInvertTriangle + 2)]
        };
        int[] pointIndex = new int[]{
                triangle.iNodes[normalizeSizeBy3(indexTriangle + 1)],
                triangle.iNodes[normalizeSizeBy3(indexTriangle + 2)],
                triangle.triangles[indexTriangle].iNodes[normalizeSizeBy3(indexInvertTriangle + 1)],
                triangle.triangles[indexTriangle].iNodes[normalizeSizeBy3(indexInvertTriangle + 2)]
        };

        Point[] points = new Point[pointIndex.length];
        for (int i = 0; i < pointIndex.length; i++) {
            points[i] = nodes.get(pointIndex[i]);
        }
        if (Geometry.is3pointsCollinear(nodes.get(pointIndex[1]), nodes.get(pointIndex[2]), nodes.get(pointIndex[3]))) {
            return null;
        }
        if (Geometry.is3pointsCollinear(nodes.get(pointIndex[3]), nodes.get(pointIndex[0]), nodes.get(pointIndex[1]))) {
            return null;
        }
        if (Geometry.convexHull(points).length != 4) {
//            System.out.println("CONVEX HULL FAIL ---> RETURN");
            return null;
        }

//        System.out.println("flipTriangles()");
//        System.out.println("RIB:");
//        for (int i = 0; i < 4; i++) {
//            System.out.println(ribs[i]);
//        }
//        System.out.println("pointIndex:");
//        for (int i = 0; i < 4; i++) {
//            System.out.println(pointIndex[i] + "-->"+nodes.get(pointIndex[i]));
//        }
//        System.out.println("outsideTriangles:");
//        for (int i = 0; i < 4; i++) {
//            System.out.println(outsideTriangles[i]);
//        }


        commonRib = getIdRib();

        triangles[0].mark = false;
        triangles[0].iNodes = new int[]{
                pointIndex[1],
                pointIndex[2],
                pointIndex[3]
        };
        triangles[0].triangles = new Triangle[]{
                outsideTriangles[1],
                outsideTriangles[2],
                triangles[1]
        };
        triangles[0].iRibs = new int[]{
                ribs[1],
                ribs[2],
                commonRib
        };

        triangles[1].mark = false;
        triangles[1].iNodes = new int[]{
                pointIndex[3],
                pointIndex[0],
                pointIndex[1]
        };
        triangles[1].triangles = new Triangle[]{
                outsideTriangles[3],
                outsideTriangles[0],
                triangles[0]
        };
        triangles[1].iRibs = new int[]{
                ribs[3],
                ribs[0],
                commonRib
        };
//
//        System.out.println("\n\nOUT ---> ");
//
//
//        for (int j = 0; j < 2; j++) {
//            System.out.println("Triangle :" + triangles[j]);
//            for (int i = 0; i < 3; i++) {
//                System.out.println("Point : ==>" + triangles[j].iNodes[i] + "==>" + nodes.get(triangles[j].iNodes[i]));
//            }
//            for (int i = 0; i < 3; i++) {
//                System.out.println("Rib : ==>" + triangles[j].iRibs[i]);
//            }
//            System.out.println(triangles[j]);
//        }

//        for (int i = 0; i < 2; i++) {
//            if (isCounterClockwise(triangles[i])) {
//                triangles[i].changeClockwise();
//            }
//        }
        beginTriangle.triangles[indexTriangle] = null;
        beginTriangle = null;
        addInverseLinkOnTriangle(triangles);

//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 3; j++) {
////                System.out.println("next triangle");
//                Triangle tri = triangles[i].triangles[j];
//                int rib = triangles[i].iRibs[j];
//                if (tri != null) {
//                    for (int k = 0; k < 3; k++) {
////                        System.out.println("tri.iRibs[k] "+tri.iRibs[k]);
//                        if (tri.iRibs[k] == rib) {
//                            tri.triangles[k] = triangles[i];
////                            System.out.println("Triangle"+tri.triangles[k]+"  rib"+rib);
//                        }
//                    }
//                }
//            }
//        }


        beginTriangle = triangles[0];

        return triangles;
        */
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
                if (PRINTABLE) System.out.println("\nXXXXXXXX\n SAME POINT \n" + nextPoint);
                return;
            }
        }

        if (PRINTABLE)
            System.out.println("-----------------\nadd point - " + nextPoint);


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
            default:
                if (PRINTABLE) System.out.println("FAIL ... " + state);
                System.out.println("Блять .....");
        }
    }

    private Point centerOfPoints(Point[] points) {
        double x = 0.0D, y = 0.0D;
        for (int i = 0; i < points.length; i++) {
            x += points[i].getX();
            y += points[i].getY();
        }
        return new Point(x / points.length, y / points.length);
    }

//    private static Random random = new Random();

    private GeometryPointTriangle.PointTriangleState movingByConvexHull(Point point) {
//boolean PRINTABLE = false;
//        counter.add("movingByConvexHull");

        if (PRINTABLE) {
            System.out.println("===");
            System.out.println("Find point = " + point.toString());
        }

        Point[] trianglePoint = new Point[]{
                nodes.get(beginTriangle.iNodes[0]),
                nodes.get(beginTriangle.iNodes[1]),
                nodes.get(beginTriangle.iNodes[2])
        };

        if (PRINTABLE) {
            System.out.println("\n\n\n\nAnalyze next triangle");
            System.out.println("Triangle points");
            for (int i = 0; i < 3; i++) {
                System.out.println(trianglePoint[i]);
            }
        }

        TriangulationAdvance.GeometryPointTriangle.PointTriangleState
                state = GeometryPointTriangle.statePointInTriangle(point, trianglePoint);

        if (PRINTABLE)
            System.out.println("out of movingByConvexHull :" + state);

//        switch (state) {
//            case POINT_INSIDE:
//            case POINT_ON_LINE_0:
//            case POINT_ON_LINE_1:
//            case POINT_ON_LINE_2:
//                return state;
//            case POINT_OUTSIDE_LINE_0:
//                counter.add("move OUTSIDE_LINE");
//                beginTriangle = beginTriangle.triangles[0];
//                return movingByConvexHull(point);
//            case POINT_OUTSIDE_LINE_1:
//                counter.add("move OUTSIDE_LINE");
//                beginTriangle = beginTriangle.triangles[1];
//                return movingByConvexHull(point);
//            case POINT_OUTSIDE_LINE_2:
//                counter.add("move OUTSIDE_LINE");
//                beginTriangle = beginTriangle.triangles[2];
//                return movingByConvexHull(point);
//        }
        if (state != TriangulationAdvance.GeometryPointTriangle.PointTriangleState.POINT_OUTSIDE) {
            return state;
        } else {
            Point triangleCenter = centerOfPoints(trianglePoint);
            int ribPosition = -1;

//            counter.add("move OUTSIDE");

//            boolean invertDirection = random.nextBoolean();
            for (int i = 0; i < 3; i++) {
                int last = i;
                int next = normalizeSizeBy3(i + 1);//i + 1 < 3) ? i + 1 : i + 1 - 3;
//                int position = i;
//                if (invertDirection) {
//                    int p = 2 - i;
//                    last = p;
//                    next = (p + 1 < 3) ? p + 1 : p + 1 - 3;
//                    position = p;
//                }
                Point ribPoint1 = trianglePoint[last];
                Point ribPoint2 = trianglePoint[next];

                if (PRINTABLE) {
                    System.out.println("Line 1 - " + ribPoint1.toString() + ":" + ribPoint2.toString());
                    System.out.println("Line 2 - " + triangleCenter.toString() + ":" + point.toString());
                }

                GeometryLineLine.IntersectState intersectState = GeometryLineLine.stateLineLine(ribPoint1, ribPoint2, triangleCenter, point);

                if (PRINTABLE) System.out.println(intersectState);

                if (intersectState == GeometryLineLine.IntersectState.INTERSECT ||
                        intersectState == GeometryLineLine.IntersectState.INTERSECT_POINT_ON_LINE) {
                    ribPosition = last;
                    break;
                }
            }

            // todo - some problem with null triangles
//            if (ribPosition == -1) {
//                ribPosition = random.nextInt(3);
//            }

            if (PRINTABLE) {
                System.out.println("triangleCenter = " + triangleCenter);
                System.out.println("ribPosition = " + ribPosition);
                System.out.println("beginTriangle.iRibs[ribPosition] = " + beginTriangle.iRibs[ribPosition]);
                System.out.println("beginTriangle.iRibs[ribPosition] coord = " + nodes.get(beginTriangle.iNodes[ribPosition]));
                System.out.println("beginTriangle.iRibs[ribPosition] coord = " + nodes.get(beginTriangle.iNodes[normalizeSizeBy3(ribPosition + 1)]));
                System.out.println("trianglePoint:" + trianglePoint.toString());
                for (int i = 0; i < 3; i++) {
                    System.out.println(i + ":" + trianglePoint[i]);
                }
                System.out.println(beginTriangle);
                System.out.println(beginTriangle.triangles.length);
                for (int i = 0; i < beginTriangle.triangles.length; i++) {
                    System.out.println(beginTriangle.triangles[i]);
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

    private void addNextPointInTriangle(Point nextPoint) {


        if (PRINTABLE) {
            System.out.println("addNextPointInTriangle");
        }

        if (PRINTABLE) {
            System.out.println("input point = " + nextPoint);
            System.out.println("Points of triangle");
            for (int i = 0; i < 3; i++) {
                System.out.println(nodes.get(beginTriangle.iNodes[i]));
            }
        }


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


//        for (int i = 0; i < 3; i++) {
//            if (isCounterClockwise(triangles[i])) {
//                triangles[i].changeClockwise();
//            }
//        }

//        beginTriangle = null;

        if (PRINTABLE) {
            System.out.println("beginTriangle");
            for (int i = 0; i < 3; i++) {
                System.out.println(nodes.get(beginTriangle.iNodes[i]));
            }
            for (int i = 0; i < 3; i++) {
                System.out.println(beginTriangle.triangles[i]);
            }
        }


        beginTriangle = triangles[0];
        addInverseLinkOnTriangle(triangles);


        if (PRINTABLE) {
            System.out.println("After addNextPointInTriangle triangles");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.println(nodes.get(triangles[i].iNodes[j]));
                }
                for (int j = 0; j < 3; j++) {
                    System.out.println(triangles[i].triangles[j]);
                }
                System.out.println("+");
            }
        }


        /// TODO FUCK THIS SHIT
        for (int i = 0; i < 3; i++) {
            if (!isGoodDelaunay(triangles[i], 0)) {
                flipTriangles(triangles[i], 0);
            }
        }
    }

    private void addInverseLinkOnTriangle(Triangle[] triangles) {
        for (int i = 0; i < triangles.length; i++) {
            if (isCounterClockwise(triangles[i])) {
                triangles[i].changeClockwise();
            }
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


        if (PRINTABLE)
            System.out.println("addNextPointOnLine");

        if (PRINTABLE) {
            System.out.println("input point = " + nextPoint);
            System.out.println("Points of triangle");
            for (int i = 0; i < 3; i++) {
                System.out.println(nodes.get(beginTriangle.iNodes[i]));
            }
            System.out.println("Points of second triangle");
            for (int i = 0; i < 3; i++) {
                System.out.println(nodes.get(beginTriangle.triangles[indexLineInTriangle].iNodes[i]));
            }
        }
//
//        if (isCounterClockwise(beginTriangle)) {
//            beginTriangle.changeClockwise();
//        }
//        if (isCounterClockwise(beginTriangle.triangles[indexLineInTriangle])) {
//            beginTriangle.triangles[indexLineInTriangle].changeClockwise();
//        }

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

//        System.out.println("1");

        if (beginTriangle.triangles[indexLineInTriangle] == null) {
//            beginTriangle = null;
//            for (int i = 0; i < 2; i++) {
//                if (isCounterClockwise(triangles[i])) {
//                    triangles[i].changeClockwise();
//                }
//            }
            addInverseLinkOnTriangle(new Triangle[]{triangles[0], triangles[1]});
            beginTriangle = triangles[0];
            return;
        }

        int ribConnectId = beginTriangle.iRibs[indexLineInTriangle];
        Triangle nextTriangle = beginTriangle.triangles[indexLineInTriangle];
//        beginTriangle = null;
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

//        System.out.println("2");


//        for (int i = 0; i < 4; i++) {
//            if (isCounterClockwise(triangles[i])) {
//                triangles[i].changeClockwise();
//            }
//        }


        if (PRINTABLE) {
            System.out.println("beginTriangle");
            for (int i = 0; i < 3; i++) {
                System.out.println(nodes.get(beginTriangle.iNodes[i]));
            }
            for (int i = 0; i < 3; i++) {
                System.out.println(beginTriangle.triangles[i]);
            }
        }

        addInverseLinkOnTriangle(triangles);

//        beginTriangle = null;
        beginTriangle = triangles[0];

        if (PRINTABLE) {
            System.out.println("\n\n\nAfter addNextPointOnLine");
            for (int i = 0; i < triangles.length; i++) {
                System.out.println("New triangle " + i);
                for (int j = 0; j < 3; j++) {
                    System.out.println(nodes.get(triangles[i].iNodes[j]));
                }
            }
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
    /*
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
*/

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

//        /**
//         * Checking input point - is inside Box?
//         *
//         * @param point input data
//         * @return true - if point inside or on border of the box, and false - if point outside the box or you don`t add any points.
//         * @see Point
//         */
//        public boolean isInBox(Point point) {
//            if (x_min <= point.getX() && point.getX() <= x_max)
//                if (y_min <= point.getY() && point.getY() <= y_max)
//                    return true;
//            return false;
//        }

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
            POINT_OUTSIDE/*,
            POINT_OUTSIDE_LINE_0,
            POINT_OUTSIDE_LINE_1,
            POINT_OUTSIDE_LINE_2,*/
        }

        static GeometryPointTriangle.PointTriangleState statePointInTriangle(Point p, Point[] tri) {

//            counter.add("PointInTriangle");


//            if (!GeometryCoordinate.isPointInRectangle(p, tri))
//                return PointTriangleState.POINT_OUTSIDE;


            // TODO: 19.08.2016 high-precition
//            Point vector1 = new Point(tri[1].getX() - tri[0].getX(), tri[1].getY() - tri[0].getY());
//            Point vector2 = new Point(tri[2].getX() - tri[0].getX(), tri[2].getY() - tri[0].getY());
//            double det = vector1.getX() * vector2.getY() - vector2.getX() * vector1.getY();
//            Point tmp = new Point(p.getX() - tri[0].getX(), p.getY() - tri[0].getY());
//            double lambda = (tmp.getX() * vector2.getY() - vector2.getX() * tmp.getY()) / det;
//            double mue = (vector1.getX() * tmp.getY() - tmp.getX() * vector1.getY()) / det;
//            if (lambda > 0 && mue > 0 && (lambda + mue) < 1) {
//                return PointTriangleState.POINT_INSIDE;
//            }

/*

            if (GeometryPointLine.statePointOnLine(p, tri[0], tri[1]) == GeometryPointLine.PointLineState.POINT_ON_LINE) {
                return GeometryPointTriangle.PointTriangleState.POINT_ON_LINE_0;
            }
            if (GeometryPointLine.statePointOnLine(p, tri[1], tri[2]) == GeometryPointLine.PointLineState.POINT_ON_LINE) {
                return GeometryPointTriangle.PointTriangleState.POINT_ON_LINE_1;
            }
            if (GeometryPointLine.statePointOnLine(p, tri[2], tri[0]) == GeometryPointLine.PointLineState.POINT_ON_LINE) {
                return GeometryPointTriangle.PointTriangleState.POINT_ON_LINE_2;
            }

            for (triangulation.elements.Point aTri : tri) {
                if (p.equals(aTri))
                    return GeometryPointTriangle.PointTriangleState.POINT_ON_CORNER;
            }

            int[] sign = new int[3];
            for (int i = 0; i < 3; i++) {
                BigDecimal result = Geometry.pointOnLine(tri[i], tri[normalizeSizeBy3(i + 1)], p);
                sign[i] = result.compareTo(BigDecimal.valueOf(Precisions.epsilon()));
                if (PRINTABLE) System.out.println("SIGN \"" + sign[i] + "\" :" + result.toString());
            }

            if (sign[0] == sign[1] && sign[1] == sign[2] && sign[0] != 0) {
                return PointTriangleState.POINT_INSIDE;
            }


            return GeometryPointTriangle.PointTriangleState.POINT_OUTSIDE;
*/


            for (triangulation.elements.Point aTri : tri) {
                if (p.equals(aTri))
                    return PointTriangleState.POINT_ON_CORNER;
            }

//            for (int i = 0; i < tri.length; i++) {
//                for (int j = i + 1; j < tri.length; j++) {
//                    if (tri[i].equals(tri[j]))
//                        return PointTriangleState.POINT_ON_CORNER;
//                }
//            }

            BigDecimal line1 = Geometry.pointOnLine(tri[0], p, tri[1]);
            if (GeometryCoordinate.isPointInRectangle(p, tri[0], tri[1])) {
                if (Geometry.is3pointsCollinear(line1))
                    return PointTriangleState.POINT_ON_LINE_0;
            }
//            if (!Geometry.isCounterClockwise(line1)) {
//                return PointTriangleState.POINT_OUTSIDE_LINE_0;
//            }

            BigDecimal line2 = Geometry.pointOnLine(tri[1], p, tri[2]);
            if (GeometryCoordinate.isPointInRectangle(p, tri[1], tri[2])) {
                if (Geometry.is3pointsCollinear(line2))
                    return PointTriangleState.POINT_ON_LINE_1;
            }
//            if (!Geometry.isCounterClockwise(line2)) {
//                return PointTriangleState.POINT_OUTSIDE_LINE_1;
//            }

            BigDecimal line3 = Geometry.pointOnLine(tri[2], p, tri[0]);
            if (GeometryCoordinate.isPointInRectangle(p, tri[2], tri[0])) {
                if (Geometry.is3pointsCollinear(line3))
                    return PointTriangleState.POINT_ON_LINE_2;
            }
//            if (!Geometry.isCounterClockwise(line3)) {
//                return PointTriangleState.POINT_OUTSIDE_LINE_2;
//            }

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

//        public static double angleBetween2Line
//                (Point vertex,
//                 Point point1,
//                 Point point2) {
//            return angleBetween2Line
//                    (vertex.getX(), vertex.getY(),
//                            point1.getX(), point1.getY(),
//                            point2.getX(), point2.getY());
//        }

//        static boolean is3pointsCollinear
//                (Point pLine1,
//                 Point pLine2,
//                 Point pLine3) {
//            return is3pointsCollinear(pLine1.getX(), pLine1.getY(),
//                    pLine2.getX(), pLine2.getY(),
//                    pLine3.getX(), pLine3.getY());
//        }

//        static boolean isPointInRectangle
//                (Point Point,
//                 Point p1Line,
//                 Point p2Line) {
//            return isPointInRectangle(Point.getX(), Point.getY(),
//                    p1Line.getX(), p1Line.getY(),
//                    p2Line.getX(), p2Line.getY());
//        }

//        public static boolean isPointsOnOneLine(List<Point> coordinatePoints) {
//            for (int i = 2; i < coordinatePoints.size(); i++) {
//                if (!is3pointsCollinear(coordinatePoints.get(0), coordinatePoints.get(1), coordinatePoints.get(i)))
//                    return false;
//            }
//            return true;
//        }
//
//        static boolean isPointInRectangle(double x, double y,
//                                          Point p1Line,
//                                          Point p2Line) {
//            return isPointInRectangle(x, y, p1Line.getX(), p1Line.getY(), p2Line.getX(), p2Line.getY());
//        }


        static boolean isPointInRectangle(Point point, Point... list) {

//            counter.add("isPointInRectangle");

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


    private static class GeometryLineLine {

        enum IntersectState {
            INTERSECT_CORNER,
            INTERSECT,
            NOT_INTERSECT,
            LINE_IN_LINE,
            INTERSECT_POINT_ON_LINE
        }

        static GeometryLineLine.IntersectState stateLineLine(Point p1Line1, Point p2Line1,
                                                             Point p1Line2, Point p2Line2) {

//            counter.add("LineLine");

            int[] state = {
                    GeometryPointLine.statePointOnLine(p1Line1, p1Line2, p2Line2).ordinal(),
                    GeometryPointLine.statePointOnLine(p2Line1, p1Line2, p2Line2).ordinal(),
                    GeometryPointLine.statePointOnLine(p1Line2, p1Line1, p2Line1).ordinal(),
                    GeometryPointLine.statePointOnLine(p2Line2, p1Line1, p2Line1).ordinal()};

            int[] statePointOnLine = new int[GeometryPointLine.PointLineState.values().length];
            for (int i = 0; i < statePointOnLine.length; i++) {
                statePointOnLine[i] = 0;
            }

            for (int aState : state) {
                statePointOnLine[aState]++;
            }

            if (statePointOnLine[GeometryPointLine.PointLineState.POINT_OUTSIDE_LINE.ordinal()] == 4) {
                if (isLinesIntersect(p1Line1, p2Line1, p1Line2, p2Line2)) {
                    return GeometryLineLine.IntersectState.INTERSECT;
                }
            } else if (statePointOnLine[GeometryPointLine.PointLineState.POINT_OUTSIDE_LINE.ordinal()] == 3 &&
                    statePointOnLine[GeometryPointLine.PointLineState.POINT_ON_LINE.ordinal()] == 1) {
                return GeometryLineLine.IntersectState.INTERSECT_POINT_ON_LINE;
            } else if (statePointOnLine[GeometryPointLine.PointLineState.POINT_OUTSIDE_LINE.ordinal()] == 2 &&
                    statePointOnLine[GeometryPointLine.PointLineState.POINT_ON_CORNER.ordinal()] == 2) {
                return GeometryLineLine.IntersectState.INTERSECT_CORNER;
            } else if (statePointOnLine[GeometryPointLine.PointLineState.POINT_OUTSIDE_LINE.ordinal()] == 2 &&
                    statePointOnLine[GeometryPointLine.PointLineState.POINT_ON_LINE.ordinal()] == 2) {
                return GeometryLineLine.IntersectState.LINE_IN_LINE;
            } else if (statePointOnLine[GeometryPointLine.PointLineState.POINT_ON_LINE.ordinal()] == 1 &&
                    statePointOnLine[GeometryPointLine.PointLineState.POINT_OUTSIDE_LINE.ordinal()] == 1 &&
                    statePointOnLine[GeometryPointLine.PointLineState.POINT_ON_CORNER.ordinal()] == 2) {
                return GeometryLineLine.IntersectState.LINE_IN_LINE;
            } else if (statePointOnLine[GeometryPointLine.PointLineState.POINT_ON_CORNER.ordinal()] == 4) {
                return GeometryLineLine.IntersectState.LINE_IN_LINE;
            }

            return GeometryLineLine.IntersectState.NOT_INTERSECT;
        }

        private static boolean isLinesIntersect(Point p1Line1, Point p2Line1,
                                                Point p1Line2, Point p2Line2) {
            boolean clockwise1 = triangulationAdvance.TriangulationAdvance.Geometry.isCounterClockwise(p1Line1, p1Line2, p2Line1);
            boolean clockwise2 = triangulationAdvance.TriangulationAdvance.Geometry.isCounterClockwise(p2Line1, p2Line2, p1Line1);
            return clockwise1 == clockwise2;

//            Point intersect = coordinateIntersect(p1, p2, p3, p4);
//            if (intersect == null)
//                return false;
//            return GeometryCoordinate.isPointInRectangle(intersect, new Point[]{p1, p2}) &&
//                    GeometryCoordinate.isPointInRectangle(intersect, new Point[]{p3, p4});
        }


//        private static Point coordinateIntersect(Point p1, Point p2, Point p3, Point p4) {
//            // Line p1-p2
//            // Line p3-p4
////            if (Math.abs((p4.getY() - p3.getY()) * (p2.getX() - p1.getX()) - (p4.getX() - p3.getX()) * (p2.getY() - p1.getY())) < Precisions.epsilon()) {
////                return null;
////            }
////        if (Math.abs((p4.getY() - p2.getY()) * (p2.getX() - p1.getX()) - (p4.getX() - p3.getX()) * (p2.getY() - p1.getY())) < Precisions.epsilon()) {
//////            logger.info("isLinesIntersect - finish #2");
////            return false;
////        }
//            double Ua = ((p4.getX() - p3.getX()) * (p1.getY() - p3.getY()) - (p4.getY() - p3.getY()) * (p1.getX() - p3.getX())) /
//                    ((p4.getY() - p3.getY()) * (p2.getX() - p1.getX()) - (p4.getX() - p3.getX()) * (p2.getY() - p1.getY()));
////        double Ub = ((p2.getX() - p1.getX()) * (p1.getY() - p3.getY()) - (p2.getY() - p1.getY()) * (p1.getX() - p3.getX())) /
////                ((p4.getY() - p2.getY()) * (p2.getX() - p1.getX()) - (p4.getX() - p3.getX()) * (p2.getY() - p1.getY()));
//            double x = p1.getX() + Ua * (p2.getX() - p1.getX());
//            double y = p1.getY() + Ua * (p2.getY() - p1.getY());
//
//            return new Point(x, y);
//        }
    }

    private static class GeometryPointLine {

        enum PointLineState {
            POINT_ON_LINE,
            POINT_OUTSIDE_LINE,
            POINT_ON_CORNER
        }

//        static GeometryPointLine.PointLineState statePointOnLine(double x, double y,
//                                                                 Point p1Line,
//                                                                 Point p2Line) {
//
//            if (p1Line.equals(new Point(x, y)))
//                return GeometryPointLine.PointLineState.POINT_ON_CORNER;
//            if (p2Line.equals(new Point(x, y)))
//                return GeometryPointLine.PointLineState.POINT_ON_CORNER;
//
//            if (!GeometryCoordinate.is3pointsCollinear(x, y,
//                    p1Line.getX(), p1Line.getY(),
//                    p2Line.getX(), p2Line.getY()))
//                return GeometryPointLine.PointLineState.POINT_OUTSIDE_LINE;
//
//            if (!GeometryCoordinate.isPointInRectangle(x, y, p1Line, p2Line))
//                return GeometryPointLine.PointLineState.POINT_OUTSIDE_LINE;
//
//            return GeometryPointLine.PointLineState.POINT_ON_LINE;
//        }

        static GeometryPointLine.PointLineState statePointOnLine(Point point,
                                                                 Point p1Line,
                                                                 Point p2Line) {

//            counter.add("PointOnLine");

            if (p1Line.equals(point))
                return GeometryPointLine.PointLineState.POINT_ON_CORNER;
            if (p2Line.equals(point))
                return GeometryPointLine.PointLineState.POINT_ON_CORNER;

            if (!GeometryCoordinate.is3pointsCollinear(point, p1Line, p2Line))
                return GeometryPointLine.PointLineState.POINT_OUTSIDE_LINE;

            if (!GeometryCoordinate.isPointInRectangle(point, p1Line, p2Line))
                return GeometryPointLine.PointLineState.POINT_OUTSIDE_LINE;

            return GeometryPointLine.PointLineState.POINT_ON_LINE;
        }
    }

//    private static boolean is3pointsCollinear(
//            double x1, double y1,
//            double x2, double y2,
//            double x3, double y3
//    ) {
//        boolean result = false;
//        if (Math.abs(x1 - x2) < Precisions.epsilon() && Math.abs(x3 - x2) < Precisions.epsilon()) {
//            return true;
//        }
//
//        if (Math.abs(y1 - y2) < Precisions.epsilon() && Math.abs(y3 - y2) < Precisions.epsilon()) {
//            return true;
//        }
//
//        double factor = Math.abs((y2 - y1) * (x3 - x2) - (y3 - y2) * (x2 - x1));
//        if (factor < Precisions.epsilon())
//            result = true;
//        return result;
//    }


    private static class Geometry {

//        public static double normalizeAngle(final double angle) {
//            double result = angle;
//            if (angle >= 2d * Math.PI) result = normalizeAngle(angle - 2d * Math.PI);
//            if (angle < 0d) result = normalizeAngle(angle + 2d * Math.PI);
//            return result;
//        }

//        public static double angleBetween2Line(
//                double vertexX, double vertexY,
//                double x1, double y1,
//                double x2, double y2) {
//            double dx1 = x1 - vertexX;
//            double dx2 = x2 - vertexX;
//            double dy1 = y1 - vertexY;
//            double dy2 = y2 - vertexY;
//            double d1 = Math.sqrt(dx1 * dx1 + dy1 * dy1);
//            double d2 = Math.sqrt(dx2 * dx2 + dy2 * dy2);
//            double angle = Math.acos((dx1 * dx2 + dy1 * dy2) / (d1 * d2));
////        logger.info("angleBetween2Line = "+ Math.toDegrees(angle));
//            return normalizeAngle(angle);
//        }


//        public static double angle(
//                double x1, double y1,
//                double x2, double y2) {
//            double angle;
//            if (Math.abs(x2 - x1) < Precisions.epsilon()) {
//                if ((y2 - y1) > 0) angle = Math.PI / 2d;
//                else angle = Math.PI * 1.5d;
//                return normalizeAngle(angle);
//            } else if (Math.abs(y2 - y1) < Precisions.epsilon()) {
//                if ((x2 - x1) > 0) angle = 0d;
//                else angle = Math.PI;
//                return normalizeAngle(angle);
//            }
//
//            int kvadrant;
//            if (x2 > x1) {
//                if (y2 > y1) kvadrant = 1;
//                else kvadrant = 4;
//            } else {
//                if (y2 > y1) kvadrant = 2;
//                else kvadrant = 3;
//            }
//            angle = Math.atan(Math.abs((y2 - y1) / (x2 - x1)));
//            if (kvadrant == 1) ;
//            else if (kvadrant == 2) angle = Math.PI - angle;
//            else if (kvadrant == 3) angle = Math.PI + angle;
//            else if (kvadrant == 4) angle = 2d * Math.PI - angle;
//
//            return normalizeAngle(angle);
//        }

//        public static double lengthBetweenPoints(
//                double x1, double y1,
//                double x2, double y2
//        ) {
//            return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
//        }
//
//        static boolean isPointBetween2Border(double p, double pBorder1, double pBorder2) {
//            if (p < Math.min(pBorder1, pBorder2) - Precisions.epsilon())
//                return false;
//            return p <= Math.max(pBorder1, pBorder2) + Precisions.epsilon();
//        }
//
//        static boolean isPointInRectangle(double x, double y,
//                                          double Line1X, double Line1Y,
//                                          double Line2X, double Line2Y) {
//            if (!isPointBetween2Border(x, Line1X, Line2X))
//                return false;
//            return isPointBetween2Border(y, Line1Y, Line2Y);
//        }

        static BigDecimal pointOnLine(Point p1, Point p2, Point p3) {

//            counter.add("BigDecimal");

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

//            System.out.println(result.toString());
            return result;
        }

        static boolean is3pointsCollinear(BigDecimal result) {
            return result.abs().compareTo(Precisions.bigEpsilon()) < 0;
        }

        static boolean is3pointsCollinear(Point p1, Point p2, Point p3) {

//            counter.add("is3pointsCollinear");

            return is3pointsCollinear(pointOnLine(p1, p2, p3));
//            if (Math.abs(x1 - x2) < Precisions.epsilon() && Math.abs(x3 - x2) < Precisions.epsilon()) {
//                return true;
//            }
//
//            if (Math.abs(y1 - y2) < Precisions.epsilon() && Math.abs(y3 - y2) < Precisions.epsilon()) {
//                return true;
//            }

            //double factor = Math.abs((y2 - y1) * (x3 - x2) - (y3 - y2) * (x2 - x1));
//            boolean result = false;
//            if (factor < Precisions.epsilon())
//                result = true;
//            return result;

//            BigDecimal result = pointOnLine(p1, p2, p3).abs();
//
////            if(factor < 1e-6){
////                System.out.println("good");
////            }
//            //if(PRINTABLE)System.out.println("is3pointsCollinear:"+result.toString());
//            if (result.compareTo(BigDecimal.valueOf(Precisions.epsilon())) < 0) {
//                return true;
//            }
//            return false;
        }

//        public static double distancePoints(Point point1, Point point2) {
//            return Math.sqrt(Math.pow(point1.getX() - point2.getX(), 2.0D) + Math.pow(point1.getY() - point2.getY(), 2.0D));
//        }


        static double det(double a[][]) {
            double det = a[0][0] * a[1][1] * a[2][2] + a[1][0] * a[2][1] * a[0][2] + a[0][1] * a[1][2] * a[2][0]
                    - a[0][2] * a[1][1] * a[2][0] - a[0][1] * a[1][0] * a[2][2] - a[1][2] * a[2][1] * a[0][0];
            return det;
        }


        static boolean isPointInCircle(Point[] circlePoints, Point point) {

//            counter.add("isPointInCircle");

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

//            counter.add("isCounterClockwise");

            BigDecimal result = pointOnLine(a, b, c);
            return isCounterClockwise(result);
        }


        static Point[] convexHull(Point[] inputPoints) {

//            counter.add("convexHull");

            if (inputPoints.length < 2)
                return inputPoints;

            Set<Point> uniquePoints = new HashSet<>();
            for (int i = 0; i < inputPoints.length; i++) {
                uniquePoints.add(inputPoints[i]);
            }

//            System.out.println("uniquePoints"+uniquePoints);


            List<Point> arrayUnique = new ArrayList<>(uniquePoints);


//            System.out.println("arrayUnique" + arrayUnique);

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


//            System.out.println("After sort :"+ arrayUnique);


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
