package GUI.Panels;

import java.awt.*;

public class RightPanel extends TPanel {
    public RightPanel() {
        setBackground(new Color(30, 30, 30));
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
        g2D.drawRoundRect(getWidth()/300+47, 
        getHeight()/128, 
        getWidth()-54, 
        getHeight()-13,
         20,
          20);

        g2D.setStroke(new BasicStroke(3));
        g2D.drawRoundRect(getWidth()/300+54, getHeight()/128+7,getWidth()-69, getHeight()/2-30, 20, 20);
        g2D.drawRoundRect(getWidth()/300+54, getHeight()/2-10, getWidth()-69, getHeight()/2-6, 20, 20);
    }
}
