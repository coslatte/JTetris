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
    protected byte[][] actualShape;

    /**
     * Estado en que se encuentra el tetromino rotado.
     */
    protected byte[][] rotatedShape;

    /**
     * Rotación actual del tetromino.
     */
    protected byte r = 0;

    /**
     * Variable que controla el centro de la pieza. <br>
     * No todas las piezas tendrán el mismo centro. <br>
     * El centro de todas las piezas estarán en constante cambio. El SRS explica muy detalladamente a dónde se desplazan el centro de las piezas cuando estas rotan. <p>
     * El centro cambiará por varios factores:
     * <ol>
     * <li> Eventos de movimientos (mover izquierda, derecha, abajo).
     * <li> Eventos de rotación (acorde al SRS).
     * </ol>
     */
    protected byte[][] centre;

    /*
     * SRS:
     * El Super Rotation System consiste en asignar un centro al tetromino de acuerdo a qué tipo sea y donde
     * se consideren las posibles colisiones en el entorno.
     * Cuando se intenta rotar un tetro y este no logra rotar, se busca una manera de que pueda hacerlo.
     * Para eso están los Tests, que son una serie de "movimientos" que hará el centro del tetromino en busca de
     * una forma de no colisionar con nada.
     * Para los tetrominos J, L, S, T, e I, la tabla cuenta con 4 filas (estado de rotación - r0, r1, r2, r3) y
     * 5 columnas (los Tests - 1, 2, 3, 4, 5).
     * Si cuando se vaya a rotar la pieza, esta no puede hacerlo por algúna razón, se pasará por cada Test hasta
     * encontrar una manera de poder rotar. Si no se puede, entonces no rota.
     * Para el tetromino O, la tabla cuenta con 1 fila, y 5 tests. Hasta el momento pensamos no rotar la pieza.
     */

    /**
     * Tabla del SRS para los tetrominos: <b>J</b>, <b>L</b>, <b>S</b>, <b>T</b> y <b>Z</b>.
     */
    protected byte[][][] JLSTZ_TetrominoOffsetData =
    {
            {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},
            {{0, 0}, {1, 0}, {1, -1}, {0, 2}, {1, 2}},
            {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},
            {{0, 0}, {-1, 0}, {-1, -1}, {0, 2}, {-1, 2}}
    };

    /**
     * Tabla del SRS para el tetromino: <b>I</b>.
     */
    protected byte[][][] I_TetrominoOffsetData =
    {
            {{0, 0}, {-1, 0}, {2, 0}, {-1, 0}, {2, 0}},
            {{-1, 0}, {0, 0}, {0, 0}, {0, 1}, {0, -2}},
            {{-1, 1}, {0, 0}, {0, 0}, {0, 1}, {0, -2}},
            {{-1, 1}, {1, 1}, {-2, 1}, {1, 0}, {-2, 0}}
    };

    /**
     * Tabla del SRS para el tetromino: <b>O</b>.
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
        rotatedShape = new byte[actualShape.length][actualShape[0].length];
        if (orientation) {
            System.out.println("Se intentará rotar el tetromino hacia la derecha.");
            for (int i = 0; i < actualShape.length; i++) {
                for (int j = 0; j < actualShape.length; j++) {
                    rotatedShape[j][rotatedShape.length - 1 - i] = actualShape[i][j];
                }
            }
            updateR(true);
        } else {
            System.out.println("Se intentará rotar el tetromino hacia la izquierda.");
            for (int i = 0; i < actualShape.length; i++) {
                System.arraycopy(actualShape[i], 0, rotatedShape[rotatedShape.length - 1 - i], 0, actualShape.length);
            }
            updateR(false);
        }
    }

    /**
     * Actualiza el valor de <strong>r</strong>. Se suma 1 si la rotación se efectúa horariamente, y se resta 1
     * si se realiza antihorariamente.
     * Su valor pasará por 4 estados:
     * <li> r = 0: Tetromino estará en su rotación de spawn. </li>
     * <li> r = 1: Tetromino estará rotado 90º. </li>
     * <li> r = 2: Tetromino estará rotado 180º. </li>
     * <li> r = 3: Tetromino estará rotado 270º. </li>
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

    public byte[][] getActualShape() {
        return actualShape;
    }

    public byte[][] getRotatedShape() {
        return rotatedShape;
    }
}