import research.ResearchTest;
import triangulation.Triangulation;
import triangulation.elements.Collections.IDable;
import triangulation.elements.Line;
import triangulation.elements.Mesh;
import triangulation.elements.Point;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeshView extends JFrame {

    class LineDraw {
        public triangulation.elements.Point p[] = new triangulation.elements.Point[2];
    }

    public MeshView(final Mesh mesh) {

        final List<LineDraw> lines = new ArrayList<>();
        for (IDable<Line>.Element<Line> line : mesh.getLines()) {
            LineDraw lineDraw = new LineDraw();
            lineDraw.p[0] = new triangulation.elements.Point(mesh.getPoints(line.value.getIdPointA()));
            lineDraw.p[1] = new Point(mesh.getPoints(line.value.getIdPointB()));
            lines.add(lineDraw);
        }

        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = ((Graphics2D) g);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setStroke(new BasicStroke(3));
                g.setColor(Color.BLUE);
                for (LineDraw line : lines) {
                    g.drawLine(
                            (int) line.p[0].getX(),
                            (int) line.p[0].getY(),
                            (int) line.p[1].getX(),
                            (int) line.p[1].getY()
                    );
                }
                g.setColor(Color.RED);
                for (LineDraw line : lines) {
                    g.drawOval(
                            (int) line.p[0].getX(),
                            (int) line.p[0].getY()
                            , 3, 3);
                    g.drawOval(
                            (int) line.p[1].getX(),
                            (int) line.p[1].getY()
                            , 3, 3);
                }
                g.setColor(Color.BLACK);
                for (IDable<Point>.Element<Point> point : mesh.getPoints()) {
                    g.drawString(
                            point.id + "",
                            (int) point.value.getX(),
                            (int) point.value.getY() + 20
                    );
                }

            }
        };
        setContentPane(panel);

        int WINDOWS_SIZE = 700;
        this.setSize(new Dimension(WINDOWS_SIZE, WINDOWS_SIZE));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        int AMOUNT_POINTS = 200;
        {
            Triangulation triangulation = new Triangulation(ResearchTest.getRandomPoints(AMOUNT_POINTS));
            MeshView meshView = new MeshView(triangulation.getMesh());
        }
        {
            Triangulation triangulation = new Triangulation(ResearchTest.getCirclePoints(AMOUNT_POINTS));
            MeshView meshView = new MeshView(triangulation.getMesh());
        }
        {
            Triangulation triangulation = new Triangulation(ResearchTest.getLineOnLine(AMOUNT_POINTS));
            MeshView meshView = new MeshView(triangulation.getMesh());
        }
        {
            Triangulation triangulation = new Triangulation(ResearchTest.getInTriangles(AMOUNT_POINTS));
            MeshView meshView = new MeshView(triangulation.getMesh());
        }
    }
}
