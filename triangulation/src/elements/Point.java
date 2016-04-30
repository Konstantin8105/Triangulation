package elements;


import id.GeneratorId;

public class Point{
    private int id;
    Coordinate coordinate;

    public Point(int iD, Coordinate coordinate) {
        this.coordinate = new Coordinate(coordinate);
        this.id = iD;
    }

    public Point(Coordinate coordinate) {
        this.coordinate = new Coordinate(coordinate);
        this.id = GeneratorId.getID();
    }

    public int getId() {
        return id;
    }

    public Coordinate getCoordinate(){
        return coordinate;
    }

    @Override
    public String toString() {
        return "Point{" +
                "coordinate=" + coordinate +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        if(id != point.id)
            return false;
        return coordinate.equals(point.coordinate);
    }

    @Override
    public int hashCode() {
        return id;
    }

}
