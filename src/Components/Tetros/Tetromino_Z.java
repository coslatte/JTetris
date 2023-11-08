package Components.Tetros;

import GUI.Properties.TColor;


/** <pre>
 * Shape Rotation (3 x 3)
 * 0º      90º     180º    270º
 * r0      r1      r2      r3
 * ■ ■ □   □ □ ■   □ □ □   □ ■ □
 * □ ■ ■   □ ■ ■   ■ ■ □   ■ ■ □
 * □ □ □   □ ■ □   □ ■ ■   ■ □ □ */

public class Tetromino_Z extends Tetromino {
    private final byte[][] rotateShape = new byte[4][4];
    public Tetromino_Z() {
        tetroColor = TColor.RED;

        // spawn state
        actualState = new byte[][]{
                {7, 7, 0},
                {0, 7, 7},
                {0, 0, 0},
        };
    }

    @Override
    public byte[][] rotateTetromino(byte[][] c) {
        return super.rotateTetromino(c);
    }
}