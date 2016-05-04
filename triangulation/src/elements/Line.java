package elements;

public class Line {
    private int idPointA;
    private int idPointB;

    public Line(int idPointA, int idPointB) throws Exception {
        if (idPointA == idPointB)
            throw new Exception("Can`t create line with same points");
        this.idPointA = idPointA;
        this.idPointB = idPointB;
    }

    public int getIdPointA() {
        return idPointA;
    }

    public int getIdPointB() {
        return idPointB;
    }

    public void swapPoints() {
        int temp = idPointA;
        idPointA = idPointB;
        idPointB = temp;
    }

    @Override
    public String toString() {
        return "Line{" +
                "idPointA=" + idPointA +
                ", idPointB=" + idPointB +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return (idPointA == line.idPointA && idPointB == line.idPointB) ||
                (idPointB == line.idPointA && idPointA == line.idPointB);

    }

    @Override
    public int hashCode() {
        int result = idPointA;
        result = 31 * result + idPointB;
        return result;
    }
}
