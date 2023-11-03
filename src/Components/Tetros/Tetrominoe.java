package Components.Tetros;

public abstract class Tetrominoe {
    protected String tetroColor;
    protected int[] spawnPosition = {0, 0};
    public Tetrominoe(String tetroColor) {
        this.tetroColor = tetroColor;
    }
    public abstract byte[][] rotate(byte[][] c);
}
