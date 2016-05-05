package triangulation;

import elements.Line;

import java.util.ArrayList;
import java.util.List;

public class BorderLineConvexRegion {
    public static List<Line> createLoop(List<Line> listLines) throws Exception {
        List<Line> loop = new ArrayList<>(listLines.size());
        loop.add(listLines.get(0));
        for (int i = 1; i < listLines.size(); i++) {
            int idLastPoint = loop.get(loop.size() - 1).getIdPointA();
            int idNextPoint = loop.get(loop.size() - 1).getIdPointB();
            for (int j = 0; j < listLines.size(); j++) {
                Line line = listLines.get(j);
                if (line.getIdPointA() == idNextPoint && line.getIdPointB() != idLastPoint) {
                    loop.add(line);
                    j = listLines.size();
                }
                if (line.getIdPointB() == idNextPoint && line.getIdPointA() != idLastPoint) {
                    line.swapPoints();
                    loop.add(line);
                    j = listLines.size();
                }
            }
        }

        if (loop.get(0).getIdPointA() != loop.get(loop.size() - 1).getIdPointB())
            throw new Exception("Not correct loop ={"
                    + loop.get(0).getIdPointA()
                    + " ; "
                    + loop.get(loop.size() - 1).getIdPointB()
                    + " } ");
        if (loop.size() != listLines.size())
            throw new Exception("Cannot find loop");
        return loop;
    }

    public static List<Line> createSequence(List<Line> listLines) {
        List<Line> sequence = new ArrayList<>(listLines.size());
        sequence.add(listLines.get(0));
        listLines.remove(0);
        while (listLines.size() > 0) {
            int idLastPoint = sequence.get(0).getIdPointA();
            int idNextPoint = sequence.get(sequence.size() - 1).getIdPointB();
            for (int i = 0; i < listLines.size(); i++) {
                Line line = listLines.get(i);
                if (idNextPoint == line.getIdPointA()) {
                    sequence.add(line);
                    listLines.remove(i);
                    i--;
                } else if (idNextPoint == line.getIdPointB()) {
                    line.swapPoints();
                    sequence.add(line);
                    listLines.remove(i);
                    i--;
                } else if (line.getIdPointB() == idLastPoint) {
                    sequence.add(0, line);
                    listLines.remove(i);
                    i--;
                } else if (line.getIdPointA() == idLastPoint) {
                    line.swapPoints();
                    sequence.add(0, line);
                    listLines.remove(i);
                    i--;
                }
            }
        }
        return sequence;
    }
}
