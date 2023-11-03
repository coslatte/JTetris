package GUI.Tetris;

import GUI.Properties.TColors;
import GUI.Properties.TStyle;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel {
    private final int[][] fieldArray;

    public LeftPanel() {
        setBackground(TColors.BLACK202);
        fieldArray = new int[20][10];
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
        g2D.setColor(TColors.BRIGHT_GREEN);
        g2D.setStroke(new BasicStroke(TStyle.panelsBordersWidth));
        g2D.drawRoundRect(
                getWidth() / 350 + 5,
                getHeight() / 128,
                getWidth() - 13,
                getHeight() - 13,
                TStyle.rectangleRoundBordersAmount,
                TStyle.rectangleRoundBordersAmount
        );
    }

    public int[][] getFieldArray() {
        return fieldArray;
    }
}
