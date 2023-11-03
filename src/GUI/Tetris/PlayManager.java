package GUI.Tetris;

import GUI.Properties.TColors;
import GUI.Properties.TStyle;

import javax.swing.*;
import java.awt.*;

public class PlayManager extends JPanel {
    private final int[][] fieldArray;

    public PlayManager() {
        setBackground(TColors.BLACK202);
        fieldArray = new int[20][10];
    }

    public void update() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        draw(g2);
    }

    public void draw(Graphics2D g2D) {
        g2D.setColor(TColors.BRIGHT_GREEN);
        g2D.setStroke(new BasicStroke(TStyle.basicStrokeBordersWidth));
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
