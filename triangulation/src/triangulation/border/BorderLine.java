package triangulation.border;

import triangulation.border.elements.LineWithPoints;
import triangulation.border.elements.Segment;
import triangulation.border.elements.SeparateLoopToSegment;
import triangulation.border.elements.Sequence;
import triangulation.elements.Line;
import triangulation.elements.Mesh;
import triangulation.elements.Point;
import triangulation.geometry.GeometryLineLine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BorderLine {
    Mesh mesh;
    List<LineWithPoints> loop = new ArrayList<>();
    List<Line> addedLine = new ArrayList<>();
    List<Line> removedLine = new ArrayList<>();

    int MINIMAL_AMOUNT_LOOP = 3;

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
        loop = (List<LineWithPoints>) Sequence.loopization(loop);
        addPointsInLoop();

        if (loop.size() < MINIMAL_AMOUNT_LOOP) {
            List<Line> borderSegment = new ArrayList<>(loop);
            List<Integer> indexLinesDelete = new ArrayList<>(loop.size());
            for (int i = 0; i < loop.size(); i++) {
                for (int j = 0; j < loop.size(); j++) {
                    if (i != j) {
                        if (isIntersect(
                                nextPoint,
                                loop.get(i).getPointMiddle(),
                                loop.get(j).getPointA(),
                                loop.get(j).getPointB())
                                ) {
                            indexLinesDelete.add(i);
                            j = loop.size();
                        }
                    }
                }
            }
            for (int i = indexLinesDelete.size() - 1; i >= 0; i--) {
                borderSegment.remove((int) indexLinesDelete.get(i));
            }
            return (List<Line>) Sequence.createSequence(borderSegment);
        }

        List<Segment> segments = SeparateLoopToSegment.create(loop);
        List<Line> borderSegment = createBorderSegment(segments);
        return (List<Line>) Sequence.createSequence(borderSegment);

    }

    private List<Line> createBorderSegment(List<Segment> segments) {
        asd
    }

    private int normalizePositionOfLoop(int position) {
        if (0 <= position && position <= loop.size() - 1)
            return position;
        if (position < 0)
            return normalizePositionOfLoop(loop.size() - position);
        return normalizePositionOfLoop(position - loop.size());
    }

    private boolean isIntersect(Point nextPoint, Point middle, Point pointA, Point pointB) {
        GeometryLineLine.IntersectState state = GeometryLineLine.stateLineLine(
                nextPoint, middle,
                pointA, pointB
        );
        if (state == GeometryLineLine.IntersectState.INTERSECT ||
                state == GeometryLineLine.IntersectState.INTERSECT_POINT_ON_LINE ||
                state == GeometryLineLine.IntersectState.LINE_IN_LINE) {
            return true;
        }
        return false;
    }

/*
    private int getPositionOfLineNearNextPoint(Point nextPoint) {
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
    }*/

    private void addPointsInLoop() {
        for (LineWithPoints line : loop) {
            if (line.isPointNull()) {
                line.setPoints(
                        mesh.getPoints(line.getIdPointA()),
                        mesh.getPoints(line.getIdPointB()));
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
}
