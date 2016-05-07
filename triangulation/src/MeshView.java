import triangulation.Triangulation;
import triangulation.elements.Mesh;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeshView extends JFrame {

    class LineDraw {
        public triangulation.elements.Point p[] = new triangulation.elements.Point[2];
    }

    public MeshView(Mesh mesh) {
        List<LineDraw> lines = new ArrayList<>();
        for (int i = 0; i < mesh.getLines().size(); i++) {
            LineDraw line = new LineDraw();
            line.p[0] = new triangulation.elements.Point(mesh.getPoints(mesh.getLines().getByIndex(i).getIdPointA()));
            line.p[1] = new triangulation.elements.Point(mesh.getPoints(mesh.getLines().getByIndex(i).getIdPointB()));
            lines.add(line);
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
            }
        };
        setContentPane(panel);

        this.setSize(new Dimension(800, 700));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        triangulation.elements.Point[] coordinates = new triangulation.elements.Point[]{
                new triangulation.elements.Point(000.0f, 000.0f),
                new triangulation.elements.Point(100.0f, 000.0f),
                new triangulation.elements.Point(100.0f, 100.0f),
                new triangulation.elements.Point(100.0f, 400.0f)
        };

        Triangulation triangulation = new Triangulation(Arrays.asList(coordinates));

        MeshView view = new MeshView(triangulation.getMesh());
    }
}
