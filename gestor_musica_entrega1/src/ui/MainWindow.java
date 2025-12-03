package ui;

import model.MusicManager;
import javax.swing.*;
import java.awt.*;

public class MainWindow {
    private JFrame frame;
    private PlaylistPanel playlistPanel;
    private ArtistPanel artistPanel;
    private SongPanel songPanel;
    private MusicManager manager;

    public MainWindow(MusicManager manager) {
        this.manager = manager;
        frame = new JFrame("Gestor de Colección de Música");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 550);
        frame.setLayout(new BorderLayout());

        JPanel main = new JPanel(new GridLayout(1,3));
        playlistPanel = new PlaylistPanel(manager);
        artistPanel = new ArtistPanel(manager);
        songPanel = new SongPanel(manager);

        main.add(playlistPanel);
        main.add(artistPanel);
        main.add(songPanel);

        frame.add(main, BorderLayout.CENTER);

        // add window close hook to save data
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                manager.saveAll("data");
                super.windowClosing(e);
            }
        });
    }

    public void show() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }
}
