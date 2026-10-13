package GUI;

import GUI.Components.Panels.Menu.OptionsPanel;
import GUI.Components.Panels.Menu.MainMenuPanel;
import GUI.Components.Panels.Game.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UI {

    /**
     * Panel de juego que está en pantalla, o <code>null</code> si no hay ninguno. Se guarda para poder
     * detener su hilo al salir de la partida, porque si queda vivo seguiría pidiendo fotogramas al aire.
     */
    private static GamePanel currentGame;

    /**
     * Nivel con el que arranca la próxima partida. Se carga desde la pantalla de opciones.
     */
    private static int startLevel = 1;

    public static void main(String[] args) {

        JFrame mainFrame = new JFrame("JTetris");

        // jtetris icon
        BufferedImage icon = loadIcon();
        if (icon != null) {
            mainFrame.setIconImage(icon);
        }

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        MainMenuPanel mainMenuPanel = new MainMenuPanel();

        show(mainFrame, mainMenuPanel);

        // PLAY Button
        mainMenuPanel.addActionForButtonPlay(e -> {
            // Cada partida arranca con su propio panel, para no arrastrar nada de la anterior.
            stopGame();
            GamePanel game = new GamePanel();
            game.setStartLevel(startLevel);
            currentGame = game;

            show(mainFrame, game);
            game.startGameThread();

            // El teclado lo escucha el panel del juego, así que hay que darle el foco apenas aparece.
            SwingUtilities.invokeLater(game::requestFocusInWindow);
        });

        // OPTIONS Button
        mainMenuPanel.addActionForButtonOptions(e -> {
            OptionsPanel optionsPanel = new OptionsPanel();
            show(mainFrame, optionsPanel);

            optionsPanel.addActionForBack(e2 -> {
                // El nivel elegido queda guardado para la próxima partida.
                startLevel = optionsPanel.getStartLevel();
                show(mainFrame, mainMenuPanel);
            });
        });

        // EXIT Button
        mainMenuPanel.addActionForButtonExit(e -> System.exit(1));

        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
    }

    /**
     * Cambia el panel que está mostrando la ventana.
     * <p>
     * Se usa pack() en vez de un tamaño fijo porque cada panel declara el tamaño que necesita, y así la
     * ventana siempre queda justa. Como los tres paneles piden los mismos pixeles, la ventana tampoco
     * cambia de tamaño al pasar de una pantalla a otra.
     */
    private static void show(JFrame frame, JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    /**
     * Detiene el hilo de juego si había uno corriendo.
     */
    private static void stopGame() {
        if (currentGame != null) {
            currentGame.stopGameThread();
            currentGame = null;
        }
    }

    /**
     * Carga el ícono de la aplicación. Si el archivo no está, se sigue igual con el ícono por defecto de
     * Java en vez de tirar el programa abajo.
     *
     * @return La imagen del ícono, o <code>null</code> si no se pudo leer.
     */
    private static BufferedImage loadIcon() {
        File file = new File("icon/jtetris-icon.png");
        if (!file.exists()) {
            System.out.println("No se encontro el icono " + file.getPath() + ", se usara el de Java.");
            return null;
        }
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("No se pudo leer el icono: " + e.getMessage());
            return null;
        }
    }
}
