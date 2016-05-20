package triangulation.border.borderSegment;

import org.jetbrains.annotations.NotNull;
import triangulation.border.BorderBox;
import triangulation.border.borderSegment.elements.LineWithPoints;
import triangulation.elements.Line;
import triangulation.elements.Point;
import triangulation.geometry.Geometry;

import java.util.ArrayList;
import java.util.List;

public class BorderSegmentPairWithPresort extends BorderSegmentPair {

    @Override
    public List<LineWithPoints> segmentation(final Point nextPoint, final List<LineWithPoints> loop) throws Exception {
        return null;
//        List<LineWithPoints> segment = fastRemoveSegment(nextPoint, loop);
//        return super.segmentation(nextPoint, segment);
    }
/*
    @NotNull
    private List<LineWithPoints> fastRemoveSegment(final Point nextPoint, final List<LineWithPoints> loop) {
        // find point near point on loop
        //Point pointNear = findNearPoint(nextPoint, loop);
        Point pointCenter = centerOfLoop(loop);
        BorderBox bBox = getBorderBox(loop);
        // line perpendicular line (nextPoint, pointCenter) in point pointCenter on BorderBox
        LineWithPoints perpendicularLine = getPerpendicularLine(bBox,nextPoint,pointCenter);


        List<LineWithPoints> segment = new ArrayList<>();


        return null;
    }

    private LineWithPoints getPerpendicularLine(BorderBox bBox, Point pointA, Point pointB) {
        LineWithPoints perLine = null;
        try {
            perLine = new LineWithPoints(-1,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Y = a*X + b
        double [] parameterLine = Geometry.line(pointA,pointB);
        sdfsd

    }

    private BorderBox getBorderBox(List<LineWithPoints> loop) {
        BorderBox bBox = new BorderBox();
        for (LineWithPoints line : loop) {
            bBox.addPoint(line.getPointA());
        }
        return bBox;
    }

    private Point centerOfLoop(List<LineWithPoints> loop) {
        double x = 0;
        double y = 0;
        for (LineWithPoints line : loop) {
            x += line.getPointA().getX();
            y += line.getPointA().getY();
        }
        return new Point(x / loop.size(), y / loop.size());
    }

    private Point findNearPoint(Point nextPoint, List<LineWithPoints> loop) {
        Point pointNear = loop.get(0).getPointA();
        double distance = distance(nextPoint, pointNear);
        for (LineWithPoints line : loop) {
            if (distance(nextPoint, line.getPointB()) < distance) {
                distance = distance(nextPoint, pointNear);
                pointNear = line.getPointB();
            }
        }
        return pointNear;
    }

    private double distance(Point pointA, Point pointB) {
        return Math.sqrt(Math.pow(pointA.getX() - pointB.getX(), 2.0D)
                + Math.pow(pointA.getY() - pointB.getY(), 2.0D));
    }*/
}
