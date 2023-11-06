package Components.Tetros;

import java.awt.*;

public abstract class Tetromino {
    protected Color tetroColor;
    protected byte[][] centrePoint;
    protected int[] spawnPosition;
    protected byte[][] rotatedTetro;

    /**
     * Según el SRS, para los tetrominos <strong>J</strong>, <strong>L</strong>, <strong>S</strong> y <strong>T</strong> esta es la tabla con los valores de corrimiento del punto central del tetromino acorde a la posibilidad o no de poder rotar.
     * <p> Teniendo: </p>
     * <p> Filas(4) - Rotación </p>
     * <p> Columna(5) - Desfaz (Offset) del centro del tetromino. </p>
     */
    protected byte[][][] JLSTZ_TetrominoOffsetData =
    {
            {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},
            {{0, 0}, {1, 0}, {1, -1}, {0, 2}, {1, 2}},
            {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},
            {{0, 0}, {-1, 0}, {-1, -1}, {0, 2}, {-1, 2}}
    };

    /**
     * Según el SRS, para el tetromino <strong>I</strong> esta es la tabla con los valores de corrimiento del punto central del tetromino acorde a la posibilidad o no de poder rotar.
     * <p> Teniendo: </p>
     * <p> Filas(4) - Rotación </p>
     * <p> Columna(5) - Desfaz (Offset) del centro del tetromino. </p>
     */
    protected final byte[][][] I_TetrominoOffsetData =
    {
            {{0, 0}, {-1, 0}, {2, 0}, {-1, 0}, {2, 0}},
            {{-1, 0}, {0, 0}, {0, 0}, {0, 1}, {0, -2}},
            {{-1, 1}, {0, 0}, {0, 0}, {0, 1}, {0, -2}},
            {{-1, 1}, {1, 1}, {-2, 1}, {1, 0}, {-2, 0}}
    };

    /**
     * Según el SRS, para el tetromino <strong>O</strong> esta es la tabla con los valores de corrimiento del punto central del tetromino acorde a la posibilidad o no de poder rotar.
     * <p> Teniendo: </p>
     * <p> Filas(1) - Rotación </p>
     * <p> Columna(5) - Desfaz (Offset) del centro del tetromino. </p>
     */
    protected byte[][][] O_TetrominoOffsetData =
    {
            {{0, 0}, {0, -1}, {-1, -1}, {-1, -1}, {-1, 0}},
    };

    /**
     * Método que rotar 90º hacia la izquierda la matriz que se pasa por parámetros.
     * @param c
     * @return rotatedTetro  la matriz <code> c </code> pasada como parámetro en sentido antihorario.
     */
    public byte[][] rotateTetromino(byte[][] c) {
        for (int x = 0; x < c.length; x++) {
            for (int y = 0; y < c.length; y++) {
                rotatedTetro[y][rotatedTetro.length - 1 - x] = c[x][y];
            }
        }
        return rotatedTetro;
    }
}