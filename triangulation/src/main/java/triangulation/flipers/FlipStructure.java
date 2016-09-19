package triangulation.flipers;

import triangulation.TriangleStructure;

public class FlipStructure {
    public TriangleStructure triangle;
    public int side;

    public FlipStructure(TriangleStructure triangle, int side) {
        this.triangle = triangle;
        this.side = side;
    }
}
