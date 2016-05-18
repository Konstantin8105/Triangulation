import org.junit.Test;
import triangulation.border.borderSegment.elements.LineWithPoints;
import triangulation.border.borderSegment.elements.Segment;
import triangulation.border.borderSegment.elements.SeparateLoopToSegment;
import triangulation.elements.Point;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SeparateLoopToSegmentTest {


    @Test
    public void test1() throws Exception {
        List<LineWithPoints> loop = new ArrayList<>();
        {
            LineWithPoints line = new LineWithPoints(0, 1);
            line.setPoints(new Point(0, 0), new Point(1, 0));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(1, 2);
            line.setPoints(new Point(1, 0), new Point(1, 1));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(2, 3);
            line.setPoints(new Point(1, 1), new Point(1, 2));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(3, 4);
            line.setPoints(new Point(1, 2), new Point(1, 3));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(5, 0);
            line.setPoints(new Point(1, 3), new Point(0, 0));
            loop.add(line);
        }

        List<Segment> function = SeparateLoopToSegment.create(loop);
        List<Segment> result = new ArrayList<>();
        Segment temp = new Segment();
        {
            LineWithPoints line = new LineWithPoints(0, 1);
            line.setPoints(new Point(0, 0), new Point(1, 0));
            temp.getSegment()[0].add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(1, 2);
            line.setPoints(new Point(1, 0), new Point(1, 1));
            temp.getSegment()[1].add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(2, 3);
            line.setPoints(new Point(1, 1), new Point(1, 2));
            temp.getSegment()[1].add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(3, 4);
            line.setPoints(new Point(1, 2), new Point(1, 3));
            temp.getSegment()[1].add(line);
        }
        result.add(temp);
        temp = new Segment();
        {
            LineWithPoints line = new LineWithPoints(5, 0);
            line.setPoints(new Point(1, 3), new Point(0, 0));
            temp.getSegment()[0].add(line);
        }
        result.add(temp);

        assertTrue(function.size() == result.size());
        for (int i = 0; i < function.size(); i++) {
            assertTrue(function.get(i).equals(result.get(i)));
        }
    }


    @Test
    public void test2() throws Exception {
        List<LineWithPoints> loop = new ArrayList<>();
        {
            LineWithPoints line = new LineWithPoints(0, 1);
            line.setPoints(new Point(0, 0), new Point(1, 0));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(1, 2);
            line.setPoints(new Point(1, 0), new Point(1, 1));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(2, 3);
            line.setPoints(new Point(1, 1), new Point(1, 2));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(3, 4);
            line.setPoints(new Point(1, 2), new Point(1, 3));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(5, 6);
            line.setPoints(new Point(1, 3), new Point(0, 3));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(6, 0);
            line.setPoints(new Point(0, 3), new Point(0, 0));
            loop.add(line);
        }

        List<Segment> function = SeparateLoopToSegment.create(loop);
        List<Segment> result = new ArrayList<>();
        Segment temp = new Segment();
        {
            LineWithPoints line = new LineWithPoints(0, 1);
            line.setPoints(new Point(0, 0), new Point(1, 0));
            temp.getSegment()[0].add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(1, 2);
            line.setPoints(new Point(1, 0), new Point(1, 1));
            temp.getSegment()[1].add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(2, 3);
            line.setPoints(new Point(1, 1), new Point(1, 2));
            temp.getSegment()[1].add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(3, 4);
            line.setPoints(new Point(1, 2), new Point(1, 3));
            temp.getSegment()[1].add(line);
        }
        result.add(temp);
        temp = new Segment();
        {
            LineWithPoints line = new LineWithPoints(5, 6);
            line.setPoints(new Point(1, 3), new Point(0, 3));
            temp.getSegment()[0].add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(6, 0);
            line.setPoints(new Point(0, 3), new Point(0, 0));
            temp.getSegment()[1].add(line);
        }
        result.add(temp);

        assertTrue(function.size() == result.size());
        for (int i = 0; i < function.size(); i++) {
            assertTrue(function.get(i).equals(result.get(i)));
        }
    }


    @Test
    public void test3() throws Exception {
        List<LineWithPoints> loop = new ArrayList<>();
        {
            LineWithPoints line = new LineWithPoints(0, 1);
            line.setPoints(new Point(0, 0), new Point(1, 0));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(1, 2);
            line.setPoints(new Point(1, 0), new Point(1, 1));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(2, 3);
            line.setPoints(new Point(1, 1), new Point(1, 2));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(3, 4);
            line.setPoints(new Point(1, 2), new Point(1, 3));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(5, 6);
            line.setPoints(new Point(1, 3), new Point(0, 3));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(6, 7);
            line.setPoints(new Point(0, 3), new Point(0, 2));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(7, 8);
            line.setPoints(new Point(0, 2), new Point(0, 1));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(8, 0);
            line.setPoints(new Point(0, 1), new Point(0, 0));
            loop.add(line);
        }

        List<Segment> function = SeparateLoopToSegment.create(loop);
        List<Segment> result = new ArrayList<>();
        Segment temp = new Segment();
        {
            LineWithPoints line = new LineWithPoints(0, 1);
            line.setPoints(new Point(0, 0), new Point(1, 0));
            temp.getSegment()[0].add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(1, 2);
            line.setPoints(new Point(1, 0), new Point(1, 1));
            temp.getSegment()[1].add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(2, 3);
            line.setPoints(new Point(1, 1), new Point(1, 2));
            temp.getSegment()[1].add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(3, 4);
            line.setPoints(new Point(1, 2), new Point(1, 3));
            temp.getSegment()[1].add(line);
        }
        result.add(temp);
        temp = new Segment();
        {
            LineWithPoints line = new LineWithPoints(5, 6);
            line.setPoints(new Point(1, 3), new Point(0, 3));
            temp.getSegment()[0].add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(6, 7);
            line.setPoints(new Point(0, 3), new Point(0, 2));
            temp.getSegment()[1].add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(7, 8);
            line.setPoints(new Point(0, 2), new Point(0, 1));
            temp.getSegment()[1].add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(8, 0);
            line.setPoints(new Point(0, 1), new Point(0, 0));
            temp.getSegment()[1].add(line);
        }
        result.add(temp);

        assertTrue(function.size() == result.size());
        for (int i = 0; i < function.size(); i++) {
            assertTrue(function.get(i).equals(result.get(i)));
        }
    }


    @Test
    public void test4() throws Exception {
        List<LineWithPoints> loop = new ArrayList<>();
        {
            LineWithPoints line = new LineWithPoints(0, 1);
            line.setPoints(new Point(0, 0), new Point(1, 0));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(1, 2);
            line.setPoints(new Point(1, 0), new Point(1, 1));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(2, 3);
            line.setPoints(new Point(1, 1), new Point(1, 2));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(3, 4);
            line.setPoints(new Point(1, 2), new Point(1, 3));
            loop.add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(5, 6);
            line.setPoints(new Point(1, 3), new Point(10, 10));
            loop.add(line);
        }

        List<Segment> function = SeparateLoopToSegment.create(loop);
        List<Segment> result = new ArrayList<>();
        Segment temp = new Segment();
        {
            LineWithPoints line = new LineWithPoints(0, 1);
            line.setPoints(new Point(0, 0), new Point(1, 0));
            temp.getSegment()[0].add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(1, 2);
            line.setPoints(new Point(1, 0), new Point(1, 1));
            temp.getSegment()[1].add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(2, 3);
            line.setPoints(new Point(1, 1), new Point(1, 2));
            temp.getSegment()[1].add(line);
        }
        {
            LineWithPoints line = new LineWithPoints(3, 4);
            line.setPoints(new Point(1, 2), new Point(1, 3));
            temp.getSegment()[1].add(line);
        }
        result.add(temp);
        temp = new Segment();
        {
            LineWithPoints line = new LineWithPoints(5, 6);
            line.setPoints(new Point(1, 3), new Point(10, 10));
            temp.getSegment()[0].add(line);
        }
        result.add(temp);

        assertTrue(function.size() == result.size());
        for (int i = 0; i < function.size(); i++) {
            assertTrue(function.get(i).equals(result.get(i)));
        }
    }

}
