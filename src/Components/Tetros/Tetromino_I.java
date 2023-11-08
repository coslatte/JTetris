package Components.Tetros;

import GUI.Properties.TColor;


/**
 * <pre>
 * Shape Rotation (4 x 4)
 * 0º        90º       180º      270º
 * r0        r1        r2        r3
 * □ □ □ □   □ □ ■ □   □ □ □ □   □ ■ □ □
 * ■ ■ ■ ■   □ □ ■ □   □ □ □ □   □ ■ □ □
 * □ □ □ □   □ □ ■ □   ■ ■ ■ ■   □ ■ □ □
 * □ □ □ □   □ □ ■ □   □ □ □ □   □ ■ □ □ */

public class Tetromino_I extends Tetromino {
    public Tetromino_I() {
        tetroColor = TColor.CYAN;

        // spawn shape
        actualState = new byte[][]{
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
    }

    @Override
    public void rotateTetromino(boolean orientation) {
        super.rotateTetromino(orientation);
    }
}
