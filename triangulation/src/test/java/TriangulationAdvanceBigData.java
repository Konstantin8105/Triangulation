import org.junit.Assert;
import org.junit.Test;
import research.ResearchTest;
import triangulationAdvance.Point;
import triangulationAdvance.TriangulationAdvance;

public class TriangulationAdvanceBigData {

    @Test
    public void testBigInputData() {
        Point[] points = (Point[]) ResearchTest.getRandomPoints(1_000_000).toArray();
        TriangulationAdvance triangulation = new TriangulationAdvance(points);
        Assert.assertTrue(triangulation.getTriangles().size() > 0);
    }
}
