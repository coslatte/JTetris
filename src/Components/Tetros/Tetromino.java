package Components.Tetros;

import java.awt.*;

public abstract class Tetromino {

    /**
     * Color del tetromino.
     */
    public static Color tetroColor;

    /**
     * Estado actual en que se encuentra el tetromino.
     */
    protected byte[][] actualState;

    /**
     * Estado en que se encuentra el tetromino rotado.
     */
    protected byte[][] rotatedState;

    /**
     * Rotación actual del tetromino.
     */
    protected byte r;

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
     * Rota el tetromino actual 90º en el sentido en que indique el parámetro.
     * @param orientation Si '{@code true}' rota en sentido horario; antihorario en caso contrario.
     */
    public void rotateTetromino(boolean orientation) {
        rotatedState = new byte[actualState.length][actualState[0].length];
        if (orientation) {
            System.out.println("Se intentará rotar el tetromino hacia la derecha.");
            for (int i = 0; i < actualState.length; i++) {
                for (int j = 0; j < actualState.length; j++) {
                    rotatedState[j][rotatedState.length - 1 - i] = actualState[i][j];
                }
            }
            updateR(true);
        } else {
            System.out.println("Se intentará rotar el tetromino hacia la izquierda.");
            for (int i = 0; i < actualState.length; i++) {
                System.arraycopy(actualState[i], 0, rotatedState[rotatedState.length - 1 - i], 0, actualState.length);
            }
            updateR(false);
        }
    }

    /**
     * Actualiza el valor de <strong>r</strong>. Se suma 1 si la rotación se efectúa horariamente, y se resta 1
     * si se realiza antihorariamente.
     * Su valor pasará por 4 estados:
     * <li> r = 0: Tetromino estará en su rotación de spawn. </li>
     * <li> r = 1: Tetromino habrá efectuado una rotación de 90º. </li>
     * <li> r = 2: Tetromino habrá efectuado una rotación de 180º. </li>
     * <li> r = 3: Tetromino habrá efectuado una rotación de 270º. </li>
     * @param clockwise Valor booleano para determinar si se suma o se resta en 1 el valor de <strong>r</strong>
     *                  dependiendo si la rotación se efectúa horaria o antihorariamente.
     */
    private void updateR(boolean clockwise) {
        if (clockwise) {
            if (r == 3)
                r = 0;
            else
                r++;
        } else
            if (r == 0)
                r = 3;
            else
                r--;
    }

    public static Color getTetroColor() {
        return tetroColor;
    }

    public byte[][] getActualState() {
        return actualState;
    }

    public byte[][] getRotatedState() {
        return rotatedState;
    }
}