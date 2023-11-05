package Components.Tetros;

import java.awt.*;

public abstract class Tetrominoe {
    protected Color tetroColor;
    protected byte[][] centrePoint;
    protected int[] spawnPosition;
    protected byte[][] rotatedTetro;


    /**
     * Método que rotar 90º hacia la izquierda la matriz que se pasa por parámetros.
     * @param c
     * @return La matriz <code> c </code> pasada como parámetro en sentido antihorario.
     */
    public byte[][] rotateL(byte[][] c) {
        for (int x = 0; x < c.length; x++) {
            for (int y = 0; y < c.length; y++) {
                rotatedTetro[y][rotatedTetro.length - 1 - x] = c[x][y];
            }
        }
        return rotatedTetro;
    }
}