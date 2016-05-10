package triangulation.border;

import triangulation.elements.Line;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sequence {

    public static  List<? extends Line> loopization(List<? extends Line> inputLines) throws Exception {
        List<? extends Line> loop = createSequence(inputLines);
        if (loop.get(0).getIdPointA() != loop.get(loop.size() - 1).getIdPointB())
            throw new Exception("Not correct loop ={"
                    + loop.get(0).getIdPointA()
                    + " ; "
                    + loop.get(loop.size() - 1).getIdPointB()
                    + " } ");
        return loop;
    }

    public static List<? extends Line> createSequence(List<? extends Line> listLines) {
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
            if (listLines.get(0).getIdPointA() == listLines.get(position).getIdPointA())
                listLines.get(position).swapPoints();
            List<Line> out = new ArrayList<>(listLines.size());
            out.addAll(listLines.subList(position, listLines.size()));
            out.addAll(listLines.subList(0, position));
            listLines.clear();
            return createSequence(out);
        }
        return listLines;
    }
}
