package triangulation.elements;

import triangulation.elements.Collections.IDable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mesh {
    private IDable<Point> points = new IDable<>();
    protected IDable<Line> lines = new IDable<>();
    protected IDable<Triangle> triangles = new IDable<>();

    public void addPoint(List<Point> point) {
        points.add(point);
    }

    public int addLine(Line line) throws Exception {
        return lines.add(line);
    }

    public int addTriangle(Triangle triangle) throws Exception {
        return triangles.add(triangle);
    }

    public IDable<Point> getPoints() {
        return points;
    }

    public void deleteLine(int id) {
        lines.remove(id);
    }

    public void deleteTriangle(int id) {
        triangles.remove(id);
    }

    public IDable.Element[] getTrianglesByLine(Line line) throws Exception {
        IDable.Element[] tri = new IDable.Element[2];
        int presentPosition = 0;
        for (int i = 0; i < triangles.size(); i++) {
            Line[] lines = triangles.getByIndex(i).getLines();
            for (int j = 0; j < lines.length; j++) {
                if (line.equals(lines[j])) {
                    tri[presentPosition++] = triangles.getElement(i);
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

    public Point getPoints(int idPoint) {
        return points.getById(idPoint).value;
    }

    public List<IDable.Element> getLines(Point point) throws Exception {
        return lines.getListElements();
    }

    public List<triangulation.elements.Collections.IDable.Element> getTriangles(Point point) throws Exception {
        return triangles.getListElements();
    }

    public List<triangulation.elements.Collections.IDable.Element> getTriangles() throws Exception {
        return triangles.getListElements();
    }

    public Point[] getPointsByTriangle(Triangle triangle) {
        Point[] points = new Point[3];
        for (int i = 0; i < triangle.getPointsId().size(); i++) {
            points[i] = getPoints(triangle.getPointsId().get(i));
        }
        return points;
    }

    public List<Line> getBorderLine() throws Exception {
        List<Line> borderLines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (getTrianglesByLine(lines.getElement(i).value).length == 1) {
                borderLines.add(lines.getByIndex(i));
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


}
