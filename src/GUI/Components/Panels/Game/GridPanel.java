package GUI.Components.Panels.Game;

import Components.Tetros.Primitives.Tetromino;
import Components.Tetros.TetrominoFactory;
import GUI.Properties.TColor;
import GUI.Properties.TStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

/**
 * Clase donde van las matrices y básicamente el juego; donde se anima.
 * <p>
 * El tablero tiene {@link #ROWS} filas, de las cuales las primeras {@link #HIDDEN_ROWS} están por encima del
 * marco y no se dibujan: sirven de amortiguador para que una pieza recién aparecida (y las rotaciones que
 * intentan salir del marco) no disparen el final de partida de inmediato.
 * <p>
 * Todo el estado del tablero lo usa el hilo del juego para escribir y el hilo de Swing (EDT) para leer al
 * pintar. Como son dos hilos distintos, cada acceso pasa por {@link #boardLock}, así no se dibuja nunca una
 * matriz a medio actualizar.
 */
public class GridPanel extends JPanel {

    /**
     * Cantidad de columnas del tablero.
     */
    public static final int COLS = 10;

    /**
     * Cantidad de filas con las que se inicializa el tablero. Las 4 primeras no se dibujan.
     */
    public static final int ROWS = 24;

    /**
     * Filas que quedan por encima del marco y no se dibujan. Son el lugar donde nace la pieza activa.
     */
    public static final int HIDDEN_ROWS = 4;

    /**
     * Puntos que suman las tetrominos segun cuantas lineas completan al apoyarse.
     */
    private static final int[] LINE_POINTS = {0, 100, 300, 500, 800};

    /**
     * Milisegundos que la pieza tiene que seguir sin poder bajar antes de apoyarse sola. Ese margen es lo
     * que permite mover y rotar una pieza que ya llegó al suelo, en vez de quedar clavada al instante.
     */
    private static final long LOCK_DELAY = 500;

    /**
     * Puntos por celda que baja el <i>soft drop</i> y por celda que baja el <i>hard drop</i>.
     */
    private static final int SOFT_DROP_POINTS = 1;
    private static final int HARD_DROP_POINTS = 2;

    /**
     * Margen que se deja entre el borde del panel y el tablero.
     */
    private static final double MARGIN = 10;

    /**
     * Espacio que queda entre celda y celda, para que se vean separadas.
     */
    private static final double CELL_GAP = 5;

    /**
     * Cuanto se hunde el contorno de la pieza fantasma dentro de la celda.
     */
    private static final double GHOST_INSET = 5;

    /**
     * Grosor del contorno de la pieza fantasma. Mas fino que el de la grilla, para que se distinga.
     */
    private static final int GHOST_STROKE = 2;

    /**
     * <code> fieldArray </code> tiene la responsabilidad de iniciar los valores (en 0) de la matriz
     * donde van a estar los bloques que forman a los tetrominos.
     * <p>
     * El valor de cada celda es 0 si esta vacia, o el numero del tetromino que la ocupa (1 a 7), que es
     * justamente el mismo número que las celdas de la forma de la pieza llevan escrito.
     */
    private final int[][] fieldArray = new int[ROWS][COLS];

    private Rectangle2D.Double[][] celdas;

    /**
     * Tetromino que está cayendo ahora mismo, o <code>null</code> si todavía no hay ninguno.
     */
    private Tetromino actualTetromino;

    /**
     * Próximo tetromino que va a aparecer. Se mantiene aparte para poder dibujarlo como vista previa.
     */
    private Tetromino nextTetromino;

    /**
     * Saca los tetrominos en el orden en que van a aparecer.
     */
    private final TetrominoFactory factory = new TetrominoFactory();

    /**
     * Columna del tablero donde está la esquina superior izquierda de la caja de la pieza activa.
     */
    private int pieceX;

    /**
     * Fila del tablero donde está la esquina superior izquierda de la caja de la pieza activa.
     */
    private int pieceY;

    private int score = 0;
    private int lines = 0;

    /**
     * Nivel actual. No baja nunca: el juego se pone más rápido y vale más puntos.
     */
    private int level = 1;

