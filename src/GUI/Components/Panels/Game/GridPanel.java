package GUI.Components.Panels.Game;

import Components.Tetros.Primitives.Tetromino;
import GUI.Properties.TColor;
import GUI.Properties.TStyle;

import javax.swing.*;

// import Components.Tetros.Tetromino;
import Components.Tetros.Tetromino_I;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

/**
 * Clase donde van las matrices y básicamente el juego; donde se anima.
 */
public class GridPanel extends JPanel {

    /**
     * <code> fieldArray </code> tiene la responsabilidad de iniciar los valores (en 0) de la matriz
     * donde van a estar los bloques que forman a los tetrominos.
     */
    private int[][] fieldArray = new int[24][10];
    private int[][] checkArray = new int[24][10];
    private Rectangle2D.Double [][] celdas ;
    private Tetromino actualTetromino;
    /*
     * este atributo es para cuando se inicie el juego, llenar las dos matrices con 0, osea que no hay que dibujar nada
     * luego en un metodo mas abajo cambia a false luego de llenar las dos matrices
     */
    private boolean state = true;
    /*
     *
     * este atributo es para cuando un tetromino llegue al fondo
     *
     */
    private boolean endLine ;
    /*
     * estos enteros definen las posiciones a partir de donde se van a copiar los valores de los tetrominos
     */
    private int beginJ, beginI, endJ, endI = 0;

    public GridPanel() {
        setBackground(TColor.BLACK202);
    }

    /*
     * metodo que va a llenar el arreglo de rectangulos, que son basicamente los que se van a dibujar
     */

    public void fillCeldas(){
        celdas = new Rectangle2D.Double[21][10];

        for (int i = 0;i<21;i++){
            for(int j = 0;j < 10;j++){
                celdas[i][j] = new Rectangle2D.Double((double)(j*getWidth()/11+21),
                (double)(i*getHeight()/21-14)
                , (double)(getWidth()/11-7) ,
                (double)(getHeight()/21-5) );
            }
        }
        /*
         * aqui se llenan con 0 los arreglos al iniciar el juego
         */

        if(state){
            for(int i = 0; i<fieldArray.length;i++){
                for(int j = 0;j<10;j++){
                    fieldArray[i][j] = 0;
                    checkArray[i][j] = 0;
                }
            }
            state = false;
        }

    }

    /*
     *
     * este seria el metodo para copiar el tetromino actual que esta corriendo
     *
     */

    public void addTetromino(Tetromino e){
        actualTetromino = e;
        if(e instanceof Tetromino_I){
            beginJ = 3;
            endI = 3;
            endJ = 6;
        }else {
            beginJ = 4;
            endI = 2;
            endJ = 6;
        }
    }
    /*
     *
     * los proximos metodos son para mover el tetromino activo dentro de la matriz checkArray, el moveDown ya va junto
     * con el movimiento, se tiene que llamar en el hilo del juego, y los otros son para los eventos de teclado
     *
     */
    public void moveDown(){
        boolean check = true;
        if(endI+1<fieldArray.length){
            for(int j = beginJ; j<=endJ;j++ ){
                if(fieldArray[endI+1][j]!=0 && checkArray[endI][j]!=0){
                    check = false;
                    endLine = true;
                }
            }
        } else {
            check = false;
            endLine = true;
        }
        if(check){
            int [][] temp = new int[actualTetromino.getActualShape().length][endJ-beginJ+1];
            for(int i = 0; i<temp.length;i++){
                for(int j = 0;j<temp[0].length;j++){
                    temp[i][j] = (int) actualTetromino.getActualShape()[i][j];
                }
            }
            for(int i = endI; i>=beginI;i--){
                System.arraycopy(temp[i], 0, checkArray[i+1], beginJ, temp[0].length);
            }
            for(int j = beginJ; j<=endJ;j++){
                checkArray[beginI][j] = 0;
            }
        }
    }

    public void moveToTheLeft(){

    }

    public void moveToTheRight(){

    }


    /*
     * metodo para cuando una fila se llene se limpie
     */
    public void clearRow(){

    }

    /*
     *
     * metodo que va insertar el tetromino en la matriz fieldArray, se debe llamar en el hilo principal cuando pase el
     * tiempo especifico
     *
     */
    public void insertTetromino(){

    }

    /*
     *
     * metodo donde sucede la magia, y son dibujados los tetrominos de acuerdo a su posicion dentro de las dos matrices
     *
     */
    public void drawfieldArray(Graphics2D g2){
        //aqui se rellenan las celdas
        for(int i = 4;i<fieldArray.length;i++){
            for(int j = 0; j <10 ; j++){
                if(fieldArray[i][j]!=0||checkArray[i][j]!=0){
                    int eso = 0;
                    if(fieldArray[i][j]!=0){
                        eso = fieldArray[i][j];
                    }else{
                        eso = checkArray[i][j];
                    }
                    switch (eso) {
                        case 1:{
                            g2.setColor(TColor.CYAN);
                            break;
                        }
                        case 2:{
                            g2.setColor(TColor.BLUE);
                            break;
                        }
                        case 3:{
                            g2.setColor(TColor.ORANGE);
                            break;
                        }
                        case 4:{
                            g2.setColor(TColor.YELLOW);
                            break;
                        }
                        case 5:{
                            g2.setColor(TColor.GREEN);
                            break;
                        }
                        case 6:{
                            g2.setColor(TColor.VIOLET);
                            break;
                        }
                        case 7:{
                            g2.setColor(TColor.RED);
                            break;
                        }
                        default:
                            break;
                    }
                    g2.fill(celdas[i-3][j]);
                }
            }
        }
        //aqui se dibuja el borde de las mismas
        g2.setColor(Color.GRAY);
        for(int i = 4;i<fieldArray.length;i++){
            for(int j = 0;j<fieldArray[0].length;j++){
                g2.draw(celdas[i-3][j]);
            }
        }
    }

    public void update() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        draw(g2);
    }

    public void draw(Graphics2D g2D) {
        g2D.setColor(TColor.BRIGHT_GREEN);
        g2D.setStroke(new BasicStroke(TStyle.basicStrokeBordersWidth));
        g2D.drawRoundRect(
                getWidth() / 350 + 5,
                getHeight() / 128,
                getWidth() - 13,
                getHeight() - 13,
                TStyle.rectangleRoundBordersAmount,
                TStyle.rectangleRoundBordersAmount
        );

        /*
         * aqui viene la fase de llenar el array de rectangulos para luego volverlos a dibujar
         */
        fillCeldas();
        drawfieldArray(g2D);

    }

    public int[][] getFieldArray() {
        return fieldArray;
    }
    public boolean getEndLine(){
        return endLine;
    }
}
