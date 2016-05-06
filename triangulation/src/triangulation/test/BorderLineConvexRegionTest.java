package triangulation.test;

import elements.Line;
import org.junit.Test;
import triangulation.BorderLineConvexRegion;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BorderLineConvexRegionTest {

    @Test
    public void test1() throws Exception {

        List<Line> lines = new ArrayList<>();
        lines.add(new Line(0, 1));
        lines.add(new Line(3, 2));
        lines.add(new Line(2, 1));
        print("Before",lines);
        List<Line> out = BorderLineConvexRegion.createSequence(lines);
        print("After ",out);
        assertTrue(check(out));
    }

    @Test
    public void test2() throws Exception {

        List<Line> lines = new ArrayList<>();
        lines.add(new Line(0, 1));
        lines.add(new Line(2, 1));
        lines.add(new Line(2, 0));
        print("Before",lines);
        List<Line> out = BorderLineConvexRegion.createSequence(lines);
        print("After ",out);
        assertTrue(check(out));
    }

    @Test
    public void test3() throws Exception {
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(0, 5));
        lines.add(new Line(2, 5));
        lines.add(new Line(2, 0));
        print("Before",lines);
        List<Line> out = BorderLineConvexRegion.createSequence(lines);
        print("After ",out);
        assertTrue(check(out));
    }

    @Test
    public void test4() throws Exception {
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(10, 5));
        lines.add(new Line(2, 5));
        lines.add(new Line(2, 0));
        print("Before",lines);
        List<Line> out = BorderLineConvexRegion.createSequence(lines);
        print("After ",out);
        assertTrue(check(out));
    }

    @Test
    public void test5() throws Exception {
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(10, 5));
        lines.add(new Line(2, 0));
        lines.add(new Line(2, 5));
        print("Before",lines);
        List<Line> out = BorderLineConvexRegion.createSequence(lines);
        print("After ",out);
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
        print("Before",lines);
        List<Line> out = BorderLineConvexRegion.createSequence(lines);
        print("After ",out);
        assertTrue(check(out));
    }

    @Test
    public void test7() throws Exception {
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(174, 175));
        lines.add(new Line(191, 174));
        print("Before",lines);
        List<Line> out = BorderLineConvexRegion.createSequence(lines);
        print("After ",out);
        assertTrue(check(out));
    }

    @Test
    public void test8() throws Exception {
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(174, 175));
        lines.add(new Line(174, 191));
        print("Before",lines);
        List<Line> out = BorderLineConvexRegion.createSequence(lines);
        print("After ",out);
        assertTrue(check(out));
    }

    private void print(String str, List<Line> lines) {
        System.out.println(str);
        for (int i = 0; i < lines.size(); i++) {
            System.out.println(lines.get(i));
        }
    }
    private boolean check(List<Line> listLines){
        for (int i = 1; i < listLines.size(); i++) {
            if(listLines.get(i-1).getIdPointB() != listLines.get(i).getIdPointA())
                return false;
        }
        return true;
    }

}
