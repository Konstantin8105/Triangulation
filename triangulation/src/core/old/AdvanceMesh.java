package core.old;

import elements.Collections.IDable;
import elements.Line;
import elements.Mesh;
import elements.Point;
import elements.Triangle;
import triangulation.BorderBox;

import java.util.*;

public class AdvanceMesh extends Mesh {

    BorderLineConvexRegion border = new BorderLineConvexRegion();

    GridIndex lineGrid;
    boolean isLineGridCreated = false;

    GridIndex triangleGrid;
    boolean isTriangleGridCreated = false;

    public void createLineGrid() throws Exception {
        int gridAmount = calculateSize();
        BorderBox bBox = new BorderBox();
        IDable<Point> pointIDable = getPoints();
        for (int i = 0; i < pointIDable.size(); i++) {
            bBox.addPoint(pointIDable.getByIndex(i));
        }
        lineGrid = new GridIndex(gridAmount, bBox);
    }

    public void createTriangleGrid() throws Exception {
        if (lineGrid == null) {
            int gridAmount = calculateSize();
            BorderBox bBox = new BorderBox();
            IDable<Point> pointIDable = getPoints();
            for (int i = 0; i < pointIDable.size(); i++) {
                bBox.addPoint(pointIDable.getByIndex(i));
            }
            triangleGrid = new GridIndex(gridAmount, bBox);
        } else {
            triangleGrid = new GridIndex(lineGrid);
        }
    }

    @Override
    public int addLine(Line line) throws Exception {
        if (!isLineGridCreated) {
            createLineGrid();
            isLineGridCreated = true;
        }
        int idLine = super.addLine(line);
        BorderBox lineBox = new BorderBox();
        lineBox.addPoint(getPoints(line.getIdPointA()));
        lineBox.addPoint(getPoints(line.getIdPointB()));
        lineGrid.add(idLine, lineBox);
        border.addLine(line);
        return idLine;
    }

    @Override
    public void deleteLine(int idLine) {
        super.deleteLine(idLine);
        lineGrid.remove(idLine);
    }

    @Override
    public List<IDable.Element> getLines(Point point) throws Exception {
        List<Integer> ids = lineGrid.get(point);
        List<IDable.Element> out = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            out.add(lines.getById(ids.get(i)));
        }
        return out;
    }

    @Override
    public int addTriangle(Triangle triangle) throws Exception {
        if (!isTriangleGridCreated) {
            createTriangleGrid();
            isTriangleGridCreated = true;
        }
        int id = super.addTriangle(triangle);
        BorderBox lineBox = new BorderBox();
        lineBox.addPoint(getPoints(triangle.getIdPoint1()));
        lineBox.addPoint(getPoints(triangle.getIdPoint2()));
        lineBox.addPoint(getPoints(triangle.getIdPoint3()));
        triangleGrid.add(id, lineBox);
        return id;
    }

    @Override
    public void deleteTriangle(int id) {
        super.deleteTriangle(id);
        triangleGrid.remove(id);
    }

    @Override
    public List<IDable.Element> getTriangles(Point point) throws Exception {
        List<Integer> ids = triangleGrid.get(point);
        List<IDable.Element> out = new ArrayList<>(ids.size());
        for (int i = 0; i < ids.size(); i++) {
            out.add(triangles.getById(ids.get(i)));
        }
        return out;
    }


    @Override
    public IDable.Element[] getTrianglesByLine(Line line) throws Exception {
        Point A = getPoints(line.getIdPointA());
        Point B = getPoints(line.getIdPointB());
        Point middleOfLine = new Point(
                (A.getX() + B.getX()) / 2.0D,
                (A.getY() + B.getY()) / 2.0D
        );

        BorderBox bBox = new BorderBox();
        bBox.addPoint(middleOfLine);

        Set<Integer> idTriangles = new HashSet<>(triangleGrid.get(bBox));
        Iterator<Integer> iterator = idTriangles.iterator();

        List<IDable.Element> localTriangles = new ArrayList<>(idTriangles.size());
        while (iterator.hasNext()) {
            localTriangles.add(this.triangles.getById(iterator.next()));
        }

        IDable.Element[] tri = new IDable.Element[2];
        int presentPosition = 0;
        for (int i = 0; i < localTriangles.size(); i++) {
            Line[] lines = ((Triangle) localTriangles.get(i).value).getLines();
            for (int j = 0; j < lines.length; j++) {
                if (line.equals(lines[j])) {
                    tri[presentPosition++] = localTriangles.get(i);
                    if (presentPosition == 2) {
                        return tri;
                    }
                }
            }
        }
        if (presentPosition == 0)
            throw new Exception("Line without triangle. line = " + line);
        return new IDable.Element[]{tri[0]};
    }


    @Override
    public List<Line> getBorderLine() throws Exception {
        return border.getBorderLine(this);
    }

    private int calculateSize() throws Exception {
        //0.1 * Math.pow(data.getCoordinate().size(), 3d / 8d);
        //0.1d * Math.sqrt(data.getCoordinate().size());
        //0.1D * Math.pow(sizePoints(), 3D / 8D);0.1D * Math.sqrt(sizePoints());
        double size = 0.1D * Math.pow(sizePoints(), 3D / 8D);
        double minimalAmount = 1d;
        return (int) Math.max(minimalAmount, size);
    }

}
