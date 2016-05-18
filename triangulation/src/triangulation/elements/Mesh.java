package triangulation.elements;

import triangulation.border.BorderBox;
import triangulation.border.BorderLine;
import triangulation.elements.Collections.IDable;
import triangulation.geometry.GeometryPointTriangle;
import triangulation.grid.Grid;

import java.util.List;
import java.util.Set;

public class Mesh {
    private final IDable<Point> points = new IDable<>();
    private final IDable<Line> lines = new IDable<>();
    private final IDable<Triangle> triangles = new IDable<>();
    Grid lineGrid;
    Grid triangleGrid;
//    private Counter counter = new Counter("Mesh");

    private final BorderLine borderLine = new BorderLine(this);

    public void addPoint(List<Point> points) {
        //counter.add("addPoint");
        this.points.add(points);
        deleteSamePoints();

        BorderBox box = new BorderBox();
        for (Point point : points) {
            box.addPoint(point);
        }
        lineGrid = new Grid(box, points.size());
        triangleGrid = new Grid(box, points.size());
    }

    private void deleteSamePoints() {
        // TODO: 5/7/16
    }

    public int addLine(Line line, boolean mayBeBorder) throws Exception {
        //counter.add("addLine");
        if (mayBeBorder) {
            borderLine.addLine(line);
        }
        int id = lines.add(line);

        lineGrid.add(getBoxLine(line), id);

        return id;
    }

    private BorderBox getBoxLine(Line line) {
        //counter.add("getBoxLine");
        BorderBox box = new BorderBox();
        box.addPoint(points.getById(line.getIdPointA()).value);
        box.addPoint(points.getById(line.getIdPointB()).value);
        return box;
    }

    public int addTriangle(Triangle triangle) {
        //counter.add("addTriangle");
        int id = triangles.add(triangle);

        triangleGrid.add(getBoxTriangle(triangle), id);

        return id;
    }

    private BorderBox getBoxTriangle(Triangle triangle) {
        // TODO: 5/16/16 optimize
        //counter.add("getBoxTriangle");
        BorderBox box = new BorderBox();
        box.addPoint(points.getById(triangle.getIdPoint1()).value);
        box.addPoint(points.getById(triangle.getIdPoint2()).value);
        box.addPoint(points.getById(triangle.getIdPoint3()).value);
        return box;
    }

    public IDable<Point> getPoints() {
        return points;
    }

    public IDable<Line> getLines() {
        return lines;
    }

    public IDable<Triangle> getTriangulate() {
        return triangles;
    }

    public void deleteLine(int idLine) {
        //counter.add("deleteLine");
        borderLine.removeLine(lines.getById(idLine).value);
        lineGrid.remove(getBoxLine(lines.getById(idLine).value), idLine);
        lines.remove(idLine);
    }

    public void deleteTriangle(int idTriangle) {
        //counter.add("deleteTriangle");
        triangleGrid.remove(getBoxTriangle(triangles.getById(idTriangle).value), idTriangle);
        triangles.remove(idTriangle);
    }

    public IDable.Element[] getTrianglesByLine(Line line) throws Exception {
        //counter.add("getTrianglesByLine");
        // TODO: 5/16/16 super optimize

        BorderBox box = getBoxLine(line);
        Point point = new Point(
                (box.getX_min() + box.getX_max()) / 2,
                (box.getY_min() + box.getY_max()) / 2
        );


        List<Integer> idTriangles = triangleGrid.get(point);

        IDable.Element[] tri = new IDable.Element[2];
        int presentPosition = 0;

        for (Integer id : idTriangles) {
            IDable<Triangle>.Element<Triangle> triangle = triangles.getById(id);
            Line[] lines = triangle.value.getLines();
            for (Line singleLine : lines) {
                if (line.equals(singleLine)) {
                    tri[presentPosition++] = triangle;
                    if (presentPosition == 2) {
                        return tri;
                    }
                }
            }
        }
        if (presentPosition == 0)
            throw new Exception("Line without triangle. line = " + line);
        return new IDable.Element[]{tri[0]};
    }

