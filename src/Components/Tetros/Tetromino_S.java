package Components.Tetros;

import GUI.Properties.TColor;


/** <pre>
 * Shape Rotation (3 x 3)
 * 0º      90º     180º    270º
 * r0      r1      r2      r3
 * □ ■ ■   □ ■ □   □ □ □   ■ □ □
 * ■ ■ □   □ ■ ■   □ ■ ■   ■ ■ □
 * □ □ □   □ □ ■   ■ ■ □   □ ■ □ */

public class Tetromino_S extends Tetromino {
    public Tetromino_S() {
        tetroColor = TColor.GREEN;

        // spawn shape
        actualState = new byte[][]{
                {0, 5, 5},
                {5, 5, 0},
                {0, 0, 0},
        };
    }

    @Override
    public void rotateTetromino(boolean orientation) {
        super.rotateTetromino(orientation);
    }
}
