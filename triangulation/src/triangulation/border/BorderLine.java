package triangulation.border;

import triangulation.elements.Line;
import triangulation.elements.Mesh;
import triangulation.elements.Point;
import triangulation.geometry.Geometry;
import triangulation.geometry.GeometryLineLine;

import java.util.*;

public class BorderLine {
    Mesh mesh;
    List<LineWithPoints> loop = new ArrayList<>();
    List<Line> addedLine = new ArrayList<>();
    List<Line> removedLine = new ArrayList<>();

    public BorderLine(Mesh mesh) {
        this.mesh = mesh;
    }

    public void addLine(Line line) {
        addedLine.add(line);
    }

    public void removeLine(Line line) {
        removedLine.add(line);
    }

    public List<Line> getBorderSegment(Point nextPoint) throws Exception {
        createBorderLoop();
        loop = (List<LineWithPoints>) loopization(loop);
        addPointsInLoop();

        int positionNearNextPointInLoopArray = getPositionOfPointNearNextPoint(nextPoint);

        List<Line> borderSegment = new ArrayList<>(loop);

        // от текущей позиции до хоть полного круга
        // от начальной позиции до текущей позиции
        // нет ли пересечений
        // нет перечесечений - запоминаем - идем дальше
        // есть пересечение - останавливаемся

        // то же самое в обратном направлениие

        // сначало реализовать последовательный алгоритм, а после можно и галопирование попоробовать

        // для надежности можно оставить простой надежный алгоритм


        List<Integer> indexLinesDelete = new ArrayList<>(loop.size());
        for (int i = 0; i < loop.size(); i++) {
            for (int j = 0; j < loop.size(); j++) {
                if (i != j) {
                    GeometryLineLine.IntersectState state = GeometryLineLine.stateLineLine(
                            nextPoint,
                            loop.get(i).pointMiddle,
                            loop.get(j).pointA,
                            loop.get(j).pointB
                    );
                    if (state == GeometryLineLine.IntersectState.INTERSECT ||
                            state == GeometryLineLine.IntersectState.INTERSECT_POINT_ON_LINE ||
                            state == GeometryLineLine.IntersectState.LINE_IN_LINE) {
                        indexLinesDelete.add(i);
                        j = loop.size();
                    }
                }
            }
        }

        for (int i = indexLinesDelete.size() - 1; i >= 0; i--) {
            borderSegment.remove((int) indexLinesDelete.get(i));
        }

        return (List<Line>) createSequence(borderSegment);
    }

    private int getPositionOfPointNearNextPoint(Point nextPoint) {
        int position = 0;

        double[] distance = new double[loop.size()];
        for (int i = 0; i < distance.length; i++) {
            distance[i] = Geometry.distancePoints(nextPoint, loop.get(i).pointA);
        }

        double minimalDistance = distance[0];
        for (int i = 1; i < distance.length; i++) {
            if (minimalDistance > distance[i]) {
                minimalDistance = distance[i];
                position = i;
            }
        }
        return position;
    }

    private void addPointsInLoop() {
        for (LineWithPoints line : loop) {
            if (line.isPointNull()) {
                line.pointA = mesh.getPoints(line.getIdPointA());
                line.pointB = mesh.getPoints(line.getIdPointB());
                line.pointMiddle = Point.middlePoint(line.pointA, line.pointB);
            }
        }
    }

    private void createBorderLoop() throws Exception {
        loop.removeAll(removedLine);
        addedLine.removeAll(removedLine);
        removedLine.clear();
        for (int i = 0; i < addedLine.size(); i++) {
            loop.add(new LineWithPoints(addedLine.get(i)));
        }
        addedLine.clear();

        Iterator<LineWithPoints> iterator = loop.iterator();
        while (iterator.hasNext()) {
            if (mesh.getTrianglesByLine(iterator.next()).length > 1) {
                iterator.remove();
            }
        }
        if (loop.size() == 0)
            throw new Exception("Border don`t found any lines");
    }

    private List<? extends Line> loopization(List<? extends Line> inputLines) throws Exception {
        List<? extends Line> loop = createSequence(inputLines);
        if (loop.get(0).getIdPointA() != loop.get(loop.size() - 1).getIdPointB())
            throw new Exception("Not correct loop ={"
                    + loop.get(0).getIdPointA()
                    + " ; "
                    + loop.get(loop.size() - 1).getIdPointB()
                    + " } ");
        return loop;
    }

    public static List<? extends Line> createSequence(List<? extends Line> listLines) {
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

    private class LineWithPoints extends Line {
        public Point pointA;
        public Point pointB;
        public Point pointMiddle;

        public LineWithPoints(int idPointA, int idPointB) throws Exception {
            super(idPointA, idPointB);
        }

        public LineWithPoints(Line line) {
            super(line);
        }

        public boolean isPointNull() {
            return pointA == null || pointB == null || pointMiddle == null;
        }
    }
}
