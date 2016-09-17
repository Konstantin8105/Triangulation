package triangulation.flipers;

import triangulation.TriangleStructure;

public interface Fliper {
    void add(TriangleStructure triangle, int index);
    void run();
}
