package elements;

import elements.Collections.IDable;
import triangulation.BorderBox;
import triangulation.BorderLineConvexRegion;
import triangulation.GridIndex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdvanceMesh extends Mesh {

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
        int gridAmount = calculateSize();
        BorderBox bBox = new BorderBox();
        IDable<Point> pointIDable = getPoints();
        for (int i = 0; i < pointIDable.size(); i++) {
            bBox.addPoint(pointIDable.getByIndex(i));
        }
        triangleGrid = new GridIndex(gridAmount, bBox);
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
        return idLine;
    }

    @Override
    public void deleteLine(int idLine) {
        super.deleteLine(idLine);
        lineGrid.remove(idLine);
    }

    @Override
    public List<IDable.Element> getLines(Point point) {
        List<Integer> idLines = lineGrid.get(point);
        Collections.sort(idLines);
        List<IDable.Element> out = new ArrayList<>();
        int position = 0;
        for (int i = 0; i < lines.getListElements().size(); i++) {
            if (position == idLines.size())
                break;
            if (lines.getListElements().get(i).id == idLines.get(position)) {
                out.add(lines.getListElements().get(i));
                position++;
            }
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
    public List<IDable.Element> getTriangles(Point point) {
        List<Integer> ids = triangleGrid.get(point);
        Collections.sort(ids);
        List<IDable.Element> out = new ArrayList<>();
        int position = 0;
        for (int i = 0; i < lines.getListElements().size(); i++) {
            if (position == ids.size())
                break;
            if (triangles.getListElements().get(i).id == ids.get(position)) {
                out.add(triangles.getListElements().get(i));
                position++;
            }
        }
        return out;
    }

    /*
    @Override
    public IDable.Element[] getTrianglesByLine(IDable.Element line) throws Exception {
        BorderBox bBox = new BorderBox();
        bBox.addPoint(getPoints(((Line) line.value).getIdPointA()));
        bBox.addPoint(getPoints(((Line) line.value).getIdPointB()));
        List<Integer> idTriangles = triangleGrid.get(bBox);
        List<IDable.Element> localTriangles = new ArrayList<>(idTriangles.size());
        for (int i = 0; i < idTriangles.size(); i++) {
            localTriangles.add(this.triangles.getElementById(idTriangles.get(i)));
        }

        IDable.Element[] tri = new IDable.Element[2];
        int presentPosition = 0;
        for (int i = 0; i < localTriangles.size(); i++) {
            Line[] lines = ((Triangle) localTriangles.get(i).value).getLines();
            for (int j = 0; j < lines.length; j++) {
                if (((Line) line.value).equals(lines[j])) {
                    tri[presentPosition++] = localTriangles.get(i);
                    if (presentPosition == 2) {
                        return tri;
                    }
                }
            }
        }
        if (presentPosition == 0)
            throw new Exception("Line without triangle. line = " + (Line) line.value);
        return new IDable.Element[]{tri[0]};
    }*/

    private int calculateSize() throws Exception {
        //0.1 * Math.pow(data.getCoordinate().size(), 3d / 8d);
        //0.1d * Math.sqrt(data.getCoordinate().size());
        //Math.sqrt(sizePoints());
        double size = 0.3D * Math.pow(sizePoints(), 3D / 8D);
        double minimalAmount = 1d;
        return (int) Math.max(minimalAmount, size);
    }

}
