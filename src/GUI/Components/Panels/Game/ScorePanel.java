package GUI.Components.Panels.Game;

import Components.Tetros.Primitives.Tetromino;
import GUI.Properties.TColor;
import GUI.Properties.TStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Panel de la derecha: puntaje, líneas, nivel, la próxima pieza y un resumen de las teclas.
 * Toma los números del {@link GridPanel} que tiene asignado en {@link #setSource(GridPanel)}, de modo que
 * no guarda una copia del estado del juego: siempre dibuja lo que el tablero tiene en ese momento.
 */
public class ScorePanel extends JPanel {

    /**
     * Tablero del que salen los números que se muestran.
     */
    private GridPanel source;

    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 18);
    private static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final Font VALUE_FONT = new Font("Monospaced", Font.BOLD, 26);
    private static final Font SMALL_FONT = new Font("Arial", Font.PLAIN, 12);

    /**
     * Pintura con la que se recuadra la pieza que viene.
     */
    private static final Color NEXT_BACKGROUND = new Color(32, 32, 32, 180);

    /**
     * Margen que se deja entre el borde del panel y las cajas de adentro.
     */
    private static final int MARGIN = 10;

    public ScorePanel() {
        setBackground(TColor.BLACK202);
    }

    /**
     * Indica de qué tablero leer los datos que se pintan.
     */
    public void setSource(GridPanel source) {
        this.source = source;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        draw(g2);
    }

    public void draw(Graphics2D g2D) {
        int w = getWidth();
        int h = getHeight();

        g2D.setColor(TColor.BRIGHT_GREEN);
        g2D.setStroke(new BasicStroke(TStyle.basicStrokeBordersWidth));
        g2D.drawRoundRect(
                MARGIN,
                MARGIN,
                w - MARGIN * 2,
                h - MARGIN * 2,
                TStyle.rectangleRoundBordersAmount,
                TStyle.rectangleRoundBordersAmount
        );

        // La caja de arriba tiene el puntaje, las lineas y el nivel.
        int boxLeft = MARGIN + 7;
        int boxWidth = w - (MARGIN + 7) * 2;
        int statsTop = MARGIN + 12;
        int statsHeight = (int) (h * 0.27);

        g2D.drawRoundRect(
                boxLeft,
                statsTop,
                boxWidth,
                statsHeight,
                TStyle.rectangleRoundBordersAmount,
                TStyle.rectangleRoundBordersAmount
        );

        int score = (source == null) ? 0 : source.getScore();
        int lines = (source == null) ? 0 : source.getLines();
        int level = (source == null) ? 1 : source.getLevel();

        drawStat(g2D, "PUNTAJE", String.valueOf(score), boxLeft, statsTop, statsHeight / 3);
        drawStat(g2D, "LINEAS", String.valueOf(lines), boxLeft, statsTop + statsHeight / 3, statsHeight / 3);
        drawStat(g2D, "NIVEL", String.valueOf(level), boxLeft, statsTop + 2 * (statsHeight / 3), statsHeight / 3);

        // La caja del medio muestra la proxima pieza. Se la deja libre la parte de abajo para las teclas.
        int nextTop = statsTop + statsHeight + 20;
        int nextHeight = (int) (h * 0.34);

        g2D.drawRoundRect(
                boxLeft,
                nextTop,
                boxWidth,
                nextHeight,
                TStyle.rectangleRoundBordersAmount,
                TStyle.rectangleRoundBordersAmount
        );

        g2D.setColor(TColor.LABEL);
        g2D.setFont(LABEL_FONT);
        g2D.drawString("SIGUIENTE", boxLeft + 12, nextTop + 22);

        if (source != null) {
            Tetromino next = source.getNextTetromino();
            if (next != null) {
                drawNextPiece(g2D, next, boxLeft, nextTop + 30, boxWidth, nextHeight - 40);
            }
        }

        // Abajo, un resumen de las teclas para que el jugador no tenga que adivinarlas.
        g2D.setColor(TColor.LABEL);
        g2D.setFont(SMALL_FONT);
        String[] help = {
                "FLECHAS: mover",
                "ABAJO: bajar",
                "ESPACIO: soltar",
                "ARRIBA / X: rotar",
                "Z: rotar al reves",
                "P: pausa",
                "R: reiniciar",
        };
        int helpY = nextTop + nextHeight + 30;
        for (String line : help) {
            // Si no entra, no se dibuja: mejor que se corte antes que salirse del panel.
            if (helpY > h - MARGIN) {
                break;
            }
            g2D.drawString(line, boxLeft, helpY);
            helpY += 17;
        }
    }

    /**
     * Dibuja un rotulo con su valor, uno encima del otro.
     *
     * @param y     Coordenada vertical del principio del bloque.
     * @param block Alto que se le reserva a este bloque, para poder repartir los tres sin superponerse.
     */
    private void drawStat(Graphics2D g2D, String label, String value, int x, int y, int block) {
        g2D.setColor(TColor.LABEL);
        g2D.setFont(LABEL_FONT);
        g2D.drawString(label, x + 12, y + 24);

        g2D.setColor(TColor.BRIGHT_GREEN);
        g2D.setFont(VALUE_FONT);
        g2D.drawString(value, x + 12, y + 52);
    }

    /**
     * Dibuja el tetromino que viene, centrado dentro de la caja que lo contiene.
     * <p>
     * La forma viene con filas y columnas vacías alrededor, así que primero se recorta a la parte realmente
     * ocupada y recién después se escala, para que la pieza se vea lo más grande posible.
     */
    private void drawNextPiece(Graphics2D g2D, Tetromino next, int x, int y, int width, int height) {
        byte[][] shape = next.getActualShape();
        int minRow = shape.length;
        int maxRow = -1;
        int minCol = shape.length;
        int maxCol = -1;
        // Una forma sin celdas ocupadas no se puede recortar.
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 0) {
                    continue;
                }
                minRow = Math.min(minRow, i);
                maxRow = Math.max(maxRow, i);
                minCol = Math.min(minCol, j);
                maxCol = Math.max(maxCol, j);
            }
        }

        // Una forma sin celdas ocupadas no se puede recortar.
        if (maxRow < 0) {
            return;
        }

        int rows = maxRow - minRow + 1;
        int cols = maxCol - minCol + 1;
        int cell = Math.min(width / (cols + 1), height / (rows + 1));
        cell = Math.min(cell, 26);

        int originX = x + (width - cell * cols) / 2;
        int originY = y + (height - cell * rows) / 2;

        g2D.setColor(NEXT_BACKGROUND);
        g2D.fill(new Rectangle2D.Double(originX - cell / 2, originY - cell / 2,
                cell * cols + cell, cell * rows + cell));

        g2D.setColor(next.getTetroColor());
        for (int i = minRow; i <= maxRow; i++) {
            for (int j = minCol; j <= maxCol; j++) {
                if (shape[i][j] == 0) {
                    continue;
                }
                int cellX = originX + (j - minCol) * cell;
                int cellY = originY + (i - minRow) * cell;
                g2D.fill(new Rectangle2D.Double(cellX + 2, cellY + 2, cell - 4, cell - 4));
            }
        }
    }
}
