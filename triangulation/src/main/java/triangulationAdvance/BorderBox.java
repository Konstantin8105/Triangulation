package triangulationAdvance;

/**
 * Create rectangle for future check point outside rectangle.
 * Dimension: 2D
 *
 * @author Izyumov Konstantin
 * @since 24/08/2016
 */
public class BorderBox {
    private double x_min;
    private double x_max;
    private double y_min;
    private double y_max;

    public BorderBox() {
        x_min = Double.MAX_VALUE;
        x_max = -Double.MAX_VALUE;
        y_min = Double.MAX_VALUE;
        y_max = -Double.MAX_VALUE;
    }

    /**
     * Box change the size by X or Y and input point will be inside in rectangle or on border of rectangle
     *
     * @param point input data
     * @see Point
     */
    public void addPoint(Point point) {
        x_min = Math.min(x_min, point.getX());
        x_max = Math.max(x_max, point.getX());
        y_min = Math.min(y_min, point.getY());
        y_max = Math.max(y_max, point.getY());
    }

    public double getX_min() {
        return x_min;
    }

    public double getX_max() {
        return x_max;
    }

    public double getY_min() {
        return y_min;
    }

    public double getY_max() {
        return y_max;
    }

    /**
     * Calculate center of rectangle
     *
     * @return point center of rectangle
     * @see Point
     */
    public Point getCenter() {
        return new Point(
                (x_min + x_max) / 2.0,
                (y_min + y_max) / 2.0
        );
    }

    /**
     * Scale rectangle at center point
     *
     * @param center      - center of scaling
     * @param scaleFactor - scale factor
     */
    public void scale(double scaleFactor, Point center) {
        x_min = center.getX() - (center.getX() - x_min) * scaleFactor;
        x_max = center.getX() + (x_max - center.getX()) * scaleFactor;
        y_min = center.getY() - (center.getY() - y_min) * scaleFactor;
        y_max = center.getY() + (y_max - center.getY()) * scaleFactor;
    }

    /**
     * Check input point is inside the rectangle or on border
     *
     * @param point - input point
     * @return false - if input point is outside of rectangle and true for other cases
     */
    public boolean isInBoxWithBorder(Point point) {
        if (x_min - Precision.epsilon() <= point.getX() && point.getX() <= x_max + Precision.epsilon())
            if (y_min - Precision.epsilon() <= point.getY() && point.getY() <= y_max + Precision.epsilon())
                return true;
        return false;
    }
}