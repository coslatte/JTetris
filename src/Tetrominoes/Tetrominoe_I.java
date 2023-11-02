package Tetrominoes;

public class Tetrominoe_I extends Tetrominoe {
    /*
        Shape (4 x 4)
        0º        90º       180º      270º
        r0        r1        r2        r3
        ■ ■ ■ ■   ■ □ □ □   ■ ■ ■ ■   ■ □ □ □
        □ □ □ □   ■ □ □ □   □ □ □ □   ■ □ □ □
        □ □ □ □   ■ □ □ □   □ □ □ □   ■ □ □ □
        □ □ □ □   ■ □ □ □   □ □ □ □   ■ □ □ □
     */
    private final byte[][] spawnShape = {
            {1, 1, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    };
    public Tetrominoe_I (String tetroColor) {
        super (tetroColor);
    }

    @Override
    public void rotate() {

    }
}
