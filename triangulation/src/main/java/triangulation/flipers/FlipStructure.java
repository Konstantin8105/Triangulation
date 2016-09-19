package triangulation.flipers;

import triangulation.TriangleStructure;

public class FlipStructure {
    public TriangleStructure triangle;
    public byte side;

    public FlipStructure(TriangleStructure triangle, byte side) {
        this.triangle = triangle;
        this.side = side;
    }
}
