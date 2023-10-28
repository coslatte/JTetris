package Tetrominoes;

public class Tetrominoe_O extends Tetrominoe {
    /*
        Shape (2 x 2)
        0º    90º   180º  270º
        r0    r1    r2    r3
        ■ ■   ■ ■   ■ ■   ■ ■
        ■ ■   ■ ■   ■ ■   ■ ■
    */
    private static final byte[][] tetrominoe_O_spawnShape = {
            {1, 1},
            {1, 1},
    };
    public Tetrominoe_O(byte tetroDimension, String tetroColor) {
        super(tetroDimension, tetroColor);
    }
}
