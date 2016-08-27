package meshview;

import triangulationAdvance.BorderBox;
import triangulationAdvance.Point;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MeshView extends JFrame {

    final private int WINDOWS_SIZE = 800;

    private void view(final List<Point[]> mesh, final Point point) {

        final boolean TEXT_SHOW;
        if (mesh.size() > 100)
            TEXT_SHOW = false;
        else
            TEXT_SHOW = true;

        final boolean POINT_INDICATOR;
        if (mesh.size() > 999)
            POINT_INDICATOR = false;
        else
            POINT_INDICATOR = true;

        final BorderBox box = new BorderBox();
        for (int i = 0; i < mesh.size(); i++) {
            for (int j = 0; j < mesh.get(i).length; j++) {
                box.addPoint(mesh.get(i)[j]);
            }
        }

        final double scale = Math.min(
                WINDOWS_SIZE / (box.getX_max() - box.getX_min()),
                WINDOWS_SIZE / (box.getY_max() - box.getY_min())
        );


        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = ((Graphics2D) g);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setStroke(new BasicStroke(1));


                class Triangle {
                    public Point[] points;
                    public String nameTriangle;
                    public String[] nameCoordinate;

                    public Triangle(Point[] position, String[] names, String s) {
                        points = position;
                        nameTriangle = s;
                        nameCoordinate = names;
                    }
                }

                List<Triangle> triangles = new ArrayList<>();

                for (int i = 0; i < mesh.size(); i++) {
                    Point[] position = new Point[3];
                    String[] names = new String[3];
                    for (int j = 0; j < 3; j++) {
                        position[j] = new Point(
                                WINDOWS_SIZE / 2 + (mesh.get(i)[j].getX() - box.getCenter().getX()) * scale,
                                WINDOWS_SIZE / 2 + (mesh.get(i)[j].getY() - box.getCenter().getY()) * scale
                        );
                        names[j] = "(" + (int) mesh.get(i)[j].getX() + ";" + (int) mesh.get(i)[j].getY() + ")";
                    }
                    triangles.add(new Triangle(position, names, i + ""));
                }

                g.setColor(Color.BLUE);
                for (int i = 0; i < triangles.size(); i++) {
                    for (int j = 0; j < 3; j++) {
                        int next = j + 1 == 3 ? 0 : j + 1;
                        g.drawLine(
                                (int) triangles.get(i).points[j].getX(),
                                (int) triangles.get(i).points[j].getY(),
                                (int) triangles.get(i).points[next].getX(),
                                (int) triangles.get(i).points[next].getY()
                        );
                    }
                }

                g.setColor(Color.RED);
                if(POINT_INDICATOR) {
                    for (int i = 0; i < triangles.size(); i++) {
                        for (int j = 0; j < 3; j++) {
                            g.drawOval(
                                    (int) triangles.get(i).points[j].getX() - 1,
                                    (int) triangles.get(i).points[j].getY() - 1,
                                    3, 3);
                        }
                    }
                }

                if (TEXT_SHOW) {
                    g.setColor(Color.BLACK);
                    for (int i = 0; i < triangles.size(); i++) {
                        Point center = new Point(
                                (triangles.get(i).points[0].getX() + triangles.get(i).points[1].getX() + triangles.get(i).points[2].getX()) / 3,
                                (triangles.get(i).points[0].getY() + triangles.get(i).points[1].getY() + triangles.get(i).points[2].getY()) / 3
                        );
                        g.drawString(
                                i + "",
                                (int) center.getX(),
                                (int) center.getY()
                        );
                    }

                    g.setColor(Color.RED);
                    for (int i = 0; i < triangles.size(); i++) {
                        for (int j = 0; j < 3; j++) {
                            g.drawString(
                                    triangles.get(i).nameCoordinate[j],
                                    (int) triangles.get(i).points[j].getX(),
                                    (int) triangles.get(i).points[j].getY()
                            );
                        }
                    }
                }
            }
            //special point
        };
        setContentPane(panel);
        this.setSize(new Dimension(WINDOWS_SIZE + 100, WINDOWS_SIZE + 100));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public MeshView(final List<Point[]> mesh) {
        view(mesh, null);
    }

}
