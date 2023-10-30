package GUI.Panels;

import java.awt.*;

public class LeftPanel extends TPanel {
    public LeftPanel() {
        setPreferredSize(new Dimension(leftPanelDimension[0], leftPanelDimension[1]));
        setLocation(leftPanelLocationXY[0], leftPanelLocationXY[1]);
        setLayout(null);
    }

    public void drawStuff(Graphics2D g2D) {
        g2D.setColor(strokeColor);
        g2D.setStroke(new BasicStroke(5f));
        g2D.drawRect(
                leftPanelLocationXY[0] + 5,
                leftPanelLocationXY[1] + 5,
                leftPanelDimension[0] - 10,
                leftPanelDimension[1] - 10
        );
    }
}
