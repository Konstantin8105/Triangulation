package triangulation;

import elements.Line;

import java.util.ArrayList;
import java.util.List;

public class BorderLineConvexRegion {
    public static List<Line> createLoop(List<Line> listLines) throws Exception {
        List<Line> loop = createSequence(listLines);
        if (loop.get(0).getIdPointA() != loop.get(loop.size() - 1).getIdPointB())
            throw new Exception("Not correct loop ={"
                    + loop.get(0).getIdPointA()
                    + " ; "
                    + loop.get(loop.size() - 1).getIdPointB()
                    + " } ");
        return loop;
    }

    public static List<Line> createSequence(List<Line> listLines) {
        List<Line> sequence = new ArrayList<>(listLines.size());
        sequence.add(listLines.get(0));
        int position = 1;
        int idNextPoint = sequence.get(sequence.size() - 1).getIdPointB();
        while(true){
            for (int i = position; i < listLines.size(); i++) {
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
                }
            }
        }

//        listLines.remove(0);
//        while (listLines.size() > 0) {
//            int idLastPoint = sequence.get(0).getIdPointA();
//            int idNextPoint = sequence.get(sequence.size() - 1).getIdPointB();
//            for (int i = 0; i < listLines.size(); i++) {
//                Line line = listLines.get(i);
//                if (idNextPoint == line.getIdPointA()) {
//                    sequence.add(line);
//                    listLines.remove(i);
//                    i--;
//                } else if (idNextPoint == line.getIdPointB()) {
//                    line.swapPoints();
//                    sequence.add(line);
//                    listLines.remove(i);
//                    i--;
//                } else if (line.getIdPointB() == idLastPoint) {
//                    sequence.add(0, line);
//                    listLines.remove(i);
//                    i--;
//                } else if (line.getIdPointA() == idLastPoint) {
//                    line.swapPoints();
//                    sequence.add(0, line);
//                    listLines.remove(i);
//                    i--;
//                }
//            }
//        }
//        return sequence;
    }
}
