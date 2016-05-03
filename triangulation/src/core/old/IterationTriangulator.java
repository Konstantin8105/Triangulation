package core.old;

import core.elements.Coordinate;
import core.elements.Line;
import core.elements.Point;
import core.old.geometry.GeometryCoordinate;
import core.old.geometry.GeometryLineLine;
import core.old.geometry.GeometryPointLine;
import core.old.geometry.GeometryPointTriangle;
import core.old.storage.RamMesh;

import java.util.*;

public class IterationTriangulator<T extends RamMesh> {
    protected AdvanceDataMesh data;

//    Log logger = Log.getLogger(ClassNameUtil.getCurrentClassName());

    public IterationTriangulator(T data) throws Exception {
        this.data = new AdvanceDataMesh(data);
    }

    List<Point> idPointAdded = new ArrayList<>();

    public boolean run() throws Exception {
        data.getData().deleteSameCoordinates();
        List<Point> idPointList = data.getData().getPoints();

        for (int i = 0; i < 2; i++) {
            idPointAdded.add(idPointList.get(i));
        }
        for (int i = idPointAdded.size(); i < idPointList.size(); i++) {
            idPointAdded.add(idPointList.get(i));
            addNextPoint(idPointList.get(i));
        }
        return true;
    }

    private void addNextPoint(Point nextPoint) throws Exception {
        Coordinate nextPointCoordinate = nextPoint.getCoordinate();
//        logger.info("addNextPoint --> " + idPoint);

        // TODO:boudingBox

        Integer idLineOnPoint = getLineOnPoint(nextPointCoordinate);
        if (idLineOnPoint != null) {
            addNextPointOnLine(nextPoint, idLineOnPoint);
            return;
        }

        Integer idTriangleOnPoint = getTriangleOnPoint(nextPointCoordinate);
        if (idTriangleOnPoint != null) {
            addNextPointInTriangle(nextPoint.getId(), idTriangleOnPoint);
            return;
        }

        // point is outside of triangles
        addNextPointOutside(nextPoint.getId());
    }


    private Integer getLineOnPoint(Coordinate coordinate) throws Exception {
//        logger.line();
//        logger.info("In function getLineOnPoint()");
//        logger.info("coordinate = " + coordinate);
        // point on line
        List<Integer> listLinesId = data.getLinesID(coordinate);
        if (listLinesId == null) {
//            logger.info("result getLineOnPoint() = null");
//            logger.line();
            return null;
        }

//        logger.info("listLinesId" + listLinesId);
        for (Integer idLine : listLinesId) {
            Line line = data.getData().getLinesID(idLine);
            Point pointA = data.getData().getPointsID(line.getIdPointA());
            Point pointB = data.getData().getPointsID(line.getIdPointB());
            if (GeometryPointLine.statePointOnLine(coordinate.getX(), coordinate.getY(),
                    pointA.getCoordinate(), pointB.getCoordinate())
                    == GeometryPointLine.PointLineState.POINT_ON_LINE) {
//                logger.info("result getLineOnPoint() = " + idLine);//listLinesId.get(i));
//                logger.line();
                return idLine;
            }
        }

//        logger.info("result getLineOnPoint() = null");
//        logger.line();
        return null;
    }


    private Integer getTriangleOnPoint(Coordinate coordinate) throws Exception {
//        logger.line();
//        logger.info("In function getTriangleOnPoint()");
//        logger.info("coordinate = " + coordinate);
        // point in triangle
        List<Integer> listTriangleId = data.getTrianglesID(coordinate);
//        logger.info("listTriangleId " + listTriangleId);
        for (Integer triangleId : listTriangleId) {//(int i = 0; i < listTriangleId.size(); i++) {
            int[] points = data.getData().getIdPointByTriangle(triangleId);//listTriangleId.get(i));
            List<Coordinate> listCoordinate = new ArrayList<>();
            for (int j = 0; j < points.length; j++) {
                listCoordinate.add(data.getData().getCoordinateByPointId(points[j]));
            }
            if (GeometryPointTriangle.isPointInTriangle(coordinate, listCoordinate) ==
                    GeometryPointTriangle.PointTriangleState.POINT_INSIDE) {
//                logger.info("result getTriangleOnPoint() " + listTriangleId.get(i));
//                logger.line();
                return triangleId;//listTriangleId.get(i);
            }
        }
//        logger.info("result getTriangleOnPoint() = null");
//        logger.line();
        return null;
    }

