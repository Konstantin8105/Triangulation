package triangulation;

import java.util.*;

public class AdvanceDataMesh<T extends RamMesh> {
    private T data;
    private int sizeMap;

    //todo GridIndex
    private List<Integer>[][] mapLines;
    private List<Integer>[][] mapTriangles;

    private BorderLine borderLine = new BorderLine();

    private double minX;
    private double minY;
    private double dx;
    private double dy;

    public AdvanceDataMesh(T data) throws Exception {
        this.data = data;
        createAdvanceMesh();
    }

    private void createAdvanceMesh() throws Exception {
        calculateSize();
        if (data.getCoordinate().size() < 1)
            throw new Exception("Not enough coordinates");

        List<Coordinate> coordinates = data.getCoordinate();
        double minX = coordinates.get(0).getX();
        double maxX = coordinates.get(0).getX();
        double minY = coordinates.get(0).getY();
        double maxY = coordinates.get(0).getY();
        for (Coordinate coordinate : coordinates) {
            if (minX > coordinate.getX()) minX = coordinate.getX();
            if (minY > coordinate.getY()) minY = coordinate.getY();
            if (maxX < coordinate.getX()) maxX = coordinate.getX();
            if (maxY < coordinate.getY()) maxY = coordinate.getY();
        }
        this.minX = minX - Precisions.epsilon();
        this.minY = minY - Precisions.epsilon();
        this.dx = (maxX - minX + 2d * Precisions.epsilon()) / (sizeMap);
        this.dy = (maxY - minY + 2d * Precisions.epsilon()) / (sizeMap);

        mapLines = new List[sizeMap][sizeMap];
        mapTriangles = new List[sizeMap][sizeMap];
        for (int i = 0; i < sizeMap; i++) {
            for (int j = 0; j < sizeMap; j++) {
                mapLines[i][j] = new ArrayList<>();
                mapTriangles[i][j] = new ArrayList<>();
            }
        }
    }

    private void calculateSize() throws Exception {
        //0.1 * Math.pow(data.getCoordinate().size(), 3d / 8d);
        //0.1d * Math.sqrt(data.getCoordinate().size());
        //Math.sqrt(data.getCoordinate().size());
        double size = 0.1 * Math.pow(data.getCoordinate().size(), 3d / 8d);;
        double minimalAmount = 1d;
        sizeMap = (int) Math.max(minimalAmount, size);
    }

    public T getData() {
        return data;
    }

    public List<Integer> getLinesID(Coordinate coordinate){
        Position position = convertToPosition(coordinate);
        List<Integer> result = mapLines[position.i][position.j];
        return result;
    }

    public List<Integer> getTrianglesID(Coordinate coordinate){
        Position position = convertToPosition(coordinate);
        List<Integer> result = mapTriangles[position.i][position.j];
        return result;
    }

    public void addLine(Integer idPoint1, Integer idPoint2) throws Exception {
        data.addLine(idPoint1, idPoint2);
        Line line = data.getLinesID(data.getIdLineByPoints(idPoint1, idPoint2));
        List<Position> positions = convert(idPoint1, idPoint2);
        for (Position position : positions) {
            mapLines[position.i][position.j].add(line.getId());
        }
        borderLine.addLine(line);
    }

    public void addTriangle(Integer idPoint1, Integer idPoint2, Integer idPoint3) throws Exception {
        data.addTriangle(idPoint1, idPoint2, idPoint3);
        Integer idTriangle = data.getIdTriangleByPoint(idPoint1, idPoint2, idPoint3);
        List<Position> positions = convert(idPoint1, idPoint2, idPoint3);
        for (Position position : positions) {
            mapTriangles[position.i][position.j].add(idTriangle);
        }
    }

    public void deleteTriangle(int idTriangle) throws Exception {
        int[] idPoint = data.getIdPointByTriangle(idTriangle);
        List<Position> positions = convert(idPoint[0], idPoint[1], idPoint[2]);
        for (Position position : positions) {
            List<Integer> ids = mapTriangles[position.i][position.j];
            for (int i = 0; i < ids.size(); i++) {
                if (ids.get(i) == idTriangle) {
                    ids.remove(i);
                    //i--;
                    i = ids.size();
                }
            }
        }
        data.deleteTriangle(idTriangle);
    }

