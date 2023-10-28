package GUI.Panels;

import javax.swing.*;
import java.awt.*;

public class CenterPanel extends JPanel {
    public CenterPanel() {
        setPreferredSize(new Dimension(TetrisPanel.WIDTH / 3, TetrisPanel.HEIGHT));
        setLocation(TetrisPanel.WIDTH / 3, 0);
        setBackground(Color.decode("#303030"));
        setLayout(null);
    }
}
