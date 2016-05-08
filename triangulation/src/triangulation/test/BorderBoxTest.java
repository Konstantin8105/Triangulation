import org.junit.Assert;
import org.junit.Test;
import triangulation.BorderBox;
import triangulation.elements.Point;

public class BorderBoxTest {

    @Test(expected = NullPointerException.class)
    public void notNullException_addPoint() {
        BorderBox borderBox = new BorderBox();
        borderBox.addPoint(null);
    }

    @Test(expected = NullPointerException.class)
    public void notNullException_isInBox() {
        BorderBox borderBox = new BorderBox();
        borderBox.addPoint(null);
    }

    @Test(expected = NullPointerException.class)
    public void emptyPoints() {
        BorderBox borderBox = new BorderBox();
        borderBox.isInBox(null);
    }

    @Test
    public void test() {
        Point[] points = new Point[]{
                new Point(1.0D, 0.0D),
                new Point(0.0D, 1.0D)
        };
        BorderBox borderBox = new BorderBox();
        for (Point point : points) {
            borderBox.addPoint(point);
        }
        Assert.assertTrue(borderBox.isInBox(new Point(0.5D, 0.5D)));
        Assert.assertTrue(borderBox.isInBox(new Point(1.0D, 1.0D)));
        Assert.assertFalse(borderBox.isInBox(new Point(1.5D, 0.5D)));
        Assert.assertFalse(borderBox.isInBox(new Point(-1.0D, 0.5D)));
    }

}