    public void deleteLine(Integer idLine) throws Exception {
        Line line = data.getLinesID(idLine);
        //int[] idPoint = data.getIdPointsByLine(idLine);
        List<Position> positions = convert(line.getIdPointA(),line.getIdPointB());
        for (Position position : positions) {
            List<Integer> ids = mapLines[position.i][position.j];
            for (int i = 0; i < ids.size(); i++) {
                if (ids.get(i) == idLine) {
                    ids.remove(i);
                    i = ids.size();
                }
            }
        }
        borderLine.deleteLine(line);
        data.deleteLine(idLine);
    }

    public List<Integer> getIdBorderLines() throws Exception {
        return borderLine.getBorderLine();
    }

    public List<Integer> getIdTrianglesByLine(Integer idLine) throws Exception {
        int[] idPoint = data.getIdPointsByLine(idLine);
        List<Position> positions = convert(idPoint[0], idPoint[1]);

        Set<Integer> idTriangles = new HashSet<>();
        for (Position position : positions) {
            idTriangles.addAll(mapTriangles[position.i][position.j]);
        }

        List<Integer> idTriangle = new ArrayList<>();

        Iterator<Integer> iterator = idTriangles.iterator();
        while (iterator.hasNext()) {
            Triangle triangle = data.getTriangleById(iterator.next());
            if ((triangle.getIdPoint1() == idPoint[0] && triangle.getIdPoint2() == idPoint[1])
                    ||
                    (triangle.getIdPoint2() == idPoint[0] && triangle.getIdPoint3() == idPoint[1])
                    ||
                    (triangle.getIdPoint3() == idPoint[0] && triangle.getIdPoint1() == idPoint[1])
                    ||
                    (triangle.getIdPoint1() == idPoint[1] && triangle.getIdPoint2() == idPoint[0])
                    ||
                    (triangle.getIdPoint2() == idPoint[1] && triangle.getIdPoint3() == idPoint[0])
                    ||
                    (triangle.getIdPoint3() == idPoint[1] && triangle.getIdPoint1() == idPoint[0])
                    ) {
                idTriangle.add(triangle.getId());
            }
        }
        return idTriangle;
    }


    private class Position {
        public int i, j;

        public Position(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "i=" + i +
                    ", j=" + j +
                    '}';
        }
    }

    private Position convertToPosition(Coordinate coordinate) {
        double x = coordinate.getX() - minX;
        double y = coordinate.getY() - minY;
        Position result = new Position((int)(x / dx), (int) (y / dy));
        return result;
    }

    private List<Position> convert(Integer idPoint1, Integer idPoint2) throws Exception {
        List<Position> out = new ArrayList<>();
        Coordinate p1 = data.getCoordinateByPointId(idPoint1);
        Coordinate p2 = data.getCoordinateByPointId(idPoint2);
        Position position1 = convertToPosition(p1);
        Position position2 = convertToPosition(p2);
        Position min = new Position(min(position1.i, position2.i), min(position1.j, position2.j));
        Position max = new Position(max(position1.i, position2.i), max(position1.j, position2.j));
        for (int i = min.i; i <= max.i; i++) {
            for (int j = min.j; j <= max.j; j++) {
                out.add(new Position(i, j));
            }
        }
        return out;
    }


    private List<Position> convert(Integer idPoint1, Integer idPoint2, Integer idPoint3) throws Exception {
        Coordinate p1 = data.getCoordinateByPointId(idPoint1);
        Coordinate p2 = data.getCoordinateByPointId(idPoint2);
        Coordinate p3 = data.getCoordinateByPointId(idPoint3);
        Position position1 = convertToPosition(p1);
        Position position2 = convertToPosition(p2);
        Position position3 = convertToPosition(p3);
        List<Position> out = new ArrayList<>();
        Position min = new Position(min(position1.i, position2.i, position3.i), min(position1.j, position2.j, position3.j));
        Position max = new Position(max(position1.i, position2.i, position3.i), max(position1.j, position2.j, position3.j));
        for (int i = min.i; i <= max.i; i++) {
            for (int j = min.j; j <= max.j; j++) {
                out.add(new Position(i, j));
            }
        }
        return out;
    }

    private int max(int a, int b) {
        if (a > b) return a;
        return b;
    }

    private int min(int a, int b) {
        if (a > b) return b;
        return a;
    }

    private int max(int a, int b, int c) {
        return max(a, max(b, c));
    }

    private int min(int a, int b, int c) {
        return min(a, min(b, c));
    }

}
