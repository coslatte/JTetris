package Tetrominoes;

public abstract class Tetrominoe {
    protected String tetroColor;
    protected int[] spawnPosition = {0, 0};
    public Tetrominoe(String tetroColor) {
        this.tetroColor = tetroColor;
    }

    // Lo hacemos abstracto?
    public void rotate() {
    }
}
