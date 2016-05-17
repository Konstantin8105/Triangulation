package triangulation.border;

import counter.Counter;
import triangulation.border.borderSegment.BorderSegmentPair;
import triangulation.border.borderSegment.BorderSegmentWorst;
import triangulation.border.borderSegment.elements.LineWithPoints;
import triangulation.border.borderSegment.elements.Sequence;
import triangulation.elements.Line;
import triangulation.elements.Mesh;
import triangulation.elements.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BorderLine {
    Counter counter = new Counter("BorderLine");
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
        counter.add("getBorderSegment");
        createBorderLoop();
        loop = (List<LineWithPoints>) Sequence.loopization(loop);
        addPointsInLoop();

        counter.add("Loop #", loop.size());

        if (loop.size() <= MINIMAL_AMOUNT_LOOP) {
            return (List<Line>) Sequence.createSequence(
                    BorderSegmentWorst.segmentation(nextPoint, loop)
            );
        }

        return (List<Line>) Sequence.createSequence(
                BorderSegmentPair.segmentation(nextPoint, loop)
        );
    }

    private void addPointsInLoop() {
        counter.add("addPointsInLoop");
        for (LineWithPoints line : loop) {
            if (line.isPointNull()) {
                line.setPoints(
                        mesh.getPoints(line.getIdPointA()),
                        mesh.getPoints(line.getIdPointB()));
            }
        }
    }

    private void createBorderLoop() throws Exception {
        counter.add("createBorderLoop");
        loop.removeAll(removedLine);
        addedLine.removeAll(removedLine);
        removedLine.clear();
        ((ArrayList) loop).ensureCapacity(loop.size() + addedLine.size());
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

    public Counter getCounter() {
        return counter;
    }
}
