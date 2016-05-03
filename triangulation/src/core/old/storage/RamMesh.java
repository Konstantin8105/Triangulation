package core.old.storage;

import core.elements.Triangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RamMesh extends RamLine{

    List<Triangle> triangles;

    public RamMesh() {
        this.triangles = new ArrayList<>();
    }

    private static Comparator<Triangle> comparatorId = new Comparator<Triangle>() {
        @Override
        public int compare(Triangle o1, Triangle o2) {
            return o1.getId() - o2.getId();
        }
    };

    public List<Integer> getTrianglesID() throws Exception {
        List<Integer> ids = new ArrayList<>(triangles.size());
        for (Triangle triangle : triangles) {
            ids.add(triangle.getId());
        }
        return ids;
    }

    public Triangle getTriangleById(int idTriangle) throws Exception {
        int index = Collections.binarySearch(triangles, new Triangle(idTriangle, 0, -1, -2), comparatorId);
        return triangles.get(index);
    }

    public int[] getIdPointByTriangle(int idTriangle) throws Exception {
        int index = Collections.binarySearch(triangles, new Triangle(idTriangle, 0, -1, -2), comparatorId);
        Triangle triangle = triangles.get(index);
        int[] id = new int[3];
        id[0] = triangle.getIdPoint1();
        id[1] = triangle.getIdPoint2();
        id[2] = triangle.getIdPoint3();
        return id;
    }
//
//    public List<Integer> getIdTrianglesByLine(int idLine) throws Exception {
//        int[] idPoint = getIdPointsByLine(idLine);
//        List<Integer> idTriangle = new ArrayList<>();
//        for (Triangle triangle : triangles) {
//            if ((triangle.getIdPoint1() == idPoint[0] && triangle.getIdPoint2() == idPoint[1])
//                    ||
//                    (triangle.getIdPoint2() == idPoint[0] && triangle.getIdPoint3() == idPoint[1])
//                    ||
//                    (triangle.getIdPoint3() == idPoint[0] && triangle.getIdPoint1() == idPoint[1])
//                    ||
//                    (triangle.getIdPoint1() == idPoint[1] && triangle.getIdPoint2() == idPoint[0])
//                    ||
//                    (triangle.getIdPoint2() == idPoint[1] && triangle.getIdPoint3() == idPoint[0])
//                    ||
//                    (triangle.getIdPoint3() == idPoint[1] && triangle.getIdPoint1() == idPoint[0])
//                    ) {
//                idTriangle.add(triangle.getId());
//            }
//        }
//        return idTriangle;
//    }

//    public List<Integer> getIdTrianglesByPoint(int idPoint) throws Exception {
//        List<Integer> idTriangle = new ArrayList<>();
//        for (Triangle triangle : triangles) {
//            if (triangle.getIdPoint1() == idPoint ||
//                    triangle.getIdPoint2() == idPoint ||
//                    triangle.getIdPoint3() == idPoint) {
//                idTriangle.add(triangle.getId());
//            }
//        }
//        return idTriangle;
//    }

    public void addTriangle(int idPoint1, int idPoint2, int idPoint3) throws Exception {
        triangles.add(new Triangle(idPoint1, idPoint2, idPoint3));
        Collections.sort(triangles, comparatorId);
    }

    public void deleteTriangle(int idTriangle) throws Exception {
        int index = Collections.binarySearch(triangles, new Triangle(idTriangle, 0, -1, -2), comparatorId);
        triangles.remove(index);
    }
//
//    public List<Integer> getIdBorderLines() throws Exception {
//        List<Line> listLines = getLines();
//        List<Integer> idBorderLines = new ArrayList<>();
//        for (int i = 0; i < listLines.size(); i++) {
//            if (getIdTrianglesByLine(listLines.get(i).getId()).size() == 1) {
//                idBorderLines.add(listLines.get(i).getId());
//            }
//        }
//        return idBorderLines;
//    }

    public Integer getIdTriangleByPoint(Integer idPoint1, Integer idPoint2, Integer idPoint3) throws Exception {
        for (Triangle triangle : triangles) {
            if ((triangle.getIdPoint1() == idPoint1 || triangle.getIdPoint1() == idPoint2 || triangle.getIdPoint1() == idPoint3) &&
                    (triangle.getIdPoint2() == idPoint1 || triangle.getIdPoint2() == idPoint2 || triangle.getIdPoint2() == idPoint3) &&
                    (triangle.getIdPoint3() == idPoint1 || triangle.getIdPoint3() == idPoint2 || triangle.getIdPoint3() == idPoint3)) {
                return triangle.getId();
            }
        }
        throw new Exception("Wrong getIdTriangleByPoint = " + idPoint1 + "," + idPoint2 + "," + idPoint3);
    }
}
