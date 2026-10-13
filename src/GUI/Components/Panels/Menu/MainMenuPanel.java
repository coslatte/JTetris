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
     * Panel de inicio del juego que contendr\u00e1:
     * <li> T\u00edtulo del juego.
     * <li> Bot\u00f3n <b>PLAY</b>, para empezar a jugar.
     * <li> Bot\u00f3n <b>OPTIONS</b>, para los ajustes y el nivel inicial.
     * <li> Bot\u00f3n <b>EXIT</b>, para salir de la aplicaci\u00f3n.
     */

public class MainMenuPanel extends JPanel {

    /**
     * Medida de la ventana. El panel no es redimensionable, as\u00ed que se calculan las posiciones una
     * sola vez y no hace falta volver a calcularlas en cada pintado.
     */
    private static final int WIDTH = 650;
    private static final int HEIGHT = 700;

    private static final int BUTTON_WIDTH = 190;
    private static final int BUTTON_HEIGHT = 52;

    MenuButton playButton, optionsButton, exitButton;

    public MainMenuPanel() {
        playButton = new PlayButton("PLAY");
        optionsButton = new OptionsButton("OPTIONS");
        exitButton = new ExitButton("EXIT");

        setBackground(TColor.BLACK202);
        setLayout(null);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // Los botones se ubican aca y no en paintComponent: modificar los bounds de un componente
        // mientras se pinta deja al hijo dibujandose con la posicion del fotograma anterior.
        int left = (WIDTH - BUTTON_WIDTH) / 2;
        int first = HEIGHT / 2 - 30;
        playButton.setBounds(left, first, BUTTON_WIDTH, BUTTON_HEIGHT);
        optionsButton.setBounds(left, first + 70, BUTTON_WIDTH, BUTTON_HEIGHT);
        exitButton.setBounds(left, first + 140, BUTTON_WIDTH, BUTTON_HEIGHT);

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

        Graphics2D g2 = (Graphics2D) g;

        // Dibujando el borde del Panel.
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

        // Titulo del juego.
        g2.setFont(new Font("Arial", Font.BOLD, 64));
        String title = "JTETRIS";
        int titleWidth = g2.getFontMetrics().stringWidth(title);
        g2.drawString(title, (getWidth() - titleWidth) / 2, HEIGHT / 4);

        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        g2.setColor(TColor.LABEL);
        String subtitle = "hecho a mano, sin herramientas de dise\u00f1o";
        int subtitleWidth = g2.getFontMetrics().stringWidth(subtitle);
        g2.drawString(subtitle, (getWidth() - subtitleWidth) / 2, HEIGHT / 4 + 30);
    }
}
