package GUI;

import GUI.Tetris.FirstPanel;
import GUI.Tetris.TetrisPanel;
import GUI.Tetris.GUI_Components.PlayButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class TetrisGUI {
    private FirstPanel fp;
    private TetrisPanel tp;
    private JFrame mainFrame;
    private Timer timer;
   
    public static void main(String[] args) {

        JFrame mainFrame = new JFrame("JTetris Alpha Version");

        // ÍCONO DE LA APLICACIÓN
        try {
            mainFrame.setIconImage(ImageIO.read(new File("icon/jtetris-icon.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        FirstPanel fp = new FirstPanel();
        TetrisPanel tp = new TetrisPanel();
        

        mainFrame.add(fp);
        fp.addActionForButton(new ActionListener() {

            
            @Override
            public void actionPerformed(ActionEvent e) {
               
                mainFrame.remove(fp);
                mainFrame.add(tp);
                mainFrame.revalidate();
                tp.startGameThread();
                
            }
            
        });
        /*
         * Clase Toolkit de Java para obtener las dimensiones de la pantalla y ponerlo para que aparezca en
         * coordenadas relativas al monitor. Con esta se obtiene el ancho y largo de la pantalla y dividiéndolo entre
         * un número. Va a dar como resultado unas coordenadas relativas a cualquier monitor
         */
//        Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
//        mainFrame.setLocation((int) (screenDimensions.getWidth() / 6), (int) (screenDimensions.getHeight() / 6));

        mainFrame.setSize(650, 700);
        mainFrame.setLocationRelativeTo(null);
        //tp.startGameThread();
        mainFrame.setVisible(true);
    }
    
}
