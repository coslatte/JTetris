package GUI.Components.Panels.Game;

import GUI.Properties.TColor;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class GamePanel extends JPanel implements Runnable {

    /**
     * Cantidad de FPS que tendrá el juego.
     */
    private static final int FPS = 60;

    /**
     * Panel de la izquierda.
     */
    GridPanel lp;

    /**
     * Panel de la derecha.
     */
    ScorePanel scorePanel;

    /**
     * Hilo del juego.
     */
    Thread gameThread;

    public GamePanel() {
        setBackground(TColor.BLACK202);
        setLayout(new BorderLayout());

        // LEFT PANEL
        lp = new GridPanel();
        lp.setPreferredSize(new Dimension(380, 700));

        // RIGHT PANEL
        scorePanel = new ScorePanel();
        scorePanel.setPreferredSize(new Dimension(300, 700));

        add(lp, BorderLayout.WEST);
        add(scorePanel, BorderLayout.EAST);
    }

    // Este método inicia el thread del Panel principal (GamePanel).
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        /*
        Dentro de este while loop, vamos a necesitar 2 cosas para que el loop
        funcione correctamente:
        1. Actualizar. - update()
        2. Pintar. - paintComponent()
        Para eso entonces se definen los métodos 'update()' y 'paintComponent()'
        que se encargarán de actualizar datos y pintar cosas en pantalla con
        esos datos actualizados y nuevos.

        El método update() se va a actualizar tantas veces la CPU tenga las
        capacidades para hacerlo, dependiendo de qué tan fuerte sea. Por lo tanto,
        tenemos que buscar un límite de repeticiones para que los bloques
        no avancen unidades infinitas y sí lógicas.
         */

        // El game loop que vamos a usar será 'delta/accumulator loop'.
        double drawInterval = (double) 1000000000 / FPS; // Esto no es más que 0.016 seg.
        double delta = 0;

        /*
        nanoTime devuelve el tiempo de corrida actual de la Java Virtual Machine en nanosegundos.
        1 segundo = 1.000.000 nanosegundos.
        Esto hace falta para tener control sobre los FPS del juego.
         */
        long lastTime = System.nanoTime();
        long currentTime;

        // Datos para tener constancia de los FPS en tiempo real.
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            // Se toma el valor del tiempo actual en nanosegundos.
            currentTime = System.nanoTime();

            /*
            Se resta el tiempo actual (currentTime) con el último tiempo registrado (lastTime), o sea, cuánto tiempo
            ha pasado, y se divide entre los intervalos para dibujar que asignamos en 'drawInterval'.
            Finalmente, dicho resultado se suma a la variable 'delta'.
             */
            delta += (currentTime - lastTime) / drawInterval;

            // se actualiza el temporizador para los FPS.
            timer += (currentTime - lastTime);

            // El tiempo actual se guarda en el último tiempo registrado.
            lastTime = currentTime;

            /*
            Lo que pasa en este 'if statement' es que en cada iteración, agregamos (en 'delta') el tiempo transcurrido,
            dividido por el intervalo de pintar, y cuando 'delta' llega a ser igual al intervalo de pintar que asignamos
            entonces actualizamos -update() y pintamos -repaint(). Finalmente reiniciamos el valor de 'delta'
            disminuyendo su valor en 1.
             */
            if (delta >= 1) {
                update();
                repaint();
                delta--;

                // Se incrementa en 1 la cantidad de veces que se ha pintado en 1 segundo.
                drawCount++;
            }

            // 'if statement' para chequear los FPS.
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        lp.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        lp.draw(g2);
        scorePanel.draw(g2);
    }
}
