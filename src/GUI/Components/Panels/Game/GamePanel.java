package GUI.Components.Panels.Game;

import GUI.Properties.TColor;
import GUI.Properties.TStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class GamePanel extends JPanel implements Runnable {

    /**
     * Cantidad de FPS que tendrá el juego.
     */
    private static final int FPS = 60;

    /**
     * Milisegundos que un fotograma puede durar como máximo. Si la máquina se traba, el juego saltea
     * fotogramas en lugar de intentar recuperar todos y volver a bloquearse.
     */
    private static final long MAX_FRAME = 100;

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

    /**
     * Verdadero mientras el bucle de juego tiene que seguir corriendo.
     */
    private volatile boolean running = false;

    /**
     * Verdadero cuando el jugador pausó la partida.
     */
    private volatile boolean paused = false;

    /**
     * Teclas que el jugador tiene apretadas en este momento. Sirve para distinguir una pulsación de una
     * tecla mantenida, que es lo que permite mover la pieza rápido hacia un lado.
     */
    private final Set<Integer> pressedKeys = ConcurrentHashMap.newKeySet();

    /**
     * Fotogramas que faltan para repetir el movimiento horizontal mientras la tecla siga apretada.
     * <p>
     * Arranca rápido para que la pieza llegue enseguida al borde, y después se frena para que el jugador
     * pueda dejarla en la columna exacta. Es compartido entre los dos hilos, asi que atómico.
     */
    private final AtomicInteger dasTimer = new AtomicInteger();

    /**
     * Fotogramas que tienen que pasar entre movimiento y movimiento con la tecla recién apretada.
     */
    private static final int ARR_DELAY = 6;

    /**
     * Fotogramas que tienen que pasar entre movimiento y movimiento con la tecla mantenida.
     */
    private static final int DAS_DELAY = 3;

    /**
     * Nivel con el que arranca la partida.
     */
    private int startLevel = 1;

    /**
     * Jugadas que el jugador pidió desde el hilo de Swing y que todavía no se aplicaron. Swing entrega las
     * teclas en su propio hilo, así que no se tocan el tablero directamente: se encolan y el hilo del
     * juego las aplica en su próximo paso.
     */
    private final Deque<Integer> pendingInputs = new ArrayDeque<>();

    /**
     * Ancho que se le pide a la ventana. Es la suma exacta de los dos paneles, para que el
     * <i>BorderLayout</i> no los recorte ni los superponga.
     */
    public static final int WIDTH = 650;

    /**
     * Alto que se le pide a la ventana.
     */
    public static final int HEIGHT = 700;

    public GamePanel() {
        setBackground(TColor.BLACK202);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // LEFT PANEL
        lp = new GridPanel();
        lp.setPreferredSize(new Dimension(380, HEIGHT));

        // RIGHT PANEL
        scorePanel = new ScorePanel();
        scorePanel.setSource(lp);
        scorePanel.setPreferredSize(new Dimension(WIDTH - 380, HEIGHT));

        /*
         * La capa de avisos se agrega antes que los dos paneles, y no después, porque Swing no pinta los
         * hijos en el orden en que se agregan sino al revés: el último que se agregó se pinta primero y
         * el primero se pinta de último, que es el que queda arriba. Agregándola primero, entonces, es la
         * única forma de que el aviso tape el tablero sin tener que poner una ventana aparte encima.
         */
        add(overlay);

        add(lp, BorderLayout.WEST);
        add(scorePanel, BorderLayout.EAST);

        setFocusable(true);
        installKeyListener();
    }

    /**
     * El <i>BorderLayout</i> le daría a la capa de avisos el hueco del medio, que acá no existe porque los
     * dos paneles de al lado ya ocupan todo el ancho. Por eso su tamaño se lo ponemos a mano.
     */
    @Override
    public void doLayout() {
        super.doLayout();
        overlay.setBounds(0, 0, getWidth(), getHeight());
    }

    /**
     * Conecta las teclas del juego. El panel tiene que tener el foco para recibirlas.
     */
    private void installKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                boolean firstPress = !pressedKeys.contains(code);
                pressedKeys.add(code);

                if (code == KeyEvent.VK_P || code == KeyEvent.VK_ESCAPE) {
                    if (firstPress) {
                        togglePause();
                    }
                    return;
                }

                if (lp.isGameOver()) {
                    if (firstPress && code == KeyEvent.VK_R) {
                        restart();
                    }
                    return;
                }

                if (paused) {
                    return;
                }

                // Las teclas de juego no deben disparar el beep de foco de Swing.
                e.consume();

                if (firstPress) {
                    switch (code) {
                        case KeyEvent.VK_LEFT -> {
                            dasTimer.set(ARR_DELAY);
                            enqueue(MOVE_LEFT);
                        }
                        case KeyEvent.VK_RIGHT -> {
                            dasTimer.set(ARR_DELAY);
                            enqueue(MOVE_RIGHT);
                        }
                        case KeyEvent.VK_DOWN -> enqueue(SOFT_DROP);
                        case KeyEvent.VK_UP, KeyEvent.VK_X -> enqueue(ROTATE_CW);
                        case KeyEvent.VK_Z, KeyEvent.VK_CONTROL -> enqueue(ROTATE_CCW);
                        case KeyEvent.VK_SPACE -> enqueue(HARD_DROP);
                        default -> {
                        }
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                pressedKeys.remove(e.getKeyCode());
                dasTimer.set(0);
            }
        });

        // Al hacer clic en cualquier parte del panel se le devuelve el foco, asi el teclado nunca queda
        // muerto porque el jugador haya tocado la ventana.
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow();
            }
        });

        setFocusTraversalKeysEnabled(false);
    }

    /**
     * Jugadas que el juego sabe aplicar.
     */
    private static final int MOVE_LEFT = 0;
    private static final int MOVE_RIGHT = 1;
    private static final int SOFT_DROP = 2;
    private static final int ROTATE_CW = 3;
    private static final int ROTATE_CCW = 4;
    private static final int HARD_DROP = 5;

    /**
     * Encola una jugada para que la aplique el hilo del juego.
     */
    private void enqueue(int action) {
        synchronized (pendingInputs) {
            // Un hard drop o una rotación no tienen sentido repetidos 400 veces por segundo.
            if (pendingInputs.size() < 32) {
                pendingInputs.add(action);
            }
        }
    }

    /**
     * Alterna la pausa del juego.
     */
    private void togglePause() {
        if (!lp.isGameOver()) {
            paused = !paused;
            refreshOverlay();
        }
    }

    /**
     * Arranca una partida nueva.
     * <p>
     * Vuelve a levantar el bucle porque con la partida terminada el hilo ya habia salido. Se lo espera un
     * poco antes de arrancar el nuevo para que no queden dos hilos updating el mismo tablero.
     */
    private void restart() {
        paused = false;
        stopGameThread();
        joinGameThread();
        lp.reset(startLevel);
        startGameThread();
        refreshOverlay();
    }

    /**
     * Espera a que el hilo de juego termine, con un tope para no quedar colgados si algo se rompe.
     */
    private void joinGameThread() {
        Thread old = gameThread;
        if (old == null) {
            return;
        }
        try {
            old.join(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Este método inicia el thread del Panel principal (GamePanel).
    public void startGameThread() {
        if (running) {
            return;
        }
        lp.reset(startLevel);
        running = true;
        gameThread = new Thread(this);
        gameThread.setDaemon(true);
        gameThread.start();
    }

    /**
     * Define el nivel con el que arranca la partida. Hay que llamarlo antes de
     * {@link #startGameThread()}.
     */
    public void setStartLevel(int level) {
        this.startLevel = Math.max(1, Math.min(20, level));
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
        long frameTime = 1000000000L / FPS; // Esto no es más que 0.016 seg.
        long lastTime = System.nanoTime();
        long currentTime;

        while (running) {

            // Se toma el valor del tiempo actual en nanosegundos.
            currentTime = System.nanoTime();

            /*
            Se resta el tiempo actual (currentTime) con el último tiempo registrado (lastTime), o sea, cuánto tiempo
            ha pasado. Ese es el 'delta' del fotograma, y define cuánto se mueve el juego.
             */
            long delta = currentTime - lastTime;

            // El tiempo actual se guarda en el último tiempo registrado.
            lastTime = currentTime;

            /*
            Cuando 'delta' llega al intervalo de fotograma, actualizamos -update() y pintamos -repaint().
            Si el paso anterior fue más corto que el intervalo, no hay nada que pintar todavía.
             */
            if (delta >= frameTime) {
                update(Math.min(delta / 1000000L, MAX_FRAME));
                repaint();
            }

            /*
            Se espera lo que falta para el próximo fotograma en vez de repetir el bucle a toda velocidad.
            Girar el bucle sin parar quema un núcleo entero de la CPU al pedo: el juego no va más rápido por
            calcular más veces por segundo, y además deja la máquina libre para el navegador y todo lo demás.
             */
            long remaining = frameTime - delta;
            if (remaining > 0) {
                try {
                    Thread.sleep(remaining / 1000000L, (int) (remaining % 1000000L));
                } catch (InterruptedException e) {
                    // stopGameThread() corta el bucle por aca.
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    /**
     * Aplica las jugadas pendientes y le pasa el tiempo transcurrido al tablero.
     * La corre entre el hilo del juego; el tablero se encarga de su propio bloqueo.
     *
     * @param deltaMs Milisegundos que pasaron desde el paso anterior.
     */
    public void update(long deltaMs) {
        if (!paused) {
            applyPendingInputs();
            applyHeldKeys();
            lp.update(deltaMs);
        }

        // Si el juego terminó, no hace falta seguir pidiendo fotogramas, y el aviso queda en pantalla.
        if (lp.isGameOver()) {
            running = false;
        }

        refreshOverlay();
    }

    /**
     * Aplica en el hilo del juego todas las jugadas que el jugador haya pedido.
     */
    private void applyPendingInputs() {
        int action;
        do {
            action = -1;
            synchronized (pendingInputs) {
                if (!pendingInputs.isEmpty()) {
                    action = pendingInputs.poll();
                }
            }
            if (action != -1) {
                applyAction(action);
            }
        } while (action != -1);
    }

    /**
     * Ejecuta una jugada sobre el tablero.
     */
    private void applyAction(int action) {
        switch (action) {
            case MOVE_LEFT -> lp.moveToTheLeft();
            case MOVE_RIGHT -> lp.moveToTheRight();
            case SOFT_DROP -> lp.softDrop();
            case ROTATE_CW -> lp.rotate(true);
            case ROTATE_CCW -> lp.rotate(false);
            case HARD_DROP -> lp.hardDrop();
            default -> {
            }
        }
    }

    /**
     * Repite el movimiento horizontal mientras la tecla siga apretada.
     * <p>
     * El primer movimiento ya lo aplicó {@code keyPressed}, así que acá sólo se cuentan los fotogramas y
     * se repiten cuando corresponde.
     */
    private void applyHeldKeys() {
        boolean left = pressedKeys.contains(KeyEvent.VK_LEFT);
        boolean right = pressedKeys.contains(KeyEvent.VK_RIGHT);

        if (!left && !right) {
            dasTimer.set(0);
            return;
        }

        if (dasTimer.getAndDecrement() > 0) {
            return;
        }

        if (left) {
            lp.moveToTheLeft();
        } else {
            lp.moveToTheRight();
        }
        dasTimer.set(DAS_DELAY);
    }

    /**
     * Capa que se pinta encima de los dos paneles para tapar la partida y mostrar el aviso de pausa o de
     * fin de partida.
     * <p>
     * Va como componente hijo, y no dibujado en el <code>paintComponent</code> del panel, porque Swing
     * pinta primero el panel y después sus hijos: los dos paneles de al lado son opacos, así que cualquier
     * cosa que se dibuje antes que ellos queda tapada. Se agrega antes que ellos, porque el orden de
     * pintado es al revés (ver el constructor).
     */
    private final JComponent overlay = new OverlayLayer();

    /**
     * Capa transparente que sólo pinta algo cuando hay un aviso que mostrar.
     */
    private final class OverlayLayer extends JComponent {

        /**
         * Tinta del velo que tapa la partida.
         */
        private static final Color SHADE = new Color(0, 0, 0, 170);

        /**
         * Se hace visible con {@link #setVisible(boolean)}, no con un flag propio: si la capa no tiene nada
         * que pintar no molesta en absoluto, y Swing ya se encarga de pedir el repintado cuando aparece.
         */
        OverlayLayer() {
            setOpaque(false);
        }

        /**
         * La capa dice que no tiene nada donde se la pueda hacer clic.
         * <p>
         * Está estirada encima de todo, así que si aceptara los clics se los quedaría y el jugador no
         * podría devolverle el foco al panel pulsando en cualquier parte de la ventana.
         */
        @Override
        public boolean contains(int x, int y) {
            return false;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            String message = overlayMessage();
            if (message == null) {
                return;
            }

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(SHADE);
            g2.fillRect(0, 0, getWidth(), getHeight());

            /*
             * El aviso se centra sobre el tablero y no sobre la ventana entera: centrado en la ventana
             * el texto queda partiendo el borde entre los dos paneles, y lo que se está tapando es el
             * tablero, que es adonde mira el jugador.
             */
            int centerX = lp.getX() + lp.getWidth() / 2;

            g2.setColor(TColor.BRIGHT_GREEN);
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            int messageWidth = g2.getFontMetrics().stringWidth(message);
            g2.drawString(message, centerX - messageWidth / 2, getHeight() / 2 - 20);

            g2.setFont(new Font("Arial", Font.PLAIN, 16));
            String hint = lp.isGameOver() ? "Pulsá R para volver a empezar" : "Pulsá P para seguir";
            int hintWidth = g2.getFontMetrics().stringWidth(hint);
            g2.drawString(hint, centerX - hintWidth / 2, getHeight() / 2 + 20);
            g2.dispose();
        }
    }

    /**
     * Aviso que hay que mostrar por encima de la partida, o <code>null</code> si se está jugando normal.
     */
    private String overlayMessage() {
        if (lp.isGameOver()) {
            return "GAME OVER";
        }
        return paused ? "PAUSA" : null;
    }

    /**
     * Muestra u oculta la capa de avisos, y repinta el panel para que el cambio se vea enseguida.
     */
    private void refreshOverlay() {
        boolean visible = overlayMessage() != null;
        if (overlay.isVisible() != visible) {
            overlay.setVisible(visible);
            repaint();
        }
    }

    /**
     * Detiene el hilo del juego.
     */
    public void stopGameThread() {
        running = false;
        if (gameThread != null) {
            gameThread.interrupt();
        }
    }
}
