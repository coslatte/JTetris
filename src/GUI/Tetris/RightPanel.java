package GUI.Tetris;

import GUI.Properties.TColors;
import GUI.Properties.TStyle;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {
    public RightPanel() {
        setBackground(TColors.BLACK202);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawStuff(g2);
    }

    public void drawStuff(Graphics2D g2D) {
        g2D.setColor(TColors.BRIGHT_GREEN);
        g2D.setStroke(new BasicStroke(TStyle.panelsBordersWidth));
        g2D.drawRoundRect(
                getWidth() / 300 + 47,
                getHeight() / 128,
                getWidth() - 54,
                getHeight() - 13,
                TStyle.rectangleRoundBordersAmount,
                TStyle.rectangleRoundBordersAmount
        );

        g2D.setStroke(new BasicStroke(3));
        g2D.drawRoundRect(
                getWidth() / 300 + 54,
                getHeight() / 128 + 7,
                getWidth() - 69,
                getHeight() / 2 - 30,
                TStyle.rectangleRoundBordersAmount,
                TStyle.rectangleRoundBordersAmount
        );
        g2D.drawRoundRect(
                getWidth() / 300 + 54,
                getHeight() / 2 - 10,
                getWidth() - 69,
                getHeight() / 2 - 6,
                TStyle.rectangleRoundBordersAmount,
                TStyle.rectangleRoundBordersAmount
        );
    }
}
