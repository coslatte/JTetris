package GUI.Components.Panels.Menu;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.JPanel;

import GUI.Components.Buttons.ExitButton;
import GUI.Components.Buttons.Primitives.MenuButton;
import GUI.Components.Buttons.OptionsButton;
import GUI.Properties.TColor;
import GUI.Properties.TStyle;
import GUI.Components.Buttons.PlayButton;

    /**
     * Panel de inicio del juego que contendrá:
     * <li> Título del juego.
     * <li> Botón <b>PLAY</b>, para empezar a jugar.
     * <li> Botón <b>OPTIONS</b>, para ajustes de configuraciones varias. // WIP
     * <li> Botón <b>EXIT</b>, para Salir de la aplicación. // WIP
     */

public class MainMenuPanel extends JPanel {
    MenuButton playButton, optionsButton, exitButton;
    public MainMenuPanel() {
        playButton = new PlayButton("PLAY");
        optionsButton = new OptionsButton("OPTIONS");
        exitButton = new ExitButton("EXIT");

        setBackground(TColor.BLACK202);
        setLayout(null);
        add(playButton);
        add(optionsButton);
        add(exitButton);
    }

    public void addActionForButtonPlay(ActionListener e) {
        playButton.addActionListener(e);
    }

    public void addActionForButtonOptions(ActionListener e) {
        optionsButton.addActionListener(e);
    }

    public void addActionForButtonExit(ActionListener e) {
        exitButton.addActionListener(e);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujando el borde del Panel.
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(TColor.BRIGHT_GREEN);
        g2.setStroke(new BasicStroke(TStyle.basicStrokeBordersWidth - 1));
        g2.drawRoundRect(
                getWidth() / 350 + 5,
                getHeight() / 128,
                getWidth() - 13,
                getHeight() - 13,
                TStyle.rectangleRoundBordersAmount - 5,
                TStyle.rectangleRoundBordersAmount - 5
        );

        // Dibujando los botones.
        playButton.setBounds(
                getWidth() / 3 + 28,
                getHeight() / 2 + 50,
                getWidth() / 4,
                getHeight() / 16
        );

        optionsButton.setBounds(
                getWidth() / 3 + 28,
                getHeight() / 2 + 100,
                getWidth() / 4,
                getHeight() / 16
        );

        exitButton.setBounds(
                getWidth() / 3 + 28,
                getHeight() / 2 + 150,
                getWidth() / 4,
                getHeight() / 16
        );
    }
}
