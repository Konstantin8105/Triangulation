package triangulation;

import elements.Point;

/**
 * Create rectangle for future check point outside rectangle.
 *
 * @author Izyumov Konstantin
 * @since 05/04/2016
 */
public class BorderBox {
    private double xmin;
    private double xmax;
    private double ymin;
    private double ymax;
    boolean haveFirstPoint = false;

    /**
     * @param point
     * @see Point
     */
    public void addPoint(Point point) {
        if (!haveFirstPoint) {
            xmin = xmax = point.getX();
            ymin = ymax = point.getY();
        } else {
            xmin = Math.min(xmin, point.getX());
            xmax = Math.max(xmax, point.getX());
            ymin = Math.min(ymin, point.getY());
            ymax = Math.max(ymax, point.getY());
            haveFirstPoint = true;
        }
    }

    /**
     * @param point
     * @return true - if point outside of box or you don`t add any points, and false - if point on border or inside box.
     * @see Point
     */
    public boolean isInBox(Point point) {
        if (!haveFirstPoint) {
            return true;
        } else {
            if (xmin < point.getX() && point.getX() < xmax)
                if (ymin < point.getY() && point.getY() < ymax)
                    return true;
        }
        return false;
    }
}
