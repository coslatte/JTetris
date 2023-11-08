package Components.Tetros;

import GUI.Properties.TColor;


/** <pre>
 * Shape Rotation (3 x 3)
 * 0º      90º     180º    270º
 * r0      r1      r2      r3
 * □ ■ ■   □ ■ ■   □ ■ ■   □ ■ ■
 * □ ■ ■   □ ■ ■   □ ■ ■   □ ■ ■
 * □ □ □   □ □ □   □ □ □   □ □ □ */

public class Tetromino_O extends Tetromino {
    public Tetromino_O() {
        tetroColor = TColor.YELLOW;

        // spawn shape
        actualState = new byte[][]{
                {0, 4, 4},
                {0, 4, 4},
                {0, 0, 0},
        };
    }

    @Override
    public void rotateTetromino(boolean orientation) {
        super.rotateTetromino(orientation);
    }
}
