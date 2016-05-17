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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Segment segment1 = (Segment) o;

        //TODO Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(segment, segment1.segment);

    }

    public boolean haveBothSegments() {
        return segment[0].size() > 0 && segment[1].size() > 0;
    }

    public boolean[] getIntersect() {
        return intersect;
    }
}
