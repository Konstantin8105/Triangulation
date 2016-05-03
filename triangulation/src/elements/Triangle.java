package elements;

public class Triangle {
    private int idPoint1;
    private int idPoint2;
    private int idPoint3;

    public Triangle(int idPoint1, int idPoint2, int idPoint3) {
        this.idPoint1 = idPoint1;
        this.idPoint2 = idPoint2;
        this.idPoint3 = idPoint3;
    }

    public int getIdPoint1() {
        return idPoint1;
    }

    public int getIdPoint2() {
        return idPoint2;
    }

    public int getIdPoint3() {
        return idPoint3;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "idPoint1=" + idPoint1 +
                ", idPoint2=" + idPoint2 +
                ", idPoint3=" + idPoint3 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Triangle triangle = (Triangle) o;

        if (idPoint1 != triangle.idPoint1) return false;
        if (idPoint2 != triangle.idPoint2) return false;
        return idPoint3 == triangle.idPoint3;

    }

    @Override
    public int hashCode() {
        int result = idPoint1;
        result = 31 * result + idPoint2;
        result = 31 * result + idPoint3;
        return result;
    }
}
