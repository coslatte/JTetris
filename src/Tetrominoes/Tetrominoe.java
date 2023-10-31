package Tetrominoes;

public abstract class Tetrominoe {
    protected String tetroColor;
    public Tetrominoe(String tetroColor) {
        this.tetroColor = tetroColor;
    }

    public abstract void rotate();
}
