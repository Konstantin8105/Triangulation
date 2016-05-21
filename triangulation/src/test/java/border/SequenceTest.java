package border;

import org.junit.Test;
import triangulation.border.borderSegment.elements.Sequence;
import triangulation.elements.Line;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SequenceTest {

    @Test
    public void test1() throws Exception {

        List<Line> lines = new ArrayList<>();
        lines.add(new Line(0, 1));
        lines.add(new Line(3, 2));
        lines.add(new Line(2, 1));
        print("Before", lines);
        List<Line> out = (List<Line>) Sequence.createSequence(lines);
        print("After ", out);
        assertTrue(check(out));
    }

    @Test
    public void test2() throws Exception {

        List<Line> lines = new ArrayList<>();
        lines.add(new Line(0, 1));
        lines.add(new Line(2, 1));
        lines.add(new Line(2, 0));
        print("Before", lines);
        List<Line> out = (List<Line>) Sequence.createSequence(lines);
        print("After ", out);
        assertTrue(check(out));
    }

    @Test
    public void test3() throws Exception {
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(0, 5));
        lines.add(new Line(2, 5));
        lines.add(new Line(2, 0));
        print("Before", lines);
        List<Line> out = (List<Line>) Sequence.createSequence(lines);
        print("After ", out);
        assertTrue(check(out));
    }

    @Test
    public void test4() throws Exception {
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(10, 5));
        lines.add(new Line(2, 5));
        lines.add(new Line(2, 0));
        print("Before", lines);
        List<Line> out = (List<Line>) Sequence.createSequence(lines);
        print("After ", out);
        assertTrue(check(out));
    }

    @Test
    public void test5() throws Exception {
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(10, 5));
        lines.add(new Line(2, 0));
        lines.add(new Line(2, 5));
        print("Before", lines);
        List<Line> out = (List<Line>) Sequence.createSequence(lines);
        print("After ", out);
        assertTrue(check(out));
    }

    @Test
    public void test6() throws Exception {
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(10, 5));
        lines.add(new Line(0, 1));
        lines.add(new Line(0, 10));
        lines.add(new Line(5, 2));
        lines.add(new Line(2, 1));
        print("Before", lines);
        List<Line> out = (List<Line>) Sequence.createSequence(lines);
        print("After ", out);
        assertTrue(check(out));
    }

    @Test
    public void test7() throws Exception {
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(174, 175));
        lines.add(new Line(191, 174));
        print("Before", lines);
        List<Line> out = (List<Line>) Sequence.createSequence(lines);
        print("After ", out);
        assertTrue(check(out));
    }

    @Test
    public void test8() throws Exception {
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(174, 175));
        lines.add(new Line(174, 191));
        print("Before", lines);
        List<Line> out = (List<Line>) Sequence.createSequence(lines);
        print("After ", out);
        assertTrue(check(out));
    }

    @Test
    public void test9() throws Exception {
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(174, 175));
        lines.add(new Line(175, 150));
        lines.add(new Line(174, 191));
        print("Before", lines);
        List<Line> out = (List<Line>) Sequence.createSequence(lines);
        print("After ", out);
        assertTrue(check(out));
    }

    final private boolean PRINTABLE = false;

    private void print(String str, List<Line> lines) {
        if (PRINTABLE) {
            System.out.println(str);
            for (Line line : lines) {
                System.out.println(line);
            }
        }
    }

    private boolean check(List<Line> listLines) {
        for (int i = 1; i < listLines.size(); i++) {
            if (listLines.get(i - 1).getIdPointB() != listLines.get(i).getIdPointA())
                return false;
        }
        return true;
    }

    //Line{idPointA=1, idPointB=0}, Line{idPointA=0, idPointB=2}, Line{idPointA=3, idPointB=1}, Line{idPointA=3, idPointB=2}

    @Test
    public void test10() throws Exception {
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(1, 0));
        lines.add(new Line(0, 2));
        lines.add(new Line(3, 1));
        lines.add(new Line(3, 2));
        print("Before", lines);
        List<Line> out = (List<Line>) Sequence.createSequence(lines);
        print("After ", out);
        assertTrue(check(out));
    }

//IN createSequence inputLines = [Line{idPointA=1, idPointB=0}, Line{idPointA=0, idPointB=2}, Line{idPointA=3, idPointB=1}, Line{idPointA=3, idPointB=2}]
//OUT createSequence inputLines = [Line{idPointA=1, idPointB=0}, Line{idPointA=0, idPointB=2}, Line{idPointA=2, idPointB=3}, Line{idPointA=3, idPointB=1}]

    @Test
    public void test11() throws Exception {
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(1, 0));
        lines.add(new Line(0, 2));
        lines.add(new Line(3, 1));
        lines.add(new Line(3, 2));
        print("Before", lines);
        List<Line> out = (List<Line>) Sequence.createSequence(lines);
        print("After ", out);
        assertTrue(check(out));
    }


}
