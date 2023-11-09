package GUI.Tetris;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import GUI.TetrisGUI;
import GUI.Properties.TColor;
import GUI.Properties.TStyle;
import GUI.Tetris.GUI_Components.PlayButton;
import java.awt.*;

public class FirstPanel extends JPanel {
    /*
     * panel de inicio de el juego contendra en titulo de el juego asi como un boton para empezar a jugar
     */
    private PlayButton playButton;
    
    Thread firstAnimation;
    public FirstPanel(){
        setBackground(TColor.BLACK202);
        setLayout(null);
        playButton = new PlayButton();
        add(playButton);

    }
    public void addActionForButton(ActionListener e){
        playButton.addActionListener(e);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(TColor.BRIGHT_GREEN);
        g2.setStroke(new BasicStroke(TStyle.basicStrokeBordersWidth));
        g2.drawRoundRect(
                getWidth() / 350 + 5,
                getHeight() / 128,
                getWidth() - 13,
                getHeight() - 13,
                TStyle.rectangleRoundBordersAmount,
                TStyle.rectangleRoundBordersAmount
        );
        /*
         * coordenadas que va a tener el boton, asi como su dimension
         */
        playButton.setBounds(getWidth()/3+28, getHeight()/2+50, getWidth()/4, getHeight()/16);
    }
}
