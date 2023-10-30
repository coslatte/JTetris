package GUI.Panels;

import javax.swing.*;
import java.awt.*;

public class TPanel extends JPanel {
    // LEFT PANEL
    protected final int[] leftPanelDimension = {MainPanel.WIDTH / 3, MainPanel.HEIGHT}; // {x, y}
    protected final int[] leftPanelLocationXY = {0, 0}; // {x, y}

    // CENTER PANEL
    protected final int[] centerPanelDimension = {(int) (MainPanel.WIDTH / 3), MainPanel.HEIGHT}; // {x, y}
    protected final int[] centerPanelLocationXY = {(int) (MainPanel.WIDTH / 3), 0}; // {x, y}

    // RIGHT PANEL
    protected final int[] rightPanelDimension = {MainPanel.WIDTH / 3, MainPanel.HEIGHT}; // {x, y}
    protected final int[] rightPanelLocationXY = {MainPanel.WIDTH * 2 / 3, 0}; // {x, y}

    // Color de los bordes
    protected Color strokeColor = new Color(255, 255, 255);

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }
}