    private Point[] getPointsByTriangle(Triangle triangle) {
        //counter.add("getPointsByTriangle");
        // TODO: 5/16/16 super optimize
        // TODO: 5/16/16  optimize
        Point[] points = new Point[3];
        for (int i = 0; i < triangle.getPointsId().size(); i++) {
            points[i] = this.points.getById(triangle.getPointsId().get(i)).value;
        }
        return points;
    }


    public int sizePoints() {
        return points.size();
    }

    public int sizeLines() {
        return lines.size();
    }

    public int sizeTriangles() {
        return triangles.size();
    }

    public IDable<?>.Element<?> pointInRegion(IDable<Point>.Element<Point> nextPoint) {
        //counter.add("pointInRegion");
        List<Integer> idTriangles = triangleGrid.get(nextPoint.value);
        GeometryPointTriangle.PointTriangleState gpt = null;
        Line line = null;
        for (Integer id : idTriangles) {
            IDable<Triangle>.Element<Triangle> triangle = triangles.getById(id);
            Point[] points = getPointsByTriangle(triangle.value);
            gpt = GeometryPointTriangle.isPointInTriangle(nextPoint.value, points);
            if (gpt == GeometryPointTriangle.PointTriangleState.POINT_INSIDE) {
                return triangle;
            } else if (gpt == GeometryPointTriangle.PointTriangleState.POINT_ON_LINE_0) {
                line = triangle.value.getLines()[0];
                break;
            } else if (gpt == GeometryPointTriangle.PointTriangleState.POINT_ON_LINE_1) {
                line = triangle.value.getLines()[1];
                break;
            } else if (gpt == GeometryPointTriangle.PointTriangleState.POINT_ON_LINE_2) {
                line = triangle.value.getLines()[2];
                break;
            }
        }
        if (
                gpt == GeometryPointTriangle.PointTriangleState.POINT_ON_LINE_0 ||
                        gpt == GeometryPointTriangle.PointTriangleState.POINT_ON_LINE_1 ||
                        gpt == GeometryPointTriangle.PointTriangleState.POINT_ON_LINE_2
                ) {
            List<Integer> idLines = lineGrid.get(nextPoint.value);
            for (Integer idLine : idLines) {
                IDable<Line>.Element<Line> lineById = lines.getById(idLine);
                if (lineById.value.equals(line)) {
                    return lineById;
                }
            }
        }
        return null;
    }

    public Point getPoints(int idPoint) {
        return points.getById(idPoint).value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Mesh :\n");

        for (int i = 0; i < 20; i++) {
            builder.append("-");
        }
        builder.append("\n");

        builder.append("Points :\n");
        for (IDable<Point>.Element<Point> point : points) {
            builder.append("ID").append(point.id).append(" : ").append(point.value);
        }

        for (int i = 0; i < 20; i++) {
            builder.append("-");
        }
        builder.append("\n");

        builder.append("Line :\n");
        for (IDable<Line>.Element<Line> line : lines) {
            builder.append("ID").append(line.id).append(" : ").append(line.value).append("\n");
        }

        for (int i = 0; i < 20; i++) {
            builder.append("-");
        }
        builder.append("\n");

        builder.append("Triangles :\n");
        for (IDable<Triangle>.Element<Triangle> triangle : triangles) {
            builder.append("ID").append(triangle.id).append(" : ").append(triangle.value).append("\n");
        }

        return builder.toString();
    }

    public List<Line> getBorderSegment(Point nextPoint) throws Exception {
        //counter.add("getBorderSegment");
        return borderLine.getBorderSegment(nextPoint);
    }

//    public Counter getCounter() {
//        System.out.println(lineGrid.getCounter());
//        System.out.println(triangleGrid.getCounter());
//        System.out.println(borderLine.getCounter());
//        return counter;
//    }
}