    private void addNextPointOutside(Integer idNextPoint) throws Exception {
//        logger.line();
//        logger.info("In function addNextPointOutside()");
        // get list of border - lines with only one triangle - take border points
        List<Integer> idBorderLines = data.getIdBorderLines();
        // if list of border points are "0"
        if (idBorderLines.size() == 0) {
            addNextPointWithoutBorder(idNextPoint);
            return;
        }

//        logger.info("idBorderLines = " + idBorderLines);
        // create list of all lines, triangles

        /*
        List<Line> borderLines = new ArrayList<>();
        for (int i = 0; i < idBorderLines.size(); i++) {
            //int[] points = data.getData().getIdPointsByLine(idBorderLines.get(i));
            //borderLines.add(new Line(0, points[0], points[1]));
            borderLines.add(data.getData().getLinesID(idBorderLines.get(i)));
        }
        sortLines(borderLines);
        */

        List<Boolean> addList = new ArrayList<>();
        for (int i = 0; i < borderLines.size(); i++) {
            addList.add(false);
        }

        List<GeometryLineLine.IntersectState> inter = new ArrayList<>();

        for (int i = 0; i < borderLines.size(); i++) {
            inter.add(GeometryLineLine.IntersectState.NOT_INTERSECT);

            ArrayList<Line> copyListLines = new ArrayList(borderLines);
            copyListLines.remove(i);
            // check intersect line and border line
            boolean haveIntersect = false;
            for (int j = 0; j < copyListLines.size(); j++) {
                Coordinate A = data.getData().getCoordinateByPointId(borderLines.get(i).getIdPointA());
                Coordinate B = data.getData().getCoordinateByPointId(borderLines.get(i).getIdPointB());
                Coordinate middle = new Coordinate((A.getX() + B.getX()) * 0.5f, (A.getY() + B.getY()) * 0.5f);
                GeometryLineLine.IntersectState state = GeometryLineLine.stateLineLine(
                        middle,
                        data.getData().getCoordinateByPointId(idNextPoint),
                        data.getData().getCoordinateByPointId(copyListLines.get(j).getIdPointA()),
                        data.getData().getCoordinateByPointId(copyListLines.get(j).getIdPointB()));

                if (state == GeometryLineLine.IntersectState.LINE_IN_LINE ||
                        state == GeometryLineLine.IntersectState.INTERSECT ||
                        state == GeometryLineLine.IntersectState.INTERSECT_POINT_ON_LINE) {
                    j = copyListLines.size() + 1;
                    haveIntersect = true;
                    inter.set(inter.size() - 1, state);
                }
            }

            //int pointA = borderLines.get(i).getIdPointA();
            //int pointB = borderLines.get(i).getIdPointB();
            Coordinate A = data.getData().getCoordinateByPointId(borderLines.get(i).getIdPointA());
            Coordinate B = data.getData().getCoordinateByPointId(borderLines.get(i).getIdPointB());
            Coordinate N = data.getData().getCoordinateByPointId(idNextPoint);
            GeometryLineLine.IntersectState state = GeometryLineLine.stateLineLine(A, N, B, N);

            if (state == GeometryLineLine.IntersectState.LINE_IN_LINE || state == GeometryLineLine.IntersectState.INTERSECT) {
                haveIntersect = true;
            }
            if (!haveIntersect) {
                addList.set(i, true);
            }
        }

//        logger.info("borderLines = " + borderLines);
//        logger.info("addList = " + addList);

        // remove if have intersect
        List<Line> listLineWithoutIntersect = new ArrayList<>();
        for (int i = 0; i < addList.size(); i++) {
            if (addList.get(i))
                listLineWithoutIntersect.add(borderLines.get(i));
        }

//        logger.info("end of addNextPointOutside()");
//        logger.line();
        addNextPointInOutsideLoop(idNextPoint, listLineWithoutIntersect);
    }

