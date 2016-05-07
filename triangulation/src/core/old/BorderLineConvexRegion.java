package core.old;

import core.old.AdvanceMesh;
import elements.Line;

import java.util.ArrayList;
import java.util.Collections;
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
        if (listLines.size() == 1)
            return listLines;
        int idBeforeLine = listLines.get(0).getIdPointB();
        int position = 1;
        boolean changes = true;
        while (changes) {
            changes = false;
            for (int i = position; i < listLines.size(); i++) {
                if (idBeforeLine == listLines.get(i).getIdPointA()) {
                    if (position != i)
                        Collections.swap(listLines, position, i);
                    position++;
                    idBeforeLine = listLines.get(position - 1).getIdPointB();
                    changes = true;
                } else if (idBeforeLine == listLines.get(i).getIdPointB()) {
                    listLines.get(i).swapPoints();
                    if (position != i)
                        Collections.swap(listLines, position, i);
                    position++;
                    idBeforeLine = listLines.get(position - 1).getIdPointB();
                    changes = true;
                }
            }
        }
        if (position != listLines.size()) {
            List<Line> out = new ArrayList<>(listLines.size());
            out.addAll(listLines.subList(position, listLines.size()));
            out.addAll(listLines.subList(0, position));
            listLines.clear();
            return createSequence(out);
        }
        return listLines;
    }

    private List<Line> addedLines  = new ArrayList<>();
    private List<Line> borderLine  = new ArrayList<>();

    public List<Line> getBorderLine(AdvanceMesh mesh) throws Exception {
        if(addedLines.size() == 0)
            return borderLine;
        borderLine.addAll(addedLines);
        for (int i = 0; i < borderLine.size(); i++) {
            if (mesh.getTrianglesByLine(borderLine.get(i)).length != 1) {
                borderLine.remove(i);
                i--;
            }
        }
        return borderLine;
    }

    public void addLine(Line line) {
        addedLines.add(line);
    }

}
