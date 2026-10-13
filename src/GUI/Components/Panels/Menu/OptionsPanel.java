package GUI.Components.Panels.Menu;

import GUI.Components.Buttons.BackButton;
import GUI.Properties.TColor;
import GUI.Properties.TStyle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Pantalla de opciones.
 * <p>
 * Por ahora tiene lo justo para que el juego se pueda configurar de verdad: el nivel con el que arranca la
 * partida y el listado de teclas. Nada de esto es decorativo, el nivel elegido se le pasa al tablero.
 */
public class OptionsPanel extends JPanel {

    /**
     * Nivel mas bajo que se puede elegir.
     */
    private static final int MIN_LEVEL = 1;

    /**
     * Nivel mas alto que se puede elegir.
     */
    private static final int MAX_LEVEL = 20;

    /**
     * Medida de la ventana.
     */
    private static final int WIDTH = 650;
    private static final int HEIGHT = 700;

    private int startLevel = 1;

    /**
     * Muestra el nivel elegido. Se actualiza cada vez que se tocan los botones de menos y mas.
     */
    private final JLabel levelLabel;

    private final BackButton backButton;

    public OptionsPanel() {
        setBackground(TColor.BLACK202);
        setLayout(new BorderLayout(0, 16));
        setBorder(new EmptyBorder(48, 40, 32, 40));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        levelLabel = new JLabel();
        levelLabel.setForeground(TColor.BRIGHT_GREEN);
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        levelLabel.setFont(new Font("Monospaced", Font.BOLD, 22));
        levelLabel.setPreferredSize(new Dimension(50, 34));
        refreshLevelLabel();

        add(buildContent(), BorderLayout.CENTER);

        backButton = new BackButton("VOLVER");
        backButton.setPreferredSize(new Dimension(180, 50));

        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        south.setOpaque(false);
        south.add(backButton);
        add(south, BorderLayout.SOUTH);
    }

    /**
     * Arma la parte de arriba: titulo, selector de nivel y la lista de teclas.
     */
    private JComponent buildContent() {
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 18, 0);

        JLabel title = new JLabel("OPCIONES");
        title.setForeground(TColor.BRIGHT_GREEN);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        content.add(title, c);

        // Fila del selector de nivel.
        c.gridy = 1;
        c.insets = new Insets(0, 0, 26, 0);

        JPanel levelRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        levelRow.setOpaque(false);

        JLabel levelText = new JLabel("Nivel inicial:");
        levelText.setForeground(TColor.LABEL);
        levelText.setFont(new Font("Arial", Font.PLAIN, 16));
        levelRow.add(levelText);

        levelRow.add(makeStepButton("<", -1));
        levelRow.add(levelLabel);
        levelRow.add(makeStepButton(">", 1));

        content.add(levelRow, c);

        // Lista de teclas, para que el jugador no las tenga que adivinar.
        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);

        JTextArea help = new JTextArea(
                "FLECHAS ......... mover la pieza\n"
                        + "FLECHA ABAJO ..... bajar de a una\n"
                        + "ESPACIO .......... soltar la pieza\n"
                        + "FLECHA ARRIBA .... rotar\n"
                        + "X / Z ............ rotar en el otro sentido\n"
                        + "P / ESC .......... pausar\n"
                        + "R ................ reiniciar, al terminar"
        );
        help.setEditable(false);
        help.setFocusable(false);
        help.setOpaque(false);
        help.setForeground(TColor.LABEL);
        help.setFont(new Font("Monospaced", Font.PLAIN, 14));
        content.add(help, c);

        return content;
    }

    /**
     * Arma uno de los dos botones que cambian el nivel de a uno en uno.
     *
     * @param text  Lo que dice el boton.
     * @param delta Cuanto sube o baja el nivel.
     */
    private JButton makeStepButton(String text, int delta) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(TColor.BRIGHT_GREEN);
        button.setBackground(TColor.BLACK303);
        button.setFocusPainted(false);
        // Con el fondo pintado a mano y sin painted=false, Swing no lo vuelve a pintar de blanco.
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(46, 34));
        button.addActionListener(e -> {
            startLevel = Math.max(MIN_LEVEL, Math.min(MAX_LEVEL, startLevel + delta));
            refreshLevelLabel();
        });
        return button;
    }

    /**
     * Vuelca el nivel actual en la etiqueta.
     */
    private void refreshLevelLabel() {
        levelLabel.setText(String.valueOf(startLevel));
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
    }

    /**
     * Nivel con el que el jugador quiere arrancar la partida.
     */
    public int getStartLevel() {
        return startLevel;
    }

    public void addActionForBack(ActionListener e) {
        backButton.addActionListener(e);
    }
}