    private void addNextPointWithoutBorder(Integer idNextPoint) throws Exception {
//        logger.line();
//        logger.info("In function addNextPointWithoutBorder()");
        // check - not enough points
        if (idPointAdded.size() < 3) {
//            logger.info("not enough points");
//            logger.info("end of addNextPointWithoutBorder()");
//            logger.line();
            return;
        }
        // if all points on NOT one line
        List<Coordinate> coordinatePoints = new ArrayList<>();
        for (int i = 0; i < idPointAdded.size(); i++) {
            coordinatePoints.add(data.getData().getCoordinateByPointId(idPointAdded.get(i).getId()));
        }
        if (GeometryCoordinate.isPointsOnOneLine(coordinatePoints)) {
//            logger.info("points in not on one line");
//            logger.info("end of addNextPointWithoutBorder()");
//            logger.line();
            return;
        }
        // create line, triangles between all points
        List<Integer> point = new ArrayList<>();
        point.add(idPointAdded.get(idPointAdded.size() - 1).getId());
        point.add(idPointAdded.get(idPointAdded.size() - 2).getId());
        point.add(idPointAdded.get(idPointAdded.size() - 3).getId());

        addSimpleTriangle(point);

        for (int i = 0; i < idPointAdded.size() - 3; i++) {
            addNextPoint(idPointAdded.get(i));
        }
//        logger.info("add triangle");
//        logger.info("end of addNextPointWithoutBorder()");
//        logger.line();
        return;
    }

/*
    private void sortLines(List<Line> listLines) throws Exception {
//        logger.line();
//        logger.info("In function sortLines()");
//        logger.info("listLines = " + listLines);
        if (listLines.size() < 2)
            throw new Exception("Not enough lines");
        List<Line> loop = new ArrayList<>();
        loop.add(listLines.get(0));
        for (int i = 1; i < listLines.size(); i++) {
            int idLastPoint = loop.get(loop.size() - 1).getIdPointA();
            int idNextPoint = loop.get(loop.size() - 1).getIdPointB();
            for (int j = 0; j < listLines.size(); j++) {
                Line line = listLines.get(j);
                if (line.getIdPointA() == idNextPoint && line.getIdPointB() != idLastPoint) {
                    loop.add(new Line(line.getId(), line.getIdPointA(), line.getIdPointB()));
                    j = listLines.size();
                }
                if (line.getIdPointB() == idNextPoint && line.getIdPointA() != idLastPoint) {
                    loop.add(new Line(line.getId(), line.getIdPointB(), line.getIdPointA()));
                    j = listLines.size();
                }
            }
        }

        if (loop.get(0).getIdPointA() != loop.get(loop.size() - 1).getIdPointB())
            throw new Exception("Not correct loop ={"
                    + loop.get(0).getIdPointA()
                    + " ; "
                    + loop.get(loop.size() - 1).getIdPointB()
                    + " } ");
        if (loop.size() == listLines.size()) {
            listLines = loop;
        } else
            throw new Exception("Cannot find loop");
//        logger.info("listLines = " + listLines);
//        logger.info("end of sortLines()");
//        logger.line();
    }
    */

    private void addSimpleTriangle(List<Integer> point) throws Exception {
//        logger.line();
//        logger.info("In function addSimpleTriangle()");
        if (point.size() != 3)
            throw new Exception("Strange size of points");
        data.addLine(point.get(0), point.get(1));
        data.addLine(point.get(1), point.get(2));
        data.addLine(point.get(2), point.get(0));
        data.addTriangle(
                point.get(0),
                point.get(1),
                point.get(2));
//        logger.info("end of addSimpleTriangle()");
//        logger.line();
    }


