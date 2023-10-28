package GUI.Panels;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel {
    public LeftPanel() {
        setPreferredSize(new Dimension(TetrisPanel.WIDTH / 3, TetrisPanel.HEIGHT));
        setLocation(0, 0);
        setBackground(Color.decode("#303030"));
        setLayout(null);
    }
}
