package meshview;

import research.ResearchTest;
import triangulation.border.BorderBox;
import triangulation.elements.Point;
import triangulationAdvance.TriangulationAdvance;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MeshView extends JFrame {

    public MeshView(final List<Point[]> mesh) {

        BorderBox box = new BorderBox();
        for (int i = 0; i < mesh.size(); i++) {
            for (int j = 0; j < mesh.get(i).length; j++) {
                box.addPoint(mesh.get(i)[j]);
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

        int WINDOWS_SIZE = 800;
        this.setSize(new Dimension(WINDOWS_SIZE, WINDOWS_SIZE));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        int AMOUNT_POINTS = 10;

        {
            List<Point> points = ResearchTest.getCirclePoints(AMOUNT_POINTS);
//            Triangulation triangulation = new Triangulation(points);
//            MeshView meshView = new MeshView(triangulation.getTriangles());
            TriangulationAdvance triangulationAdvance = new TriangulationAdvance((Point[]) points.toArray());
            MeshView meshViewDelaunay = new MeshView(triangulationAdvance.getTriangles());
        }
//        {
//            triangulationAdvance.TriangulationAdvance triangulation = new triangulationAdvance.TriangulationAdvance(ResearchTest.getCirclePoints(AMOUNT_POINTS));
//            MeshView meshView = new MeshView(triangulation.getMesh());
//        }
//        {
//            triangulationAdvance.TriangulationAdvance triangulation = new triangulationAdvance.TriangulationAdvance(ResearchTest.getLineOnLine(AMOUNT_POINTS));
//            MeshView meshView = new MeshView(triangulation.getMesh());
//        }
//        {
//            triangulationAdvance.TriangulationAdvance triangulation = new triangulationAdvance.TriangulationAdvance(ResearchTest.getInTriangles(AMOUNT_POINTS));
//            MeshView meshView = new MeshView(triangulation.getMesh());
//        }
    }
}
