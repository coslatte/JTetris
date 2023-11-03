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
            {2, 0, 0},
            {2, 2, 2},
            {0, 0, 0},
    };
    private final byte[][] rotateShape = new byte[4][4];
    public Tetrominoe_J(String tetroColor) {
        /*
        el color de este es amarillo
         */
        super(tetroColor);
    }

    @Override
    public byte[][] rotate(byte[][] c) {
        for (int x = 0; x<c.length;x++){
            for (int y= 0; y < c.length; y++){
                rotateShape[y][rotateShape.length -1 -x] = c[x][y];
            }
        }
        return rotateShape ;

    }

    public byte[][] getRotateShape() {
        return rotateShape;
    }

    public byte[][] getSpawnShape() {
        return spawnShape;
    }
}
