package Components.Tetros;

import GUI.Properties.TColor;

public class Tetrominoe_T extends Tetrominoe {
    /*
        Shape (3 x 3)
        0º      90º     180º    270º
        r0      r1      r2      r3
        □ ■ □   ■ □ □   □ □ □   □ ■ □
        ■ ■ ■   ■ ■ □   ■ ■ ■   ■ ■ □
        □ □ □   ■ □ □   □ ■ □   □ ■ □
    */
    private static final byte[][] spawnState = {
            {0, 6, 0},
            {6, 6, 6},
            {0, 0, 0},
    };
    private final byte[][] rotateShape = new byte[4][4];
    public Tetrominoe_T () {
        tetroColor = TColor.VIOLET;
    }

    @Override
    public byte[][] rotateL(byte[][] c) {
        return super.rotateL(c);
    }

    public byte[][] getRotateShape() {
        return rotateShape;
    }
    public byte[][] getSpawnShape() {
        return spawnState;
    }
}