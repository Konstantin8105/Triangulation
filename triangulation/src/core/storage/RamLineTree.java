package core.storage;

import com.home.fgd.stack.triangulation.elements.Line;
import com.home.fgd.stack.triangulation.id.GeneratorId;

import java.util.*;

public class RamLineTree extends RamPoint{

    TreeMap<Integer,Line> lines;

    public RamLineTree() {
        lines = new TreeMap<>();
    }

    public void addLine(int idPointDbA, int idPointDbB) throws Exception {
        lines.put(GeneratorId.getID(),new Line(idPointDbA, idPointDbB));
    }

    public List<Integer> getLinesID() throws Exception {
        List<Integer> ids = new ArrayList<>(lines.keySet());
        return ids;
    }

    public Line getLinesID(int id) throws Exception {
        return lines.get(id);
    }

    public int[] getIdPointsByLine(int idLine) throws Exception {
        int[] idPoints = new int[2];
        Line line = lines.get(idLine);
        idPoints[0] = line.getIdPointA();
        idPoints[1] = line.getIdPointB();
        return idPoints;
    }

    public List<Integer> getIdLinesByPoint(int idPoint) throws Exception {
        List<Integer> idLines = new ArrayList<>();
        Set<Map.Entry<Integer, Line>> entrySet = lines.entrySet();
        Iterator<Map.Entry<Integer, Line>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer, Line> map = iterator.next();
            if (map.getValue().getIdPointA() == idPoint || map.getValue().getIdPointB() == idPoint)
                idLines.add(map.getKey());
        }
        return idLines;
    }

    public Integer getIdLineByPoints(Integer idPoint1, Integer idPoint2) throws Exception {
        Set<Map.Entry<Integer, Line>> entrySet = lines.entrySet();
        Iterator<Map.Entry<Integer, Line>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer, Line> map = iterator.next();
            if ((map.getValue().getIdPointA() == idPoint1 && map.getValue().getIdPointB() == idPoint2) ||
                    (map.getValue().getIdPointA() == idPoint2 && map.getValue().getIdPointB() == idPoint1))
                return map.getKey();
        }
        return null;
    }

    public void deleteLine(int idLine) throws Exception {
        lines.remove(idLine);
    }
}
