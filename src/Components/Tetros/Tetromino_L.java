package Components.Tetros;

import GUI.Properties.TColor;

public class Tetromino_L extends Tetromino {
    /*
        Shape (3 x 3)
        0º      90º     180º    270º
        r0      r1      r2      r3
        □ □ ■   □ ■ □   □ □ □   ■ ■ □
        ■ ■ ■   □ ■ □   ■ ■ ■   □ ■ □
        □ □ □   □ ■ ■   ■ □ □   □ ■ □
    */
    private static final byte[][] spawnState = {
            {0, 0, 3},
            {3, 3, 3},
            {0, 0, 0},
    };
    private final byte[][] rotateShape = new byte[4][4];
    public Tetromino_L() {
        tetroColor = TColor.ORANGE;
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
