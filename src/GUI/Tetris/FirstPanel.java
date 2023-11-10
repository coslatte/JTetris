package GUI.Tetris;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import GUI.TetrisGUI;
import GUI.Properties.TColor;
import GUI.Properties.TStyle;
import GUI.Tetris.GUI_Components.PlayButton;
import java.awt.*;

    /**
     * Panel de inicio del juego que contendrá:
     * <li> Título del juego.
     * <li> Botón <strong>PLAY</strong>, para empezar a jugar.
     * <li> Botón <strong>OPTIONS</strong>, para ajustes de configuraciones varias. // Próximamente
     * <li> Botón <strong>Salir</strong>. // Próximamente.
     */
public class FirstPanel extends JPanel {

    /**
     * Botón <strong>PLAY</strong>.
     */
    private final PlayButton playButton;

    /**
     * Hilo para animación (Próximamente)
     */
    Thread firstAnimation;

    public FirstPanel() {
        setBackground(TColor.BLACK202);
        setLayout(null);
        playButton = new PlayButton();
        add(playButton);
    }

    public void addActionForButton(ActionListener e) {
        playButton.addActionListener(e);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(TColor.BRIGHT_GREEN);
        g2.setStroke(new BasicStroke(TStyle.basicStrokeBordersWidth));
        g2.drawRoundRect(
                getWidth() / 350 + 5,
                getHeight() / 128,
                getWidth() - 13,
                getHeight() - 13,
                TStyle.rectangleRoundBordersAmount,
                TStyle.rectangleRoundBordersAmount
        );

        // Coordenadas y dimensiones que tendrá el botón.
        playButton.setBounds(getWidth() / 3 + 28, getHeight() / 2 + 50, getWidth() / 4, getHeight() / 16);
    }
}
