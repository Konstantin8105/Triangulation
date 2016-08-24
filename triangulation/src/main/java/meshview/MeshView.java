package meshview;

import triangulationAdvance.BorderBox;
import triangulationAdvance.Point;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MeshView extends JFrame {

    final int WINDOWS_SIZE = 800;
    final private boolean nodeText = true;


    public MeshView(final List<Point[]> mesh) {

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
                for (int i = 0; i < mesh.size(); i++) {
                    Point[] position = new Point[3];
                    for (int j = 0; j < 3; j++) {
                        position[j] = new Point(
                                WINDOWS_SIZE / 2 + (mesh.get(i)[j].getX() - box.getCenter().getX()) * scale,
                                WINDOWS_SIZE / 2 + (mesh.get(i)[j].getY() - box.getCenter().getY()) * scale
                        );
                    }
                    if (nodeText) {
                        g.setColor(Color.RED);
                        for (int j = 0; j < 3; j++) {
                            g.drawString(
                                    "(" + (int) mesh.get(i)[j].getX() + ";" + (int) mesh.get(i)[j].getY() + ")",
                                    (int) position[j].getX(),
                                    (int) position[j].getY()
                            );
                        }
                    }
                    g.setColor(Color.BLUE);
                    g.drawLine(
                            (int) position[0].getX(),
                            (int) position[0].getY(),
                            (int) position[1].getX(),
                            (int) position[1].getY()
                    );
                    g.drawLine(
                            (int) position[1].getX(),
                            (int) position[1].getY(),
                            (int) position[2].getX(),
                            (int) position[2].getY()
                    );
                    g.drawLine(
                            (int) position[2].getX(),
                            (int) position[2].getY(),
                            (int) position[0].getX(),
                            (int) position[0].getY()
                    );

                    g.setColor(Color.RED);
                    g.drawOval((int) position[0].getX(), (int) position[0].getY(), 3, 3);
                    g.drawOval((int) position[1].getX(), (int) position[1].getY(), 3, 3);
                    g.drawOval((int) position[2].getX(), (int) position[2].getY(), 3, 3);

                    g.setColor(Color.BLACK);
                    Point center = new Point(
                            (position[0].getX() + position[1].getX() + position[2].getX()) / 3,
                            (position[0].getY() + position[1].getY() + position[2].getY()) / 3
                    );
                    if (nodeText) {
                        g.drawString(
                                i + "",
                                (int) center.getX(),
                                (int) center.getY()
                        );
                    }
                }
            }
        };
        setContentPane(panel);

        this.setSize(new Dimension(WINDOWS_SIZE + 100, WINDOWS_SIZE + 100));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
