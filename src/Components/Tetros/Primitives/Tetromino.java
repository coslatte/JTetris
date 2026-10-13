package Components.Tetros.Primitives;

import java.awt.*;

public abstract class Tetromino {

    /**
     * Color del tetromino.
     */
    protected Color tetroColor;

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
     * Valor de <strong>r</strong> anterior al intento de rotación en curso. Sirve para poder deshacer el
     * cambio cuando la rotación finalmente resulta inválida (ver {@link #cancelRotation()}).
     */
    protected byte previousR = 0;

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
     * Identificador numérico del tetromino. Lo asigna cada subclase en su constructor y es el mismo número
     * que aparece escrito dentro de las celdas de su forma.
     */
    protected byte typeId;

    /**
     * Devuelve el desplazamiento (columnas, filas) que la tabla SRS del tetromino indica para el test
     * solicitado partiendo del estado de rotación <strong>r</strong>.
     * <p>
     * La columna 0 de cada tabla es siempre el test "sin desplazamiento", por lo que el tablero puede
     * iterar los tests de 0 a 4 sin tratar ese caso por separado. La tabla del tetromino O tiene una única
     * fila, de modo que cualquier estado de rotación devuelve su fila 0.
     *
     * @param r    Estado de rotación desde el que se intenta salir.
     * @param test Número de test, de 0 a 4.
     * @return Arreglo de dos elementos: desplazamiento en columnas y en filas.
     */
    public byte[] getOffset(int r, int test) {
        byte[][][] table;
        if (typeId == 1) {
            table = I_TetrominoOffsetData;
        } else if (typeId == 4) {
            table = O_TetrominoOffsetData;
            r = 0;
        } else {
            table = JLSTZ_TetrominoOffsetData;
        }

        // Se acota 'r' y 'test' para que un valor inesperado nunca provoque un desbordamiento.
        int row = Math.floorMod(r, table.length);
        int col = Math.min(Math.max(test, 0), table[row].length - 1);
        return table[row][col];
    }

    public byte getTypeId() {
        return typeId;
    }

    /**
     * Rota el tetromino actual 90º en el sentido en que indique el parámetro.
     * <p>
     * La rotación se calcula sobre una matriz cuadrada del mismo tamaño que la forma actual, de modo que
     * la pieza siempre conserva su caja. El resultado queda en {@link #rotatedShape} y <strong>no</strong> se
     * aplica todavía: el tablero es quien decide si la rotación es legal (colisiones, paredes, suelo) y, si lo es,
     * llama a {@link #applyRotation()}. Así una rotación inválida no destruye la forma anterior.
     *
     * @param orientation Si '{@code true}' rota en sentido horario; antihorario en caso contrario.
     */
    public void rotateTetromino(boolean orientation) {
        int n = actualShape.length;
        previousR = r;
        rotatedShape = new byte[n][n];
        if (orientation) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    rotatedShape[j][n - 1 - i] = actualShape[i][j];
                }
            }
            updateR(true);
        } else {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    rotatedShape[n - 1 - j][i] = actualShape[i][j];
                }
            }
            updateR(false);
        }
    }

    /**
     * Acepta la última rotación calculada por {@link #rotateTetromino(boolean)}, convirtiendo
     * {@link #rotatedShape} en la nueva {@link #actualShape}.
     */
    public void applyRotation() {
        if (rotatedShape != null) {
            actualShape = rotatedShape;
            rotatedShape = null;
        }
    }

    /**
     * Descarta la rotación pendiente sin aplicarla. Se usa cuando el tablero no encuentra ningún test del
     * SRS que permita rotar: se descarta la matriz candidata y se devuelve <strong>r</strong> al valor que
     * tenía antes del intento, para que el estado interno siga siendo coherente con lo dibujado.
     */
    public void cancelRotation() {
        rotatedShape = null;
        r = previousR;
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

    public Color getTetroColor() {
        return tetroColor;
    }

    public byte[][] getActualShape() {
        return actualShape;
    }

    public byte[][] getRotatedShape() {
        return rotatedShape;
    }

    public byte getR() {
        return r;
    }
}