    /**
     * Nivel con el que arrancó la partida, para que el jugador pueda elegir uno más alto desde el menú.
     */
    private int startLevel = 1;

    private boolean gameOver = false;

    /**
     * Acumulador de milisegundos hasta la próxima bajada automática por gravedad.
     */
    private long dropAccumulator = 0;

    /**
     * Acumulador de milisegundos que la pieza lleva apoyada sin poder seguir bajando.
     */
    private long lockAccumulator = 0;

    /**
     * Verdadero cuando la pieza ya no puede bajar mas y esta esperando que se cumpla el lock delay.
     */
    private boolean resting = false;

    /**
     * Cierra el acceso al estado del tablero entre el hilo del juego y el hilo de Swing.
     */
    private final Object boardLock = new Object();

    public GridPanel() {
        setBackground(TColor.BLACK202);
    }

    /*
     * metodo que va a llenar el arreglo de rectangulos, que son basicamente los que se van a dibujar
     */

    public void fillCeldas() {
        if (celdas != null && celdas.length == ROWS && celdas[0].length == COLS
                && lastWidth == getWidth() && lastHeight == getHeight()) {
            return;
        }
        lastWidth = getWidth();
        lastHeight = getHeight();

        celdas = new Rectangle2D.Double[ROWS][COLS];

        // El tablero ocupa el panel menos un margen, para que el marco quede por fuera de las celdas.
        double usableWidth = getWidth() - (double) MARGIN * 2;
        double usableHeight = getHeight() - (double) MARGIN * 2;
        double cellWidth = usableWidth / COLS;
        double cellHeight = usableHeight / ROWS;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                celdas[i][j] = new Rectangle2D.Double(
                        MARGIN + j * cellWidth,
                        MARGIN + i * cellHeight,
                        cellWidth - CELL_GAP,
                        cellHeight - CELL_GAP
                );
            }
        }
    }

    /**
     * Ultimas medidas con las que se construyeron los rectangulos de {@link #celdas}, para no tener que
     * recalcularlos en cada fotograma.
     */
    private int lastWidth = -1;
    private int lastHeight = -1;

    /**
     * Deja el tablero como recién salido de fábrica y arranca una partida en el nivel indicado.
     *
     * @param startLevel Nivel con el que comienza la partida.
     */
    public void reset(int startLevel) {
        synchronized (boardLock) {
            this.startLevel = Math.max(1, Math.min(20, startLevel));
            this.level = this.startLevel;

            for (int i = 0; i < ROWS; i++) {
                Arrays.fill(fieldArray[i], 0);
            }

            score = 0;
            lines = 0;
            gameOver = false;
            dropAccumulator = 0;
            lockAccumulator = 0;
            resting = false;

            factory.reset();
            nextTetromino = factory.next();
            actualTetromino = null;

            spawnNext();
        }
        repaint();
    }

    /*
     *
     * este seria el metodo para copiar el tetromino actual que esta corriendo
     *
     */

    public void addTetromino(Tetromino e) {
        synchronized (boardLock) {
            actualTetromino = e;
            spawn(e);
        }
    }

    /**
     * Coloca una pieza en su posicion de aparicion. Si ya no cabe ahi, se termino la partida.
     * <p>
     * La pieza nace en la primera fila visible del tablero, y no mas arriba: las filas ocultas estan para
     * que una rotacion contra el techo no sea una colision, no para esconder la pieza. Asi el jugador ve
     * de entrada la pieza que le toco.
     */
    private void spawn(Tetromino tetromino) {
        actualTetromino = tetromino;
        if (tetromino == null) {
            return;
        }

        byte[][] shape = tetromino.getActualShape();
        pieceX = (COLS - shape.length) / 2;
        pieceY = HIDDEN_ROWS;

        dropAccumulator = 0;
        lockAccumulator = 0;
        resting = false;

        if (!fits(shape, pieceX, pieceY)) {
            gameOver = true;
            actualTetromino = null;
        }
    }

    /**
     * Saca el próximo tetromino de la bolsa y lo deja siendo el activo.
     */
    private void spawnNext() {
        spawn(nextTetromino);
        nextTetromino = factory.next();
    }

    /**
     * Dice si una forma cabría en el tablero sin pisar bloques ni salirse.
     * Las filas de índice negativo se consideran válidas: son las que quedan dentro del amortiguador.
     */
    private boolean fits(byte[][] shape, int x, int y) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 0) {
                    continue;
                }
                int nx = x + j;
                int ny = y + i;
                if (nx < 0 || nx >= COLS || ny >= ROWS) {
                    return false;
                }
                if (ny >= 0 && fieldArray[ny][nx] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     *
     * los proximos metodos son para mover el tetromino activo dentro de la matriz checkArray, el moveDown ya va junto
     * con el movimiento, se tiene que llamar en el hilo del juego, y los otros son para los eventos de teclado
     *
     */

    /**
     * Baja la pieza una fila si puede. Devuelve si de verdad bajó.
     */
    public boolean moveDown() {
        synchronized (boardLock) {
            if (actualTetromino == null || gameOver) {
                return false;
            }
            if (!fits(actualTetromino.getActualShape(), pieceX, pieceY + 1)) {
                resting = true;
                lockAccumulator = 0;
                return false;
            }
            pieceY++;
            resting = false;
            lockAccumulator = 0;
            return true;
        }
    }

    /**
     * Mueve la pieza una columna a la izquierda, si hay lugar.
     */
    public void moveToTheLeft() {
        synchronized (boardLock) {
            if (actualTetromino == null || gameOver) {
                return;
            }
            if (fits(actualTetromino.getActualShape(), pieceX - 1, pieceY)) {
                pieceX--;
                resting = false;
                lockAccumulator = 0;
            }
        }
    }

    /**
     * Mueve la pieza una columna a la derecha, si hay lugar.
     */
    public void moveToTheRight() {
        synchronized (boardLock) {
            if (actualTetromino == null || gameOver) {
                return;
            }
            if (fits(actualTetromino.getActualShape(), pieceX + 1, pieceY)) {
                pieceX++;
                resting = false;
                lockAccumulator = 0;
            }
        }
    }

    /**
     * Baja la pieza una fila. Es el movimiento que hace el jugador al mantener la flecha abajo.
     */
    public void softDrop() {
        synchronized (boardLock) {
            if (moveDown()) {
                score += SOFT_DROP_POINTS;
            }
        }
    }

    /**
     * Deja caer la pieza de golpe hasta el suelo y la apoya al instante.
     */
    public void hardDrop() {
        synchronized (boardLock) {
            if (actualTetromino == null || gameOver) {
                return;
            }
            int dropped = 0;
            while (fits(actualTetromino.getActualShape(), pieceX, pieceY + 1)) {
                pieceY++;
                dropped++;
            }
            score += dropped * HARD_DROP_POINTS;
            resting = false;
            lockAccumulator = 0;
            lockPiece();
        }
    }

    /**
     * Rota la pieza activa. Si choca, se prueban los tests del SRS en orden hasta encontrar uno que no
     * choque; si ninguno sirve, la pieza se queda como estaba.
     *
     * @param clockwise Si {@code true} rota en sentido horario.
     */
    public void rotate(boolean clockwise) {
        synchronized (boardLock) {
            if (actualTetromino == null || gameOver) {
                return;
            }
            // El tetromino O es un cuadrado: rotarlo no cambia nada, así que ni se intenta.
            if (actualTetromino.getTypeId() == 4) {
                return;
            }

            Tetromino tetromino = actualTetromino;

            // La fila de la tabla SRS se elige según la rotación de la que se sale, no según la de destino.
            int from = tetromino.getR();
            tetromino.rotateTetromino(clockwise);

            byte[][] candidate = tetromino.getRotatedShape();
            for (int test = 0; test < 5; test++) {
                byte[] offset = tetromino.getOffset(from, test);
                int nx = pieceX + offset[0];
                int ny = pieceY + offset[1];
                if (fits(candidate, nx, ny)) {
                    pieceX = nx;
                    pieceY = ny;
                    tetromino.applyRotation();
                    resting = false;
                    lockAccumulator = 0;
                    return;
                }
            }

            // Ningún test sirvió: se deshace el intento.
            tetromino.cancelRotation();
        }
    }

    /*
     * metodo para cuando una fila se llene se limpie
     */
    public int clearRow() {
        synchronized (boardLock) {
            int cleared = 0;
            for (int row = ROWS - 1; row >= 0; row--) {
                if (!isRowFull(row)) {
                    continue;
                }
                for (int r = row; r > 0; r--) {
                    System.arraycopy(fieldArray[r - 1], 0, fieldArray[r], 0, COLS);
                }
                Arrays.fill(fieldArray[0], 0);
                cleared++;
                // La fila que quedó en 'row' es la que estaba arriba, así que hay que volver a mirarla.
                row++;
            }
            return cleared;
        }
    }

    /**
     * Dice si todas las celdas de una fila están ocupadas.
     */
    private boolean isRowFull(int row) {
        for (int col = 0; col < COLS; col++) {
            if (fieldArray[row][col] == 0) {
                return false;
            }
        }
        return true;
    }

    /*
     *
     * metodo que va insertar el tetromino en la matriz fieldArray, se debe llamar en el hilo principal cuando pase el
     * tiempo especifico
     *
     */
    public void insertTetromino() {
        synchronized (boardLock) {
            lockPiece();
        }
    }

    /**
     * Apoya la pieza activa: escribe sus celdas en el tablero, borra las filas completas y saca la
     * siguiente pieza.
     */
    private void lockPiece() {
        if (actualTetromino == null) {
            return;
        }

        byte[][] shape = actualTetromino.getActualShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 0) {
                    continue;
                }
                int nx = pieceX + j;
                int ny = pieceY + i;
                if (ny >= 0 && ny < ROWS && nx >= 0 && nx < COLS) {
                    fieldArray[ny][nx] = shape[i][j];
                }
            }
        }

        int cleared = clearRow();
        if (cleared > 0) {
            lines += cleared;
            score += LINE_POINTS[cleared] * level;
            level = startLevel + (lines / 10);
        }

        spawnNext();
    }

    /**
     * Fila más baja a la que puede caer la pieza activa sin chocar. Sirve para dibujar la pieza fantasma.
     */
    private int ghostRow() {
        if (actualTetromino == null) {
            return pieceY;
        }
        byte[][] shape = actualTetromino.getActualShape();
        int y = pieceY;
        while (fits(shape, pieceX, y + 1)) {
            y++;
        }
        return y;
    }

    /**
     * Milisegundos que tarda la pieza en bajar una fila sola, según el nivel.
     * <p>
     * Aplica la fórmula de las Guideline Tetris: en el nivel 1 la pieza tarda un segundo en bajar, y a
     * partir de ahí baja cada vez más rápido, hasta un mínimo de 20 milisegundos.
     */
    private long dropInterval() {
        double seconds = Math.pow(0.8 - ((level - 1) * 0.007), level - 1);
        return (long) Math.max(20, seconds * 1000);
    }

    /*
     *
     * metodo donde sucede la magia, y son dibujados los tetrominos de acuerdo a su posicion dentro de las dos matrices
     *
     */
    public void drawfieldArray(Graphics2D g2) {
        synchronized (boardLock) {
            // Los bloques que ya estan apoyados en el tablero.
            for (int i = HIDDEN_ROWS; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    if (fieldArray[i][j] != 0) {
                        g2.setColor(TColor.tetrominoColor(fieldArray[i][j]));
                        g2.fill(celdas[i][j]);
                    }
                }
            }
        }

        // aqui se dibuja el borde de las celdas
        g2.setColor(Color.GRAY);
        for (int i = HIDDEN_ROWS; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                g2.draw(celdas[i][j]);
            }
        }

        synchronized (boardLock) {
            if (actualTetromino == null) {
                return;
            }
            byte[][] shape = actualTetromino.getActualShape();

            /*
             * La pieza fantasma marca donde se apoyaria la pieza si el jugador la soltara ahora.
             * Se dibuja despues de la grilla y un poco mas adentro de la celda: si se dibujara justo
             * sobre el borde, la linea gris de la celda taparia el contorno y no se veria nada.
             */
            int ghost = ghostRow();
            if (ghost != pieceY) {
                g2.setColor(TColor.BRIGHT_GREEN);
                g2.setStroke(new BasicStroke(GHOST_STROKE));
                for (int i = 0; i < shape.length; i++) {
                    for (int j = 0; j < shape[i].length; j++) {
                        if (shape[i][j] == 0) {
                            continue;
                        }
                        int row = ghost + i;
                        if (row >= HIDDEN_ROWS && row < ROWS) {
                            Rectangle2D.Double cell = celdas[row][pieceX + j];
                            g2.draw(new Rectangle2D.Double(
                                    cell.getX() + GHOST_INSET,
                                    cell.getY() + GHOST_INSET,
                                    cell.getWidth() - GHOST_INSET * 2,
                                    cell.getHeight() - GHOST_INSET * 2
                            ));
                        }
                    }
                }
            }

            // La pieza activa, por encima de todo lo demas.
            g2.setStroke(new BasicStroke(TStyle.basicStrokeBordersWidth));
            g2.setColor(actualTetromino.getTetroColor());
            for (int i = 0; i < shape.length; i++) {
                for (int j = 0; j < shape[i].length; j++) {
                    if (shape[i][j] == 0) {
                        continue;
                    }
                    int row = pieceY + i;
                    if (row >= HIDDEN_ROWS && row < ROWS) {
                        g2.fill(celdas[row][pieceX + j]);
                    }
                }
            }
        }
    }

    /**
     * Avanza el juego un paso. La llama el hilo del juego, nunca el de Swing.
     *
     * @param deltaMs Milisegundos que pasaron desde el paso anterior.
     */
    public void update(long deltaMs) {
        synchronized (boardLock) {
            if (actualTetromino == null || gameOver) {
                return;
            }

            if (!resting) {
                dropAccumulator += deltaMs;
                long interval = dropInterval();
                if (dropAccumulator >= interval) {
                    // Se descuenta el intervalo entero, y no sólo una vez, por si el paso fue largo.
                    dropAccumulator -= interval;
                    moveDown();
                }
            } else {
                lockAccumulator += deltaMs;
                if (lockAccumulator >= LOCK_DELAY) {
                    lockPiece();
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        draw(g2);
    }

    public void draw(Graphics2D g2D) {
        /*
         * aqui viene la fase de llenar el array de rectangulos para luego volverlos a dibujar
         */
        fillCeldas();

        /*
         * El marco abraza justamente la parte visible del tablero: las filas ocultas quedan por encima,
         * porque no son jugables y no tiene sentido dibujarles un borde.
         */
        double gridTop = celdas[HIDDEN_ROWS][0].getMinY();
        double gridBottom = celdas[ROWS - 1][COLS - 1].getMaxY();
        double gridLeft = celdas[HIDDEN_ROWS][0].getMinX();
        double gridRight = celdas[HIDDEN_ROWS][COLS - 1].getMaxX();

        g2D.setColor(TColor.BRIGHT_GREEN);
        g2D.setStroke(new BasicStroke(TStyle.basicStrokeBordersWidth));
        g2D.drawRoundRect(
                (int) (gridLeft - CELL_GAP / 2),
                (int) (gridTop - CELL_GAP / 2),
                (int) ((gridRight - gridLeft) + CELL_GAP),
                (int) ((gridBottom - gridTop) + CELL_GAP),
                TStyle.rectangleRoundBordersAmount,
                TStyle.rectangleRoundBordersAmount
        );

        drawfieldArray(g2D);
    }

    public int[][] getFieldArray() {
        return fieldArray;
    }

    public boolean isGameOver() {
        synchronized (boardLock) {
            return gameOver;
        }
    }

    public int getScore() {
        synchronized (boardLock) {
            return score;
        }
    }

    public int getLines() {
        synchronized (boardLock) {
            return lines;
        }
    }

    public int getLevel() {
        synchronized (boardLock) {
            return level;
        }
    }

    /**
     * Vista previa del próximo tetromino, o <code>null</code> si la partida todavía no empezó.
     */
    public Tetromino getNextTetromino() {
        synchronized (boardLock) {
            return nextTetromino;
        }
    }
}
