package meshview;

import research.ResearchTest;
import triangulation.Triangulation;
import triangulation.border.BorderBox;
import triangulation.elements.Point;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MeshView extends JFrame {

    int WINDOWS_SIZE = 800;

    public MeshView(final List<Point[]> mesh) {

        BorderBox box = new BorderBox();
        for (int i = 0; i < mesh.size(); i++) {
            for (int j = 0; j < mesh.get(i).length; j++) {
                box.addPoint(mesh.get(i)[j]);
            }
        }

        double scale = Math.min(
                WINDOWS_SIZE/(box.getX_max()-box.getX_min()),
                WINDOWS_SIZE/(box.getY_max()-box.getY_min())
        );

        for (int i = 0; i < mesh.size(); i++) {
            for (int j = 0; j < mesh.get(i).length; j++) {
                mesh.get(i)[j].setX(WINDOWS_SIZE/2+(mesh.get(i)[j].getX()-box.getCenter().getX())*scale);
                mesh.get(i)[j].setY(WINDOWS_SIZE/2+(mesh.get(i)[j].getY()-box.getCenter().getY())*scale);
            }
        }

        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = ((Graphics2D) g);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setStroke(new BasicStroke(1));
                for (int i = 0; i < mesh.size(); i++) {
                    g.setColor(Color.BLUE);
                    g.drawLine(
                            (int) mesh.get(i)[0].getX(),
                            (int) mesh.get(i)[0].getY(),
                            (int) mesh.get(i)[1].getX(),
                            (int) mesh.get(i)[1].getY()
                    );
                    g.drawLine(
                            (int) mesh.get(i)[1].getX(),
                            (int) mesh.get(i)[1].getY(),
                            (int) mesh.get(i)[2].getX(),
                            (int) mesh.get(i)[2].getY()
                    );
                    g.drawLine(
                            (int) mesh.get(i)[2].getX(),
                            (int) mesh.get(i)[2].getY(),
                            (int) mesh.get(i)[0].getX(),
                            (int) mesh.get(i)[0].getY()
                    );
                    g.setColor(Color.RED);
                    g.drawOval((int) mesh.get(i)[0].getX(), (int) mesh.get(i)[0].getY(), 3, 3);
                    g.drawOval((int) mesh.get(i)[1].getX(), (int) mesh.get(i)[1].getY(), 3, 3);
                    g.drawOval((int) mesh.get(i)[2].getX(), (int) mesh.get(i)[2].getY(), 3, 3);
                }
            }
        };
        setContentPane(panel);

        this.setSize(new Dimension(WINDOWS_SIZE+100, WINDOWS_SIZE+100));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        int AMOUNT_POINTS = 100;
        {
            List<Point> points = ResearchTest.getRandomPoints(AMOUNT_POINTS);
            Triangulation triangulation = new Triangulation(points);
            MeshView meshView = new MeshView(triangulation.getTriangles());
        }
        {
            List<Point> points = ResearchTest.getCirclePoints(AMOUNT_POINTS);
            Triangulation triangulation = new Triangulation(points);
            MeshView meshView = new MeshView(triangulation.getTriangles());
        }
        {
            List<Point> points = ResearchTest.getLineOnLine(AMOUNT_POINTS);
            Triangulation triangulation = new Triangulation(points);
            MeshView meshView = new MeshView(triangulation.getTriangles());
        }
        {
            List<Point> points = ResearchTest.getInTriangles(AMOUNT_POINTS);
            Triangulation triangulation = new Triangulation(points);
            MeshView meshView = new MeshView(triangulation.getTriangles());
        }
    }
}
