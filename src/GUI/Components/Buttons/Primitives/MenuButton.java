package GUI.Components.Buttons.Primitives;

import GUI.Properties.TColor;
import GUI.Properties.TStyle;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public abstract class MenuButton extends JButton {

    /**
     * Radio o grosor del borde del boton.
     */
    protected final int radius = 10;

    /**
     * Determina el estado de iluminado o no en que se encuentra el boton tras pasar el mouse sobre el.
     */
    protected boolean mouseEventState = false;

    /**
     * Texto del boton.
     */
    protected final String buttonText;

    /**
     * Fuente con la que se dibuja el texto del boton.
     */
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 20);

    /**
     * Borde redondeado del boton. Solo se ocupa de pintar el contorno; el relleno y el texto los dibuja
     * <code>paintComponent</code>, usando el tamano real del boton.
     */
    private final Border roundedBorder = new Border() {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(TColor.BRIGHT_GREEN);
            g2.setStroke(new BasicStroke(TStyle.basicStrokeBordersWidth));
            g2.draw(new RoundRectangle2D.Double(x, y, width, height, radius * 3, radius * 3));
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius, radius, radius, radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    };

    public MenuButton(String buttonText) {
        this.buttonText = buttonText;

        setOpaque(false);
        setFocusPainted(false); // Elimina el marco de enfoque.
        setContentAreaFilled(false); // Elimina el relleno de fondo predeterminado.
        setBorder(roundedBorder);
        setText(buttonText); // Agrega un texto al boton.

        // Evento de Iluminacion de los botones al pasar el Mouse.
        MouseAdapter mouseOverEvent = new MouseAdapter() {
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
        addMouseListener(mouseOverEvent);
        addMouseMotionListener(mouseOverEvent);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int w = getWidth();
        int h = getHeight();
        if (w <= 0 || h <= 0) {
            return;
        }

        g2.setColor(mouseEventState ? TColor.BLACK202.brighter() : TColor.BLACK202);
        g2.fill(new RoundRectangle2D.Double(0, 0, w, h, radius * 3, radius * 3));

        // La fuente es editable.
        g2.setFont(BUTTON_FONT);
        g2.setColor(TColor.BRIGHT_GREEN);

        // Con FontMetrics se obtiene la fuente ajustada al Graphic configurado, para luego centrar el texto.
        FontMetrics fontMetrics = g2.getFontMetrics();
        int textX = (w - fontMetrics.stringWidth(buttonText)) / 2;
        int textY = (h - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();

        g2.drawString(buttonText, textX, textY);
    }
}
