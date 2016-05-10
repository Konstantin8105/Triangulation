package triangulation.border.elements;

import triangulation.geometry.Geometry;

import java.util.ArrayList;
import java.util.List;

public class SeparateLoopToSegment {
    public static List<Segment> create(List<LineWithPoints> loop) {
        List<Segment> segments = new ArrayList<>();

        Segment temp = new Segment();
        int startSegment = 0;
        for (int i = 1; i < loop.size(); i++) {
            if (!isCollinear(loop.get(i - 1), loop.get(i))) {
                if (temp.isClear()) {
                    for (int j = startSegment; j < i; j++) {
                        temp.getSegment()[0].add(loop.get(j));
                    }
                } else {
                    for (int j = startSegment; j < i; j++) {
                        temp.getSegment()[1].add(loop.get(j));
                    }
                    segments.add(temp);
                    temp = new Segment();
                }
                startSegment = i;
                if (i == loop.size() - 1) {
                    if (temp.isClear()) {
                        temp.getSegment()[0].add(loop.get(i));
                    } else {
                        temp.getSegment()[1].add(loop.get(i));
                    }
                    segments.add(temp);
                    break;
                }
            } else if (i == loop.size() - 1) {
                if (temp.isClear()) {
                    for (int j = startSegment; j <= i; j++) {
                        temp.getSegment()[0].add(loop.get(j));
                    }
                } else {
                    for (int j = startSegment; j <= i; j++) {
                        temp.getSegment()[1].add(loop.get(j));
                    }
                }
                segments.add(temp);
            }
        }

        return segments;
    }

    private static boolean isCollinear(LineWithPoints line1, LineWithPoints line2) {
        return Geometry.is3pointsCollinear(
                line1.getPointA().getX(), line1.getPointA().getY(),
                line1.getPointB().getX(), line1.getPointB().getY(),
                line2.getPointB().getX(), line2.getPointB().getY()
        );
    }
}
