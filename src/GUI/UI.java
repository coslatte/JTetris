package GUI;

import GUI.Components.Panels.Menu.OptionsPanel;
import GUI.Components.Panels.Menu.MainMenuPanel;
import GUI.Components.Panels.Game.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class UI {
    public static void main(String[] args) {

        JFrame mainFrame = new JFrame("JTetris");

        // jtetris icon
        try {
            mainFrame.setIconImage(ImageIO.read(new File("icon/jtetris-icon.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(650, 700);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);

        MainMenuPanel mainMenuPanel = new MainMenuPanel();
        GamePanel gamePanel = new GamePanel();
        OptionsPanel optionsPanel = new OptionsPanel();

        mainFrame.add(mainMenuPanel);

        // PLAY Button
        mainMenuPanel.addActionForButtonPlay(e -> {
            mainFrame.remove(mainMenuPanel);
            mainFrame.add(gamePanel);
            mainFrame.revalidate();
            gamePanel.startGameThread();
        });

        // OPTIONS Button
        mainMenuPanel.addActionForButtonOptions(e -> {
            mainFrame.remove(mainMenuPanel);
            mainFrame.add(optionsPanel);
            mainFrame.revalidate();
        });

        // EXIT Button
        mainMenuPanel.addActionForButtonExit(e -> System.exit(1));

        mainFrame.setVisible(true);
    }
}
