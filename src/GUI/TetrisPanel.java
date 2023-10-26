package GUI;

import javax.swing.*;
import java.awt.*;

public class TetrisPanel extends JPanel implements Runnable {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    Thread gameThread;

    public TetrisPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.darkGray);
        setLayout(null);
    }

    /**
     * Este método inicia el thread del Panel principal (TetrisPanel).
     */
    public void startGameThread() {
        //
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        /*
        Dentro de este while loop, vamos a necesitar 2 cosas para que el loop
        funcione correctamente:

        - Actualizar.
        - Pintar.

        Para eso entonces se definen los métodos 'update()' y 'paintComponent()'
        que se encargarán de actualizar datos y pintar cosas en pantalla con
        esos datos actualizados y nuevos.
         */
        while (gameThread != null) {

        }
    }

    public void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
