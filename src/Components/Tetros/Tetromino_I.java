package Components.Tetros;

import GUI.Properties.TColor;

public class Tetromino_I extends Tetromino {
    /*
        Shape (4 x 4)
        0º        90º       180º      270º
        r0        r1        r2        r3
        □ □ □ □   □ □ ■ □   □ □ □ □   □ ■ □ □
        ■ ■ ■ ■   □ □ ■ □   □ □ □ □   □ ■ □ □
        □ □ □ □   □ □ ■ □   ■ ■ ■ ■   □ ■ □ □
        □ □ □ □   □ □ ■ □   □ □ □ □   □ ■ □ □
     */
    private final byte[][] spawnState = {
            {0, 0, 0, 0},
            {1, 1, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    };
    private final byte[][] rotateShape = new byte[4][4];
    public Tetromino_I() {
        tetroColor = TColor.CYAN;
    }

    @Override
    public byte[][] rotateTetromino(byte[][] c) {
        return super.rotateTetromino(c);
    }

    public byte[][] getRotateShape() {
        return rotateShape;
    }
    public byte[][] getSpawnState() {
        return spawnState;
    }
}
