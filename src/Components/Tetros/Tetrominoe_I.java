package Components.Tetros;

import GUI.Properties.TColor;

public class Tetrominoe_I extends Tetrominoe {
    /*
        Shape (4 x 4)
        0º        90º       180º      270º
        r0        r1        r2        r3
        □ □ □ □   □ □ ■ □   □ □ □ □   □ ■ □ □
        ■ ■ ■ ■   □ □ ■ □   □ □ □ □   □ ■ □ □
        □ □ □ □   □ □ ■ □   ■ ■ ■ ■   □ ■ □ □
        □ □ □ □   □ □ ■ □   □ □ □ □   □ ■ □ □
     */
    private final byte[][] spawnShape = {
            {1, 1, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    };
    private final byte[][] rotateShape = new byte[4][4];

    public Tetrominoe_I() {
        tetroColor = TColor.CYAN;
    }

    /*se Cambio total en el metodo de rotacionla idea es que este devuelva la matriz rotada
     para que sea mas facil su implementacion, y por parametros luego de la rotacion de pase de nuevo esa matriz rotada para volverla a rotar
     para es se usa el getRotateShape();

     */
    @Override
    public byte[][] rotate(byte[][] c) {
        for (int x = 0; x < c.length; x++) {
            for (int y = 0; y < c.length; y++) {
                rotateShape[y][rotateShape.length - 1 - x] = c[x][y];
            }
        }
        return rotateShape;

    }

    public byte[][] getRotateShape() {
        return rotateShape;
    }

    public byte[][] getSpawnShape() {
        return spawnShape;
    }
}
