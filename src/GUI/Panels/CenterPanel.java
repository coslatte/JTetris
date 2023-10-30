package GUI.Panels;

import java.awt.*;

public class CenterPanel extends TPanel {
    private final int[][] fieldArray;
    public CenterPanel() {
        setPreferredSize(new Dimension(centerPanelDimension[0], centerPanelDimension[1]));
        setLocation(centerPanelLocationXY[0], centerPanelLocationXY[1]);
        setLayout(null);

        fieldArray = new int[20][10];
    }

    public void update() {
    }

    public void drawStuff(Graphics2D g2D) {
        g2D.setColor(strokeColor);
        g2D.setStroke(new BasicStroke(5f));
        g2D.drawRect(
                centerPanelLocationXY[0] + 5,
                centerPanelLocationXY[1] + 5,
                centerPanelDimension[0] - 10,
                centerPanelDimension[1] - 10
        );
    }

    public int[][] getFieldArray() {
        return fieldArray;
    }
}
