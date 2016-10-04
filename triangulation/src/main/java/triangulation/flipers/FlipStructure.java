package triangulation.flipers;

import triangulation.TriangleStructure;

public class FlipStructure {
    public final TriangleStructure triangle;
    public final int side;

    public FlipStructure(TriangleStructure triangle, int side) {
        this.triangle = triangle;
        this.side = side;
    }
}
