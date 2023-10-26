package GUI;

import javax.swing.*;
import java.awt.*;

public class TetrisPanel extends JPanel {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public TetrisPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.darkGray);
        setLayout(null);
    }
}
