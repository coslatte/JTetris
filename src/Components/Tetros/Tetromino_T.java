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
    public Tetromino_T() {
        tetroColor = TColor.VIOLET;

        // spawn shape
        actualShape = new byte[][]{
                {0, 6, 0},
                {6, 6, 6},
                {0, 0, 0},
        };
    }

    @Override
    public void rotateTetromino(boolean orientation) {
        super.rotateTetromino(orientation);
    }
}
