package triangulation;


import elements.Collections.IDable;
import elements.Line;
import elements.Point;

import java.util.ArrayList;
import java.util.List;

public class BorderLineConvexRegion {

    private List<Line> loop;
    private List<Line> lineAdd;
    private List<Line> lineDelete;

    public BorderLineConvexRegion() {
        loop = new ArrayList<>();
        lineAdd = new ArrayList<>();
        lineDelete = new ArrayList<>();
    }

    public void addLine(Line line) {
        lineAdd.add(line);
    }

    public void deleteLine(Line line) {
        lineDelete.add(line);
    }

    public List<Line> getBorderLine() throws Exception {
        if (loop.size() == 0) {
            loop.addAll(lineAdd);
            lineAdd.clear();
            if (lineDelete.size() != 0) {
                throw new Exception("Please check the algorithm. lineDelete is not empty. lineDelete = " + lineDelete);
            }
            return loop;
        }
        List<Integer> removeIndex = new ArrayList<>();
        for (int i = 0; i < loop.size(); i++) {
            for (int j = 0; j < lineDelete.size(); j++) {
                if (loop.get(i).equals(lineDelete.get(j))) {
                    removeIndex.add(i);
                    j = lineDelete.size();
                }
            }
        }
        for (int i = removeIndex.size() - 1; i >= 0; i--) {
            loop.remove(removeIndex.get(i));
        }

        MUST BE LOOP

//        List<Integer> idLines = new ArrayList<>(lines.size());
//        for (int i = 0; i < lines.size(); i++) {
//            idLines.add(lines.get(i).getId());
//        }
//        return idLines;
//    }
//
//    // TODO: 29.04.2016
//    private void sortLines(List<Line> listLines) throws Exception {
//        logger.line();
//        logger.info("In function sortLines()");
//        logger.info("listLines = " + listLines);
        if (listLines.size() < 2)
            throw new Exception("Not enough lines");
        List<Line> loop = new ArrayList<>();
        loop.add(listLines.get(0));
        for (int i = 1; i < listLines.size(); i++) {
            int idLastPoint = loop.get(loop.size() - 1).getIdPointA();
            int idNextPoint = loop.get(loop.size() - 1).getIdPointB();
            for (int j = 0; j < listLines.size(); j++) {
                Line line = listLines.get(j);
                if (line.getIdPointA() == idNextPoint && line.getIdPointB() != idLastPoint) {
                    loop.add(new Line(line.getId(), line.getIdPointA(), line.getIdPointB()));
                    j = listLines.size();
                }
                if (line.getIdPointB() == idNextPoint && line.getIdPointA() != idLastPoint) {
                    loop.add(new Line(line.getId(), line.getIdPointB(), line.getIdPointA()));
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
        if (loop.size() == listLines.size()) {
            listLines = loop;
        } else
            throw new Exception("Cannot find loop");
//        logger.info("listLines = " + listLines);
//        logger.info("end of sortLines()");
//        logger.line();
    }

}
