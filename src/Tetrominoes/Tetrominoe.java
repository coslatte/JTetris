package Tetrominoes;

public abstract class Tetrominoe {
    protected String tetroColor;
    protected byte tetroDimension;
    public Tetrominoe(byte tetroDimension, String tetroColor) {
        this.tetroDimension = tetroDimension;
        this.tetroColor = tetroColor;
    }

    // Lo hacemos abstracto?
    public void rotate() {
    }
}