    private void addNextPointInOutsideLoop(Integer idNextPoint, List<Line> listLineWithoutIntersect) throws Exception {
//        logger.line();
//        logger.info("In function addNextPointInOutsideLoop()");
//        logger.info("idNextPoint = " + idNextPoint);
//        logger.info("listLineWithoutIntersect = " + listLineWithoutIntersect);
        if (listLineWithoutIntersect.size() < 1)
            throw new Exception("Not found allowable line on loop");
        //create points
        Set<Integer> idPoints = new HashSet<>();
        for (int i = 0; i < listLineWithoutIntersect.size(); i++) {
            idPoints.add(listLineWithoutIntersect.get(i).getIdPointA());
            idPoints.add(listLineWithoutIntersect.get(i).getIdPointB());
        }
        //create lines
        Iterator<Integer> iteratorPoints = idPoints.iterator();
        while (iteratorPoints.hasNext()) {
            data.addLine(idNextPoint, iteratorPoints.next());
        }
        //create triangles
        for (int i = 0; i < listLineWithoutIntersect.size(); i++) {
            data.addTriangle(
                    idNextPoint,
                    listLineWithoutIntersect.get(i).getIdPointA(),
                    listLineWithoutIntersect.get(i).getIdPointB()
            );
        }
//        logger.info("end of addNextPointInOutsideLoop()");
//        logger.line();
    }

    private void addNextPointInTriangle(int idNextPoint, int idTriangleWithNextPoint) throws Exception {
        int[] idPointTriangle = data.getData().getIdPointByTriangle(idTriangleWithNextPoint);

        data.addLine(idNextPoint, idPointTriangle[0]);
        data.addLine(idNextPoint, idPointTriangle[1]);
        data.addLine(idNextPoint, idPointTriangle[2]);

        data.addTriangle(idNextPoint, idPointTriangle[0], idPointTriangle[1]);
        data.addTriangle(idNextPoint, idPointTriangle[1], idPointTriangle[2]);
        data.addTriangle(idNextPoint, idPointTriangle[2], idPointTriangle[0]);

        data.deleteTriangle(idTriangleWithNextPoint);
    }

    private void addNextPointOnLine(Point nextPoint, Integer idLineWithNextPoint) throws Exception {
//        logger.line();
//        logger.info("In function addNextPointOnLine()");
//        logger.info("|*** ADD TRIANGLE -S: ON LINE ***|");

//        String strLogger = "addNextPointOnLine - start\n";
//        strLogger += "idNextPoint = " + idNextPoint + "\n";
//        strLogger += "idLineWithNextPoint = " + idLineWithNextPoint;
//        logger.info(strLogger);

        List<Integer> listTrianglesId = data.getIdTrianglesByLine(idLineWithNextPoint);
        int[] listPointsLine = data.getData().getIdPointsByLine(idLineWithNextPoint);
        if (listPointsLine.length != 2)
            throw new Exception("listPointsLine != 2, length = " + listPointsLine.length);
        if (nextPoint.getId() == listPointsLine[0] || nextPoint.getId() == listPointsLine[1])
            throw new Exception("idPoint is same for next point and line");

//        logger.info("listTrianglesId = " + listTrianglesId);
        for (int i = 0; i < listTrianglesId.size(); i++) {
            int[] listPointsTriangles = data.getData().getIdPointByTriangle(listTrianglesId.get(i));
            List<Integer> listForFindVertex = new ArrayList<>();
            for (int j = 0; j < listPointsTriangles.length; j++) {
                listForFindVertex.add(listPointsTriangles[j]);
            }
            for (int j = 0; j < listForFindVertex.size(); j++) {
                for (int k = 0; k < listPointsLine.length; k++) {
                    if (listForFindVertex.get(j).equals(listPointsLine[k])) {
                        listForFindVertex.remove(j);
                        j = j - 1;
                        k = listPointsLine.length;
                    }
                }
            }
            if (listForFindVertex.size() != 1)
                throw new Exception("Vertex is not single in array");
            int idVertex = listForFindVertex.get(0);
            data.addTriangle(idVertex, nextPoint.getId(), listPointsLine[0]);
            data.addTriangle(idVertex, nextPoint.getId(), listPointsLine[1]);
            data.addLine(idVertex, nextPoint.getId());
        }
        for (int i = 0; i < listTrianglesId.size(); i++)
            data.deleteTriangle(listTrianglesId.get(i));
        data.addLine(listPointsLine[0], nextPoint.getId());
        data.addLine(listPointsLine[1], nextPoint.getId());
        data.deleteLine(idLineWithNextPoint);
//        logger.info("End of function addNextPointOnLine()");
//        logger.line();
    }
}
