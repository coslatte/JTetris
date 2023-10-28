package Tetrominoes;

public class Tetrominoe_T extends Tetrominoe {
    /*
        Shape (3 x 3)
        0º      90º     180º    270º
        r0      r1      r2      r3
        □ ■ □   ■ □ □   □ □ □   □ ■ □
        ■ ■ ■   ■ ■ □   ■ ■ ■   ■ ■ □
        □ □ □   ■ □ □   □ ■ □   □ ■ □
    */
    private static final byte[][] tetrominoe_S_spawnShape = {
            {0, 1, 0},
            {1, 1, 1},
            {0, 0, 0},
    };
    public Tetrominoe_T (byte tetroDimension, String tetroColor) {
        super (tetroDimension, tetroColor);
    }
}
