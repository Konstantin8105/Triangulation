package triangulation;

import triangulation.elements.Point;
import triangulation.elements.Precision;
import triangulation.flipers.FlipStructure;
import triangulation.flipers.Fliper;

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

            if (!isPointInCircle(
                    new Point[]{
                            triangulation.getNode(next.triangle.triangles[next.side].iNodes[0]),
                            triangulation.getNode(next.triangle.triangles[next.side].iNodes[1]),
                            triangulation.getNode(next.triangle.triangles[next.side].iNodes[2])},
                    triangulation.getNode(next.triangle.iNodes[normalizeSizeBy3(next.side - 1)])))
                continue;
            triangulation.flipTriangles(next.triangle, next.side);

            // TODO: 20.09.2016 add to another class
//            Point p0 = triangulation.getNode(next.triangle.iNodes[ArrayIndexCorrection.normalizeSizeBy3(next.side - 1)]);
//            Point p1 = triangulation.getNode(next.triangle.triangles[next.side].iNodes[0]);
//            Point p2 = triangulation.getNode(next.triangle.triangles[next.side].iNodes[1]);
//            Point p3 = triangulation.getNode(next.triangle.triangles[next.side].iNodes[2]);
//
//            double s1 = (p0.getX() - p1.getX()) * (p0.getY() - p3.getY()) - (p0.getX() - p3.getX()) * (p0.getY() - p1.getY());
//            double s2 = (p2.getX() - p3.getX()) * (p2.getX() - p1.getX()) + (p2.getY() - p3.getY()) * (p2.getY() - p1.getY());
//            double s3 = (p0.getX() - p1.getX()) * (p0.getX() - p3.getX()) + (p0.getY() - p1.getY()) * (p0.getY() - p3.getY());
//            double s4 = (p2.getX() - p3.getX()) * (p2.getY() - p1.getY()) - (p2.getX() - p1.getX()) * (p2.getY() - p3.getY());
//            if (s1 * s2 + s3 * s4 >= 0)
//                continue;
//            else
//                triangulation.flipTriangles(next.triangle, next.side);
        }
    }

    static double det(double a[][]) {
        return a[0][0] * a[1][1] * a[2][2] + a[1][0] * a[2][1] * a[0][2] + a[0][1] * a[1][2] * a[2][0]
                - a[0][2] * a[1][1] * a[2][0] - a[0][1] * a[1][0] * a[2][2] - a[1][2] * a[2][1] * a[0][0];
    }

    static boolean isPointInCircle(Point[] circlePoints, Point point) {

        // todo create another algorithm with 15 multiplications
        double x1 = circlePoints[0].getX();
        double y1 = circlePoints[0].getY();

        double x2 = circlePoints[1].getX();
        double y2 = circlePoints[1].getY();

        double x3 = circlePoints[2].getX();
        double y3 = circlePoints[2].getY();

//        double z2 = x2 * x2 + y2 * y2;
//        double z1 = x1 * x1 + y1 * y1;
//        double z3 = x3 * x3 + y3 * y3;
//
//        double a = det(new double[][]{
//                {x1, y1, 1},
//                {x2, y2, 1},
//                {x3, y3, 1}
//        });
//
//        double b = det(new double[][]{
//                {z1, y1, 1},
//                {z2, y2, 1},
//                {z3, y3, 1}
//        });
//
//        double c = det(new double[][]{
//                {z1, x1, 1},
//                {z2, x2, 1},
//                {z3, x3, 1}
//        });
//
//        double d = det(new double[][]{
//                {z1, x1, y1},
//                {z2, x2, y2},
//                {z3, x3, y3}
//        });
//
//        double x0 = point.getX();
//        double y0 = point.getY();
//        double z0 = x0 * x0 + y0 * y0;
//
//        return Math.signum(a) * (a * z0 - b * x0 + c * y0 - d) < Precision.epsilon();

        double x1x = x1 - point.getX();
        double y1y = y1 - point.getY();

        double x2x = x2 - point.getX();
        double y2y = y2 - point.getY();

        double x3x = x3 - point.getX();
        double y3y = y3 - point.getY();

        double result = det(new double[][]{
                {x1x * x1x + y1y * y1y, x1x, y1y},
                {x2x * x2x + y2y * y2y, x2x, y2y},
                {x3x * x3x + y3y * y3y, x3x, y3y},
        });
        return result > Precision.epsilon();
    }

}
