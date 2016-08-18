package triangulation.elements;

import java.util.Arrays;
import java.util.List;

public class Triangle {
    private int idPoint1;
    private int idPoint2;
    private int idPoint3;
    private Line[] lines = new Line[3];
//    private int[] pointAntogonists = new int[3];

    public Triangle(
            int idPoint1,
            int idPoint2,
            int idPoint3
    ) throws Exception {
        this.idPoint1 = idPoint1;
        this.idPoint2 = idPoint2;
        this.idPoint3 = idPoint3;
        lines[0] = new Line(this.idPoint1, this.idPoint2);
//        pointAntogonists[0] = this.idPoint3;
        lines[1] = new Line(this.idPoint2, this.idPoint3);
//        pointAntogonists[1] = this.idPoint1;
        lines[2] = new Line(this.idPoint3, this.idPoint1);
//        pointAntogonists[2] = this.idPoint2;
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

    public List<Integer> getPointsId() {
        return Arrays.asList(idPoint1, idPoint2, idPoint3);
    }

    public Line[] getLines() {
        return lines;
    }
//
//    public int[] getPointAntagonists() {
//        return pointAntogonists;
//    }

//    public int getPointAntogonist(Line line) {
//        if((line.getIdPointA() == idPoint1 && line.getIdPointB() == idPoint2)
//                ||(line.getIdPointA() == idPoint2 && line.getIdPointB() == idPoint1)){
//            return idPoint3;
//        }
//        if((line.getIdPointA() == idPoint2 && line.getIdPointB() == idPoint3)
//                ||(line.getIdPointA() == idPoint3 && line.getIdPointB() == idPoint2)){
//            return idPoint1;
//        }
//        return idPoint2;
//    }
}
