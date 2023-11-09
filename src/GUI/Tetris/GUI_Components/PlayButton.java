package GUI.Tetris.GUI_Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.border.Border;
import java.awt.*;
import GUI.Properties.TColor;
import GUI.Properties.TStyle;

public class PlayButton extends JButton{
    /*
     * boton que dara inicio a el juego
     */
    private Border border;
    private boolean state = false;
    private int x1, y1, width1, height1;
    private RoundRectangle2D borderToBeFilled;
    public PlayButton(){
        /*
         * aqui se define el borde que tendra el boton, es con una interfaz la cual construiremos aca, osea 
         * implementaremos sus metodos aqui
         */
        setSize(100, 50);
        int radius = 10;
        borderToBeFilled = new RoundRectangle2D.Double();
        border = new Border() {
            
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.setColor(TColor.BRIGHT_GREEN);
                x1 = x;
                y1 = y;
                width1 = width;
                height1 = height;
                borderToBeFilled = new RoundRectangle2D.Double(x, y, width-1, height-1,radius+20, radius+20);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(TStyle.basicStrokeBordersWidth));
                g2.draw(borderToBeFilled);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(radius+1, radius+1, radius+2, radius);
            }

            @Override
            public boolean isBorderOpaque() {
                return true;
            }
            
        };
        
        /*
         * las siguiente 4 lineas de codigo estan para eliminar las caracteristicas visuales por default de los JButton
         * en java las cuales no son muy agradables para la vista como comprenderan
         * 
         */
        setBorder(null); // Elimina el borde
        setText(null); // Elimina el texto predeterminado del botón
        setFocusPainted(false); // Elimina el marco de enfoque
        setContentAreaFilled(false);// Elimina el relleno de fondo predeterminado

        setBorder(border);// anadir el borde a el boton
        setText("PLAY");
        /*
         * un mouse adapter para manejar los eventos del boton que tengan que ver con el mouse en plan que se ilumine cuando pases 
         * el mouse sobre el
         */
        MouseAdapter eventos = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                state = true;
                repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                state = false;
                repaint();
            }
        };
        addMouseListener(eventos);
        addMouseMotionListener(eventos);
        
    }
    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Color e = (state) ? TColor.BLACK202.brighter(): TColor.BLACK202;
        g2.setColor(e);
        g2.fill(borderToBeFilled);
        
        g2.setColor(TColor.BRIGHT_GREEN);
        
        /*
         * el setFont de debajo es editable le pueden cambiar tamano y tal si quieren de todas formas se adapta para 
         * el texto de el boton
         */
        g2.setFont(new Font("Arial", Font.BOLD, 20));


        /*
         * con esto se obtiene el font asignado a graphics en este momento(osea el asignado anteriormente) para asi
         * poder centrar el texto del boton matematicamente dentro de este
         */
        FontMetrics metrics = g2.getFontMetrics();
        /*
         * con estas coordenadas se centra el string dentro de el boton, no lo cambien :)
         */
        int x = x1+(width1-metrics.stringWidth("PLAY"))/2;
        int y = y1+((height1-metrics.getHeight())/2)+metrics.getAscent();


        g2.drawString("PLAY", x, y);
    }
}
