package GUI;

import GUI.Tetris.MainMenuPanel;
import GUI.Tetris.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class UI {
//    private MainMenuPanel fp;
//    private GamePanel tp;
//    private JFrame mainFrame;
//    private Timer timer;
   
    public static void main(String[] args) {

        JFrame mainFrame = new JFrame("JTetris");

        // ÍCONO DE LA APLICACIÓN
        try {
            mainFrame.setIconImage(ImageIO.read(new File("icon/jtetris-icon.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        MainMenuPanel mmp = new MainMenuPanel();
        GamePanel gp = new GamePanel();

        mainFrame.add(mmp);
        mmp.addActionForButton(e -> {
            mainFrame.remove(mmp);
            mainFrame.add(gp);
            mainFrame.revalidate();
            gp.startGameThread();
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
        //gp.startGameThread();
        mainFrame.setVisible(true);
    }
}
