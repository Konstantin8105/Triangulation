package core.old;

import core.elements.Coordinate;
import core.old.storage.RamMesh;

import java.util.List;

public class Triangulation {
    private RamMesh data;

    public Triangulation() throws Exception {
        // TODO: 2/14/16 change to RAM by default, other on polimorfism
        this.data = new RamMesh();
    }

    public RamMesh getDB() {
        return data;
    }

    public void add(List<Coordinate> points) throws Exception {
        data.addCoordinate(points);
    }

    public boolean run() throws Exception {
        IterationTriangulator triangulator = new IterationTriangulator(data);
        if (triangulator.run())
            return true;
        return false;
    }

}
