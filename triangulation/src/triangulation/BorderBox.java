package triangulation;

import triangulation.elements.Point;

/**
 * Create rectangle for future check point outside rectangle.
 * Dimension: 2D
 *
 * @author Izyumov Konstantin
 * @since 05/04/2016
 */
public class BorderBox {
    private double x_min;
    private double x_max;
    private double y_min;
    private double y_max;
    boolean haveFirstPoint = false;

    /**
     * Box change the size by X or Y and input point will be inside in rectangle or on border of rectangle
     * @param point input data
     * @see Point
     * @exception if input point is null
     */
    public void addPoint(Point point) throws NullPointerException {
        if (point == null)
            throw new NullPointerException("Point is null in BorderBox");
        if (!haveFirstPoint) {
            x_min = x_max = point.getX();
            y_min = y_max = point.getY();
            haveFirstPoint = true;
        } else {
            x_min = Math.min(x_min, point.getX());
            x_max = Math.max(x_max, point.getX());
            y_min = Math.min(y_min, point.getY());
            y_max = Math.max(y_max, point.getY());
        }
    }

    /**
     * Checking input point - is inside Box?
     * @param point input data
     * @return true - if point inside or on border of the box, and false - if point outside the box or you don`t add any points.
     * @see Point
     * @exception if input point is null or don`t add any point in box
     */
    public boolean isInBox(Point point) throws NullPointerException {
        if (point == null)
            throw new NullPointerException("Point is null in BorderBox");
        if (!haveFirstPoint)
            throw new NullPointerException("You don`t input the point");
        if (haveFirstPoint) {
            if (x_min <= point.getX() && point.getX() <= x_max)
                if (y_min <= point.getY() && point.getY() <= y_max)
                    return true;
        }
        return false;
    }
}
