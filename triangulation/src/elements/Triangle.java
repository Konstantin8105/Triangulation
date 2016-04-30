package elements;

import com.home.fgd.stack.triangulation.id.GeneratorId;

public class Triangle {

    private int id;

    private int idPoint1;
    private int idPoint2;
    private int idPoint3;

    public Triangle(int id, int idPoint1, int idPoint2, int idPoint3) {
        this.id = id;
        this.idPoint1 = idPoint1;
        this.idPoint2 = idPoint2;
        this.idPoint3 = idPoint3;
    }

    public Triangle(int idPoint1, int idPoint2, int idPoint3) {
        this.id = GeneratorId.getID();
        this.idPoint1 = idPoint1;
        this.idPoint2 = idPoint2;
        this.idPoint3 = idPoint3;
    }

    public int getId() {
        return id;
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


}
