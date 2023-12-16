package Components.Tetros;

import Components.Tetros.Primitives.Tetromino;
import GUI.Properties.TColor;


/** <pre>
 * Shape Rotation (3 x 3)
 * 0º      90º     180º    270º
 * r0      r1      r2      r3
 * □ □ ■   □ ■ □   □ □ □   ■ ■ □
 * ■ ■ ■   □ ■ □   ■ ■ ■   □ ■ □
 * □ □ □   □ ■ ■   ■ □ □   □ ■ □ */

public class Tetromino_L extends Tetromino {
    public Tetromino_L() {
        tetroColor = TColor.ORANGE;

        // spawn shape
        actualShape = new byte[][]{
                {0, 0, 3},
                {3, 3, 3},
                {0, 0, 0},
        };
    }

    @Override
    public void rotateTetromino(boolean orientation) {
        super.rotateTetromino(orientation);
    }
}
