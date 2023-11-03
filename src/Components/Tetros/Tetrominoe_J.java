package Components.Tetros;

public class Tetrominoe_J extends Tetrominoe {
    /*
        Shape (3 x 3)
        0º      90º     180º    270º
        r0      r1      r2      r3
        ■ □ □   ■ ■ □   ■ ■ ■   □ ■ □
        ■ ■ ■   ■ □ □   □ □ ■   □ ■ □
        □ □ □   ■ □ □   □ □ □   ■ ■ □
    */
    private static final byte[][] spawnShape = {
            {1, 0, 0},
            {1, 1, 1},
            {0, 0, 0},
    };
    private final byte[][] rotateShape = new byte[4][4];
    public Tetrominoe_J(String tetroColor) {
        super(tetroColor);
    }

    @Override
    public void rotate() {
        for (int x = 0; x<spawnShape.length;x++){
            for (int y= 0; y < spawnShape.length; y++){
                rotateShape[y][rotateShape.length -1 -x] = spawnShape[x][y];
            }
        }

    }
}
