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
     * Radio o grosor del borde del botón.
     */
    protected final int radius = 10;

    /**
     * Determina el estado de iluminado o no en que se encuentra el botón tras pasar el mouse sobre él.
     */
    protected boolean mouseEventState = false;

    /**
     * Coordenadas con que se centra el String dentro del botón. Fundamentales.
     */
    protected int x, y, width, height;

    /**
     * Clase necesaria para definir los bordes a pintar.
     */
    protected RoundRectangle2D borderToBeFilled;

    /**
     * Texto del botón.
     */
    protected final String buttonText;

    public MenuButton(String buttonText) {
        this.buttonText = buttonText;
        setSize(100, 50);

        borderToBeFilled = new RoundRectangle2D.Double();
        Border border = new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                MenuButton.this.x = x;
                MenuButton.this.y = y;
                MenuButton.this.width = width;
                MenuButton.this.height = height;
                borderToBeFilled = new RoundRectangle2D.Double(x, y, width, height, radius + 20, radius + 20);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(TColor.BRIGHT_GREEN);
                g2.setStroke(new BasicStroke(TStyle.basicStrokeBordersWidth));
                g2.draw(borderToBeFilled);
            }

            @Override
            public Insets getBorderInsets(Component c) { return new Insets(radius, radius, radius, radius); }

            @Override
            public boolean isBorderOpaque() { return true; }
        };

        setBorder(null); // Elimina el borde.
        setText(null); // Elimina el texto predeterminado del botón.
        setFocusPainted(false); // Elimina el marco de enfoque.
        setContentAreaFilled(false); // Elimina el relleno de fondo predeterminado.
        setBorder(border); // Añade el borde al botón.
        setText(buttonText); // Agrega un texto al botón.

        // Evento de Iluminación de los botones al pasar el Mouse.
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
        Color e = (mouseEventState) ? TColor.BLACK202.brighter() : TColor.BLACK202;

        g2.setColor(e);
        g2.fill(borderToBeFilled);
        g2.setColor(TColor.BRIGHT_GREEN);

        // La fuente es editable.
        g2.setFont(new Font("Arial", Font.BOLD, 20));

        // Con FontMetrics se obtiene la fuente ajustada al Graphic configurado, para luego centrar el texto.
        FontMetrics fontMetrics = g2.getFontMetrics();

        // NO CAMBIAR ESTA SECCIÓN
        int textX = x + (width - fontMetrics.stringWidth(buttonText)) / 2;
        int textY = y + ((height - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent();

        g2.drawString(buttonText, textX, textY);
    }
}
