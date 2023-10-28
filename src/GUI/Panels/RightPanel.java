package GUI.Panels;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {
    public RightPanel() {
        setPreferredSize(new Dimension(TetrisPanel.WIDTH / 3, TetrisPanel.HEIGHT));
        setLocation(TetrisPanel.WIDTH * 2 / 3, 0);
        setBackground(Color.decode("#303030"));
        setLayout(null);
    }
}
