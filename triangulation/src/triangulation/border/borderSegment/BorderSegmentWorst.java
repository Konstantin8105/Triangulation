package triangulation.border.borderSegment;

import triangulation.border.borderSegment.elements.LineWithPoints;
import triangulation.elements.Line;
import triangulation.elements.Point;

import java.util.ArrayList;
import java.util.List;

public class BorderSegmentWorst extends BorderSegment {

    public static List<Line> segmentation(final Point nextPoint,final List<LineWithPoints> loop) {
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
        return borderSegment;
    }
}
