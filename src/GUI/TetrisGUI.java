package GUI;

import GUI.Panels.MainPanel;

import javax.swing.*;

public class TetrisGUI {
    public static void main(String[] args) {

        JFrame mainFrame = new JFrame("JTetris test application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        MainPanel tp = new MainPanel();
        mainFrame.add(tp);
        mainFrame.pack();

        mainFrame.setLocationRelativeTo(null);
        tp.startGameThread();
        mainFrame.setVisible(true);
    }
}
