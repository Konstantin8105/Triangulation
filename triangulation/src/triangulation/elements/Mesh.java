package triangulation.elements;

import triangulation.elements.Collections.IDable;
import triangulation.geometry.GeometryPointLine;
import triangulation.geometry.GeometryPointTriangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Mesh {
    private IDable<Point> points = new IDable<>();
    private IDable<Line> lines = new IDable<>();
    private IDable<Triangle> triangles = new IDable<>();

    public void addPoint(List<Point> point) {
        points.add(point);
        deleteSamePoints();
    }

    private void deleteSamePoints() {
        // TODO: 5/7/16
    }

    public int addLine(Line line) {
        return lines.add(line);
    }

    public int addTriangle(Triangle triangle) {
        return triangles.add(triangle);
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
        lines.remove(idLine);
    }

    public void deleteTriangle(int idTriangle) {
        triangles.remove(idTriangle);
    }

    public IDable.Element[] getTrianglesByLine(Line line) throws Exception {
        IDable.Element[] tri = new IDable.Element[2];
        int presentPosition = 0;

        Iterator<IDable<Triangle>.Element<Triangle>> iterator = triangles.iterator();
        while (iterator.hasNext()) {
            IDable<Triangle>.Element<Triangle> triangle = iterator.next();
            Line[] lines = triangle.value.getLines();
            for (int j = 0; j < lines.length; j++) {
                if (line.equals(lines[j])) {
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

    public Point[] getPointsByTriangle(Triangle triangle) {
        Point[] points = new Point[3];
        for (int i = 0; i < triangle.getPointsId().size(); i++) {
            points[i] = this.points.getById(triangle.getPointsId().get(i)).value;
        }
        return points;
    }

    public List<Line> getBorderLine() throws Exception {
        List<Line> borderLines = new ArrayList<>();
        for (IDable<Line>.Element<Line> line : lines) {
            if (getTrianglesByLine(line.value).length == 1) {
                borderLines.add(line.value);
            }
        }
        if (borderLines.size() == 0)
            throw new Exception("Border don`t found any lines");
        return borderLines;
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

    public static List<Line> createLoop(List<Line> listLines) throws Exception {
        List<Line> loop = createSequence(listLines);
        if (loop.get(0).getIdPointA() != loop.get(loop.size() - 1).getIdPointB())
            throw new Exception("Not correct loop ={"
                    + loop.get(0).getIdPointA()
                    + " ; "
                    + loop.get(loop.size() - 1).getIdPointB()
                    + " } ");
        return loop;
    }

    public static List<Line> createSequence(List<Line> listLines) {
        if (listLines.size() == 1)
            return listLines;
        int idBeforeLine = listLines.get(0).getIdPointB();
        int position = 1;
        boolean changes = true;
        while (changes) {
            changes = false;
            for (int i = position; i < listLines.size(); i++) {
                if (idBeforeLine == listLines.get(i).getIdPointA()) {
                    if (position != i)
                        Collections.swap(listLines, position, i);
                    position++;
                    idBeforeLine = listLines.get(position - 1).getIdPointB();
                    changes = true;
                } else if (idBeforeLine == listLines.get(i).getIdPointB()) {
                    listLines.get(i).swapPoints();
                    if (position != i)
                        Collections.swap(listLines, position, i);
                    position++;
                    idBeforeLine = listLines.get(position - 1).getIdPointB();
                    changes = true;
                }
            }
        }
        if (position != listLines.size()) {
            if (listLines.get(0).getIdPointA() == listLines.get(position).getIdPointA())
                listLines.get(position).swapPoints();
            List<Line> out = new ArrayList<>(listLines.size());
            out.addAll(listLines.subList(position, listLines.size()));
            out.addAll(listLines.subList(0, position));
            listLines.clear();
            return createSequence(out);
        }
        return listLines;
    }

    public IDable.Element getPointOnLine(IDable<Point>.Element<Point> nextPoint) {
        if (lines == null)
            return null;
        if (lines.isEmpty())
            return null;

        Iterator<IDable<Line>.Element<Line>> iterator = lines.iterator();
        while (iterator.hasNext()) {
            IDable<Line>.Element<Line> line = iterator.next();
            Point pointA = this.points.getById(((Line) line.value).getIdPointA()).value;
            Point pointB = this.points.getById(((Line) line.value).getIdPointB()).value;
            if (GeometryPointLine.statePointOnLine(((Point) nextPoint.value).getX(), ((Point) nextPoint.value).getY(),
                    pointA, pointB)
                    == GeometryPointLine.PointLineState.POINT_ON_LINE) {
                return line;
            }
        }

        return null;
    }

    public IDable.Element getPointInTriangle(IDable<Point>.Element<Point> nextPoint) {
        if (triangles == null)
            return null;
        if (triangles.isEmpty())
            return null;

        Iterator<IDable<Triangle>.Element<Triangle>> iterator = triangles.iterator();
        while (iterator.hasNext()) {
            IDable<Triangle>.Element<Triangle> triangle = iterator.next();
            Point[] points = getPointsByTriangle((Triangle) triangle.value);
            if (GeometryPointTriangle.isPointInTriangle((Point) nextPoint.value, points) ==
                    GeometryPointTriangle.PointTriangleState.POINT_INSIDE) {
                return triangle;
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
}
