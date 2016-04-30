package core.elements;


import core.id.GeneratorId;

public class Line {
    private int id;

    private int idPointA;
    private int idPointB;

    public Line(int id, int idPointA, int idPointB) throws Exception {
        this.id = id;
        init(idPointA,idPointB);
    }

    public Line(int idPointA, int idPointB) throws Exception {
        this.id = GeneratorId.getID();
        init(idPointA,idPointB);
    }

    private void init(int idPointA, int idPointB) throws Exception {
        if (idPointA == idPointB)
            throw new Exception("Can`t create line with same points");
        if (id < 0)
            throw new Exception("Negative core.id");
        this.idPointA = idPointA;
        this.idPointB = idPointB;
    }

    public int getIdPointA() {
        return idPointA;
    }

    public int getIdPointB() {
        return idPointB;
    }

    public int getId() {
        return id;
    }

    public void swapPoints() {
        int temp = idPointA;
        idPointA = idPointB;
        idPointB = temp;
    }

    @Override
    public String toString() {
        return "Line{" +
                "  core.id=" + id +
                ", idPointA=" + idPointA +
                ", idPointB=" + idPointB +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        if (idPointA != line.idPointA) return false;
        if (idPointB != line.idPointB) return false;
        return id == line.id;

    }

    @Override
    public int hashCode() {
        int result = idPointA;
        result = 31 * result + idPointB;
        result = 31 * result + id;
        return result;
    }
}
