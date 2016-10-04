package triangulation.geometries;

import triangulation.elements.BorderBox;
import triangulation.elements.Point;

class GeometryCoordinate {
    static boolean isPointInRectangle(Point point, Point... list) {
        BorderBox borderBox = new BorderBox();
        for (int i = 0; i < list.length; i++) {
            borderBox.addPoint(list[i]);
            if (i > 2 && borderBox.isInBoxWithBorder(point)) {
                return true;
            }
        }
        return borderBox.isInBoxWithBorder(point);
    }
}
