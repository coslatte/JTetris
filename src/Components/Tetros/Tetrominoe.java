package Components.Tetros;

import java.awt.*;

public abstract class Tetrominoe {
    protected Color tetroColor;
    protected int[] spawnPosition = {0, 0};
    public abstract byte[][] rotate(byte[][] c);
}
