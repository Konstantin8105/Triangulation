package old.view;

import old.core.storage.RamMesh;

import javax.swing.*;
import java.awt.*;

public class MeshView extends JFrame {
    RamMesh data;

    public MeshView(RamMesh data) {
        this.data = data;
        initUI();
    }

    private void initUI() {
        setTitle("Mesh old.view");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setContentPane(new DrawPane());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class DrawPane extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
//            try {
//                List<Coordinate> coordinates = data.getCoordinate();
//                for (Coordinate coordinate : coordinates) {
//                    g.fillRect((int) coordinate.getX(), (int) coordinate.getY(), 1, 1);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            super.paintComponent(g);
        }
    }
}
