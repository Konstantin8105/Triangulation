package triangulation.border.borderSegment.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Segment {
    private List<LineWithPoints> segment[] = new ArrayList[2];
    private boolean intersect[] = new boolean[2];

    public Segment() {
        for (int i = 0; i < segment.length; i++) {
            segment[i] = new ArrayList<>();
            intersect[i] = false;
        }
    }

    public boolean isClear() {
        return segment[0].size() == 0 && segment[1].size() == 0;
    }

    public List<LineWithPoints>[] getSegment() {
        return segment;
    }

    public boolean haveBothSegments() {
        return segment[0].size() > 0 && segment[1].size() > 0;
    }

    public boolean[] getIntersect() {
        return intersect;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "segment=" + Arrays.toString(segment) +
                ", intersect=" + Arrays.toString(intersect) +
                '}';
    }
}
