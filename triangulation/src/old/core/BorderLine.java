package old.core;

import old.core.elements.Line;

import java.util.ArrayList;
import java.util.List;

public class BorderLine {

    List<Line> lines;

    public BorderLine() {
        lines = new ArrayList<>();
    }

    public void addLine(Line line) {
        if(lines.size() < 4){
            lines.add(line);
        }
        sdf
        // TODO: 29.04.2016
    }

    public void deleteLine(Line line) {
        // TODO: 29.04.2016
        sdf
        lines.remove(line);
    }

    public List<Integer> getBorderLine() {
        List<Integer> idLines = new ArrayList<>(lines.size());
        for (int i = 0; i < lines.size(); i++) {
            idLines.add(lines.get(i).getId());
        }
        return idLines;
    }

    // TODO: 29.04.2016
    private void sortLines(List<Line> listLines) throws Exception {
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
