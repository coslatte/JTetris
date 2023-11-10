package GUI.Tetris.GUI_Components;

import GUI.Properties.TColor;
import GUI.Properties.TStyle;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class PlayButton extends JButton {

    /**
     * Determina el estado de iluminado o no en que se encuentra el botón tras pasar el mouse sobre él.
     */
    private boolean mouseEventState = false;

    /**
     * Coordenadas con que se centra el String dentro del botón. Fundamentales.
     */
    private int x1, y1, width1, height1;

    /**
     * Clase necesaria para definir los bordes a pintar.
     */
    private RoundRectangle2D borderToBeFilled;

    /**
     * Radio o grosor del borde del botón.
     */
    private final int radius;

    public PlayButton() {
        /*
         * Aquí se define el borde que tendrá el botón, es con una interfaz la cual construiremos acá.
         * En otras palabras, implementaremos sus métodos aquí.
         */
        setSize(100, 50);
        radius = 10;
        borderToBeFilled = new RoundRectangle2D.Double();

        // Botón que dará inicio al juego:
        Border border = new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.setColor(TColor.BRIGHT_GREEN);
                x1 = x;
                y1 = y;
                width1 = width;
                height1 = height;
                borderToBeFilled = new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius + 20, radius + 20);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(TStyle.basicStrokeBordersWidth));
                g2.draw(borderToBeFilled);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(radius + 1, radius + 1, radius + 2, radius);
            }

            @Override
            public boolean isBorderOpaque() {
                return true;
            }
        };

        /*
         * La función de las siguientes 4 líneas de codigo son para eliminar las características visuales
         * por default de los JButton en Java, las cuales no lucen muy llamativas que digamos...
         */
        setBorder(null); // Elimina el borde.
        setText(null); // Elimina el texto predeterminado del botón.
        setFocusPainted(false); // Elimina el marco de enfoque.
        setContentAreaFilled(false); // Elimina el relleno de fondo predeterminado.

        setBorder(border);// Añadir el borde al botón.
        setText("PLAY");

        /*
         * Un mouse adapter para manejar los eventos del botón que tengan que ver con el mouse.
         * En otras palabras, que se ilumine cuando pases el mouse sobre él.
         */
        MouseAdapter eventos = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseEventState = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseEventState = false;
                repaint();
            }
        };
        addMouseListener(eventos);
        addMouseMotionListener(eventos);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Color e = (mouseEventState) ? TColor.BLACK202.brighter() : TColor.BLACK202;
        g2.setColor(e);
        g2.fill(borderToBeFilled);

        g2.setColor(TColor.BRIGHT_GREEN);

        /*
         * El método setFont de debajo es editable.
         * Se le puede cambiar tamaño si se quiere, de todas formas se adapta al texto del botón.
         */
        // TODO hacer una clase que maneje configuraciones de aspectos personalizables como esta en un futuro...
        g2.setFont(new Font("Arial", Font.BOLD, 20));

        /*
         * Con este método se obtiene el font asignado a Graphics en este momento (o sea el asignado anteriormente)
         * para así poder centrar el texto del botón matemáticamente dentro de este.
         */
        FontMetrics metrics = g2.getFontMetrics();

        // NO CAMBIAR ESTA SECCIÓN
        int x = x1 + (width1 - metrics.stringWidth("PLAY")) / 2;
        int y = y1 + ((height1 - metrics.getHeight()) / 2) + metrics.getAscent();

        g2.drawString("PLAY", x, y);
    }
}
