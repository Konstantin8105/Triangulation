package triangulation.border;

import triangulation.elements.Collections.IDable;
import triangulation.elements.Line;
import triangulation.elements.Mesh;
import triangulation.elements.Point;
import triangulation.geometry.GeometryLineLine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BorderLine {
    Mesh mesh;

    public BorderLine(Mesh mesh) {
        this.mesh = mesh;
    }

    public List<Line> getBorderSegment(Point point) {

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

    private List<Line> getBorderLinesForNewConvex(Point nextPoint) throws Exception {
        List<Line> borderLine = Mesh.createLoop(mesh.getBorderLine());

        Point[] pointsOfLine = new Point[borderLine.size() + 1];
        pointsOfLine[0] = mesh.getPoints(borderLine.get(0).getIdPointA());
        for (int i = 0; i < borderLine.size(); i++) {
            pointsOfLine[i + 1] = mesh.getPoints(borderLine.get(i).getIdPointB());
        }
        pointsOfLine[borderLine.size()] = pointsOfLine[0];

        Point[] pointsMiddleOfLine = new Point[borderLine.size()];
        for (int i = 0; i < pointsMiddleOfLine.length; i++) {
            pointsMiddleOfLine[i] = Point.middlePoint(pointsOfLine[i], pointsOfLine[i + 1]);
        }



        List<Integer> indexLinesDelete = new ArrayList<>();
        for (int i = 0; i < borderLine.size(); i++) {
            for (int j = 0; j < borderLine.size(); j++) {
                if (i != j) {
                    GeometryLineLine.IntersectState state = GeometryLineLine.stateLineLine(
                            nextPoint,
                            pointsMiddleOfLine[i],
                            pointsOfLine[j],
                            pointsOfLine[j + 1]);
                    if (state == GeometryLineLine.IntersectState.INTERSECT ||
                            state == GeometryLineLine.IntersectState.INTERSECT_POINT_ON_LINE ||
                            state == GeometryLineLine.IntersectState.LINE_IN_LINE) {
                        indexLinesDelete.add(i);
                        j = borderLine.size();
                    }
                }
            }

        }

        for (int i = indexLinesDelete.size() - 1; i >= 0; i--) {
            borderLine.remove((int) indexLinesDelete.get(i));
        }

        return Mesh.createSequence(borderLine);
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
