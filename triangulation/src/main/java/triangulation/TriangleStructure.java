package triangulation;


// Triangle data structure
public class TriangleStructure {
    // indexes of triangle points
    public int[] iNodes;
    // indexes of near triangles
    public TriangleStructure[] triangles;
    // indexes of triangle ribs
    public int[] iRibs;

    public void changeClockwise() {
        int temp;
        temp = iNodes[0];
        iNodes[0] = iNodes[1];
        iNodes[1] = temp;
        temp = iRibs[1];
        iRibs[1] = iRibs[2];
        iRibs[2] = temp;
        TriangleStructure tri = triangles[1];
        triangles[1] = triangles[2];
        triangles[2] = tri;
    }
}
