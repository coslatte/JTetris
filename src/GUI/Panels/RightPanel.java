package GUI.Panels;

import java.awt.*;

public class RightPanel extends TPanel {
    public RightPanel() {
        setPreferredSize(new Dimension(rightPanelDimension[0], rightPanelDimension[1]));
        setLocation(rightPanelLocationXY[0], rightPanelLocationXY[1]);
        setLayout(null);
    }

    public void drawStuff(Graphics2D g2D) {
        g2D.setColor(strokeColor);
        g2D.setStroke(new BasicStroke(5f));
        g2D.drawRect(
                rightPanelLocationXY[0] + 5,
                rightPanelLocationXY[1] + 5,
                rightPanelDimension[0] - 10,
                rightPanelDimension[1] - 10
        );
    }
}
