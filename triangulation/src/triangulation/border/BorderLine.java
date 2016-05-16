package triangulation.border;

import triangulation.border.elements.LineWithPoints;
import triangulation.border.elements.Segment;
import triangulation.border.elements.SeparateLoopToSegment;
import triangulation.border.elements.Sequence;
import triangulation.elements.Line;
import triangulation.elements.Mesh;
import triangulation.elements.Point;
import triangulation.geometry.GeometryLineLine;

import java.util.*;

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


        if (loop.size() <= MINIMAL_AMOUNT_LOOP) {
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
        List<Line> borderSegment = createBorderSegment(nextPoint, segments);


        return (List<Line>) Sequence.createSequence(borderSegment);

    }

    private List<Line> createBorderSegment(Point nextPoint, List<Segment> segments) throws Exception {
        List<Line> borderSegment = new ArrayList<>();
        for (int i = 0; i < segments.size(); i++) {
            if (segments.get(i).haveBothSegments()) {
                checkTriangle(nextPoint, segments.get(i));
            } else {
                checkTriangle(nextPoint, segments.get(i), segments.get(normalizePositionOfList(i + 1, segments)));
            }
        }
        for (int i = 0; i < segments.size(); i++) {
            for (int j = 0; j < 2; j++) {
                if (!segments.get(i).getIntersect()[j] && segments.get(i).getSegment()[j].size() > 0) {
                    for (int k = 0; k < segments.get(i).getSegment()[j].size(); k++) {
                        borderSegment.add(segments.get(i).getSegment()[j].get(k));
                    }
                }
            }
        }
        return borderSegment;
    }

    private void checkTriangle(Point nextPoint, Segment segment, Segment fictiveSegment) throws Exception {
        LineWithPoints line[] = new LineWithPoints[3];
        if (segment.getSegment()[0].size() == 1) {
            line[0] = segment.getSegment()[0].get(0);
        } else {
            int size = segment.getSegment()[0].size();
            line[0] = new LineWithPoints(
                    segment.getSegment()[0].get(0).getIdPointA(),
                    segment.getSegment()[0].get(size - 1).getIdPointB()
            );
            line[0].setPoints(
                    segment.getSegment()[0].get(0).getPointA(),
                    segment.getSegment()[0].get(size - 1).getPointB()
            );
        }

        line[1] = fictiveSegment.getSegment()[0].get(0);

        // fictive line
        line[2] = new LineWithPoints(
                line[1].getIdPointB(),
                line[0].getIdPointA()
        );
        line[2].setPoints(
                line[1].getPointB(),
                line[0].getPointA()
        );

        boolean intersect[] = new boolean[]{false, false, false};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i != j) {
                    if (isIntersect(
                            nextPoint,
                            line[i].getPointMiddle(),
                            line[j].getPointA(),
                            line[j].getPointB())
                            ) {
                        intersect[i] = true;
                    }
                }
            }
        }

        segment.getIntersect()[0] = intersect[0];
    }

    private void checkTriangle(Point nextPoint, Segment segment) throws Exception {
        LineWithPoints line[] = new LineWithPoints[3];
        for (int i = 0; i < 2; i++) {
            if (segment.getSegment()[i].size() == 1) {
                line[i] = segment.getSegment()[i].get(0);
            } else {
                int size = segment.getSegment()[i].size();
                line[i] = new LineWithPoints(
                        segment.getSegment()[i].get(0).getIdPointA(),
                        segment.getSegment()[i].get(size - 1).getIdPointB()
                );
                line[i].setPoints(
                        segment.getSegment()[i].get(0).getPointA(),
                        segment.getSegment()[i].get(size - 1).getPointB()
                );
            }
        }

        // fictive line
        line[2] = new LineWithPoints(
                line[1].getIdPointB(),
                line[0].getIdPointA()
        );
        line[2].setPoints(
                line[1].getPointB(),
                line[0].getPointA()
        );

        boolean intersect[] = new boolean[]{false, false, false};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i != j) {
                    if (isIntersect(
                            nextPoint,
                            line[i].getPointMiddle(),
                            line[j].getPointA(),
                            line[j].getPointB())
                            ) {
                        intersect[i] = true;
                    }
                }
            }
        }

        for (int i = 0; i < 2; i++) {
            segment.getIntersect()[i] = intersect[i];
        }
    }

    private int normalizePositionOfList(int position, List<?> list) {
        if (0 <= position && position <= list.size() - 1)
            return position;
        if (position < 0)
            return normalizePositionOfList(list.size() - position, list);
        return normalizePositionOfList(position - list.size(), list);
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
        ((ArrayList)loop).ensureCapacity(loop.size() + addedLine.size());
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
