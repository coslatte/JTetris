package Tetrominoes;

public abstract class Tetrominoe {
    protected String tetroColor;
    public Tetrominoe(String tetroColor) {
        this.tetroColor = tetroColor;
    }

    // Lo hacemos abstracto?
    public void rotate() {
    }
}
