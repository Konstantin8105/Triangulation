package storage.ram;


import elements.Line;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RamLine {

    List<Line> lines;

    private static Comparator<Line> comparatorId = new Comparator<Line>() {
        @Override
        public int compare(Line o1, Line o2) {
            return o1.getId() - o2.getId();
        }
    };

    public RamLine() {
        lines = new ArrayList<>();
    }

    public void addLine(int idPointDbA, int idPointDbB) throws Exception {
        lines.add(new Line(idPointDbA, idPointDbB));
        Collections.sort(lines, comparatorId);
    }

//    public List<Integer> getLinesID() throws Exception {
//        List<Integer> ids = new ArrayList<>(lines.size());
//        for (Line line : lines) {
//            ids.add(line.getId());
//        }
//        return ids;
//    }

    public int sizeLines() {
        return lines.size();
    }

    public List<Line> getLines() {
        return lines;
    }

    public Line getLinesID(int id) throws Exception {
        Line search = new Line(id, 0, -1);
        int index = Collections.binarySearch(lines, search, comparatorId);
        return lines.get(index);
    }

    public int[] getIdPointsByLine(int idLine) throws Exception {
        Line line = getLinesID(idLine);
        int[] idPoints = new int[2];
        idPoints[0] = line.getIdPointA();
        idPoints[1] = line.getIdPointB();
        return idPoints;
    }

//    public List<Integer> getIdLinesByPoint(int idPoint) throws Exception {
//        List<Integer> idLines = new ArrayList<>();
//        for (Line line : lines) {
//            if (line.getIdPointA() == idPoint || line.getIdPointB() == idPoint)
//                idLines.add(line.getId());
//        }
//        return idLines;
//    }

    public Integer getIdLineByPoints(Integer idPoint1, Integer idPoint2) throws Exception {
        for (Line line : lines) {
            if ((line.getIdPointA() == idPoint1 && line.getIdPointB() == idPoint2) ||
                    (line.getIdPointA() == idPoint2 && line.getIdPointB() == idPoint1))
                return line.getId();
        }
        throw new Exception("Wrong getIdLineByPoints = " + idPoint1 + "," + idPoint2);

    }

    public void deleteLine(int idLine) throws Exception {
        int index = Collections.binarySearch(lines, new Line(idLine, 0, -1), comparatorId);
        lines.remove(index);
    }
}
