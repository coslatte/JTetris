package GUI.Panels;

import java.awt.*;

import javax.swing.JButton;

public class CenterPanel extends TPanel {
    private final int[][] fieldArray;
    public CenterPanel() {
        //setPreferredSize(new Dimension(centerPanelDimension[0], centerPanelDimension[1]));
        //setLocation(centerPanelLocationXY[0], centerPanelLocationXY[1]);
        fieldArray = new int[20][10];
        setBackground(new Color(30, 30, 30));
    }

    public void update() {
    }
    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawStuff(g2);
    }
    public void drawStuff(Graphics2D g2D) {
        g2D.setColor(strokeColor);
        g2D.setStroke(new BasicStroke(4));
        g2D.drawRoundRect(getWidth()/350+5, getHeight()/128, getWidth()-13, getHeight()-13, 20, 20);
    }

    public int[][] getFieldArray() {
        return fieldArray;
    }
}
