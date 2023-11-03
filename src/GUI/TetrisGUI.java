package GUI;

import GUI.Panels.MainPanel;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

public class TetrisGUI {
    public static void main(String[] args) {

        JFrame mainFrame = new JFrame("JTetris test application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        MainPanel tp = new MainPanel();
        mainFrame.add(tp);

        /*Acabo de usar la clase Toolkit de java para obtener las dimensiones de la pantalla y ponerlo para que aparezca en 
        coordenadas relativas al monitor
         * 
         */
        Dimension e = Toolkit.getDefaultToolkit().getScreenSize();
        /*
         * con esto se obtiene el ancho y largo de la pantalla y dividiendolo entre un numero t va a dar unas coordenadas relativas 
         * a cualquier monitor
         */
        mainFrame.setLocation((int)(e.getWidth()/6), (int)(e.getHeight()/6));
        mainFrame.setSize(650, 700 );
        mainFrame.setLocationRelativeTo(null);
        tp.startGameThread();
        mainFrame.setVisible(true);
    }
}
