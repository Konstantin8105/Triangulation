package triangulation.border;

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
    //    Counter counter = new Counter("BorderLine");
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
//        counter.add("getBorderSegment");
        createBorderLoop();
        loop = (List<LineWithPoints>) Sequence.loopization(loop);
        addPointsInLoop();

//        counter.add("Loop #", loop.size());

        if (loop.size() <= MINIMAL_AMOUNT_LOOP) {
            List<LineWithPoints> segment = (new BorderSegmentWorst()).segmentation(nextPoint, loop);
//            System.err.println("segment1 -->" + segment);
            return (List<Line>) Sequence.createSequence(
                    segment
            );
        }

        List<LineWithPoints> segment = (new BorderSegmentPair()).segmentation(nextPoint, loop);
//        System.out.println(String.format("%.3f",(double)segment.size()/(double)loop.size()) + "="+ segment.size()+ "/" + loop.size());
//        System.err.println("segment2 -->" + segment);
        return (List<Line>) Sequence.createSequence(
                segment
        );
    }

    private void addPointsInLoop() {
//        counter.add("addPointsInLoop");
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

        deleteLinesNotOnBorder(addedLine);

        boolean fullChecking = true;
        if (addedLine.size() == 2) {
            if (addedLine.get(0).getIdPointA() == addedLine.get(1).getIdPointA() && addedLine.get(0).getIdPointB() != addedLine.get(1).getIdPointB()) {
                Line newLine = new Line(
                        addedLine.get(0).getIdPointB(),
                        addedLine.get(1).getIdPointB()
                );
                for (int i = 0; i < loop.size(); i++) {
                    if (newLine.equals(loop.get(i))) {
                        if (mesh.getTrianglesByLine((Line) loop.get(i)).length > 1) {
                            loop.remove(i);
                        } else {
                            LineWithPoints temp = new LineWithPoints(loop.get(i));
                            loop.clear();
                            loop.add(temp);
                        }
                        fullChecking = false;
                        break;
                    }
                }
            }
        } else {
            // TODO: 5/19/16
            System.err.println(addedLine);
            //[Line{idPointA=2, idPointB=1}, Line{idPointA=1, idPointB=0}, Line{idPointA=0, idPointB=2}]
        }

        if (fullChecking)
            deleteLinesNotOnBorder(loop);

        ((ArrayList) loop).ensureCapacity(loop.size() + addedLine.size());
        for (int i = 0; i < addedLine.size(); i++) {
            loop.add(new LineWithPoints(addedLine.get(i)));
        }
        addedLine.clear();

        if (loop.size() == 0)
            throw new Exception("Border don`t found any lines");
    }

    private void deleteLinesNotOnBorder(List<? extends Line> lines) throws Exception {
        Iterator<? extends Line> iterator = lines.iterator();
        while (iterator.hasNext()) {
            if (mesh.getTrianglesByLine((Line) iterator.next()).length > 1) {
                iterator.remove();
            }
        }
    }
//    public Counter getCounter() {
//        return counter;
//    }
}
