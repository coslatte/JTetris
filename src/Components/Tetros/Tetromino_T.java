package Components.Tetros;

import GUI.Properties.TColor;


/** <pre>
 * Shape Rotation (3 x 3)
 * 0º      90º     180º    270º
 * r0      r1      r2      r3
 * □ ■ □   ■ □ □   □ □ □   □ ■ □
 * ■ ■ ■   ■ ■ □   ■ ■ ■   ■ ■ □
 * □ □ □   ■ □ □   □ ■ □   □ ■ □ */

public class Tetromino_T extends Tetromino {
    private final byte[][] rotateShape = new byte[4][4];
    public Tetromino_T() {
        tetroColor = TColor.VIOLET;

        // spawn shape
        actualState = new byte[][]{
                {0, 6, 0},
                {6, 6, 6},
                {0, 0, 0},
        };
    }

    @Override
    public byte[][] rotateTetromino(byte[][] c) {
        return super.rotateTetromino(c);
    }
}
