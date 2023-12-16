package GUI.Components.Panels.Game;

import GUI.Properties.TColor;
import GUI.Properties.TStyle;

import javax.swing.*;
import java.awt.*;

/**
 * Clase donde van las matrices y básicamente el juego; donde se anima.
 */
public class GridPanel extends JPanel {

    /**
     * <code> fieldArray </code> tiene la responsabilidad de iniciar los valores (en 0) de la matriz
     * donde van a estar los bloques que forman a los tetrominos.
     */
    private final int[][] fieldArray = new int[20][10];
    public GridPanel() {
        setBackground(TColor.BLACK202);
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
        g2D.setColor(TColor.BRIGHT_GREEN);
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
