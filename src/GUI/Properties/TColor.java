package GUI.Properties;

import java.awt.*;

/**
 * Paleta de colores de la aplicación.
 */
public class TColor {
    public static final Color BLACK202 = Color.decode("#202020");
    public static final Color BLACK303 = Color.decode("#303030");
    public static final Color BLACK404 = Color.decode("#404040");
    public static final Color CYAN = Color.decode("#3df0cc"); //Tetrominoe I
    public static final Color BLUE = Color.decode("#228be6"); //Tetrominoe J
    public static final Color ORANGE = Color.decode("#fd7e14"); // Tetrominoe L
    public static final Color YELLOW = Color.decode("#ffd43b"); //Tetrominoe O
    public static final Color GREEN = Color.decode("#47eb65"); //Tetrominoe S
    public static final Color VIOLET = Color.decode("#be4bdb"); //Tetrominoe T
    public static final Color RED = Color.decode("#e03131"); //Tetrominoe Z
    public static final Color BRIGHT_GREEN = Color.decode("#12b886");
    public static final Color LABEL = Color.decode("#9e9e9e");

    /**
     * Devuelve el color que corresponde a un tetromino a partir del número que sus celdas llevan escrito.
     * Así el tablero puede pintar cada bloque sólo mirando el valor que encuentra en la matriz, sin tener
     * que saber qué pieza es.
     *
     * @param id Identificador del tetromino: 1 I, 2 J, 3 L, 4 O, 5 S, 6 T, 7 Z.
     */
    public static Color tetrominoColor(int id) {
        switch (id) {
            case 1:
                return CYAN;
            case 2:
                return BLUE;
            case 3:
                return ORANGE;
            case 4:
                return YELLOW;
            case 5:
                return GREEN;
            case 6:
                return VIOLET;
            case 7:
                return RED;
            default:
                return Color.WHITE;
        }
    }
}
