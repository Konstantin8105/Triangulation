package triangulation.border.borderSegment;

import triangulation.border.borderSegment.elements.LineWithPoints;
import triangulation.border.borderSegment.elements.Segment;
import triangulation.border.borderSegment.elements.SeparateLoopToSegment;
import triangulation.elements.Line;
import triangulation.elements.Point;

import java.util.ArrayList;
import java.util.List;

public class BorderSegmentPair extends BorderSegment {

    @Override
    public List<Line> segmentation(final Point nextPoint,final List<LineWithPoints> loop) throws Exception {
        List<Segment> segments = SeparateLoopToSegment.create(loop);
        return createBorderSegment(nextPoint, segments);
    }

    protected static List<Line> createBorderSegment(Point nextPoint, List<Segment> segments) throws Exception {
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

    private static void checkTriangle(Point nextPoint, Segment segment, Segment fictiveSegment) throws Exception {
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

    private static void checkTriangle(Point nextPoint, Segment segment) throws Exception {
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

    private static int normalizePositionOfList(int position, List<?> list) {
        if (0 <= position && position <= list.size() - 1)
            return position;
        if (position < 0)
            return normalizePositionOfList(list.size() - position, list);
        return normalizePositionOfList(position - list.size(), list);
    }
}
