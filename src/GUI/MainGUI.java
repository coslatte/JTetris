package GUI;

import javax.swing.*;

public class MainGUI {
    public static void main(String[] args) {

        JFrame mainFrame = new JFrame("JTetris test application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        TetrisPanel tp = new TetrisPanel();
        mainFrame.add(tp);
        mainFrame.pack();

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}
