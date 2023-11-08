package Components.Tetros;

import GUI.Properties.TColor;


/** <pre>
 * Shape Rotation (3 x 3)
 * 0º      90º     180º    270º
 * r0      r1      r2      r3
 * ■ □ □   □ ■ ■   □ □ □   □ ■ □
 * ■ ■ ■   □ ■ □   ■ ■ ■   □ ■ □
 * □ □ □   □ ■ □   □ □ ■   ■ ■ □ */

public class Tetromino_J extends Tetromino {
    public Tetromino_J() {
        tetroColor = TColor.BLUE;

        // spawn shape
        actualState = new byte[][]{
                {2, 0, 0},
                {2, 2, 2},
                {0, 0, 0}
        };
    }

    @Override
    public byte[][] rotateTetromino(byte[][] c) {
        return super.rotateTetromino(c);
    }
}
