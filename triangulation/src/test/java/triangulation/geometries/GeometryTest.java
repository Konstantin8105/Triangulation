package triangulation.geometries;

import org.junit.Assert;
import org.junit.Test;
import triangulation.elements.Point;

import java.math.BigDecimal;

public class GeometryTest {

    @Test
    public void calculateValuePointOnLine() throws Exception {
        double delta = 1.D;
        for (int i = 1; i < 11; i++) {
            Point p1 = new Point(0, 1.D);
            Point p2 = new Point(delta, 0.D);
            Point p3 = new Point(0, 0.D);
            double value = Geometry.PointOnLineCalculator.calculateDouble(p1, p2, p3);
            System.out.println(i + " : " + value + " --> " + delta);
            BigDecimal result = Geometry.PointOnLineCalculator.calculateBigDecimal(p1, p2, p3);
            Assert.assertTrue("Position " + i
                            + " : " + Geometry.calculateValuePointOnLine(value)
                            + " : " + Geometry.calculateValuePointOnLine(result),
                    Geometry.calculateValuePointOnLine(value) ==
                            Geometry.calculateValuePointOnLine(result) &&
                            Geometry.calculateValuePointOnLine(value) == Geometry.POINT_ON_LINE.RESULT_IS_MORE_ZERO);
            delta /= 10D;
        }
    }
}