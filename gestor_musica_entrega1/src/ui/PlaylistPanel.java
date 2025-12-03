package ui;

import model.MusicManager;
import model.Playlist;
import model.Song;

import javax.swing.*;
import java.awt.*;

public class PlaylistPanel extends JPanel {
    private JList<Playlist> playlistJList;
    private DefaultListModel<Playlist> listModel;
    private MusicManager manager;

    public PlaylistPanel(MusicManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Listas de Reproducción"));

        listModel = new DefaultListModel<>();
        playlistJList = new JList<>(listModel);
        add(new JScrollPane(playlistJList), BorderLayout.CENTER);

        JPanel south = new JPanel();
        JButton btnNew = new JButton("Nueva lista");
        JButton btnAddSong = new JButton("Agregar canción");
        JButton btnView = new JButton("Ver canciones");
        south.add(btnNew);
        south.add(btnAddSong);
        south.add(btnView);
        add(south, BorderLayout.SOUTH);

        manager.getPlaylists().forEach(listModel::addElement);

        btnNew.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Nombre de la nueva lista:");
            if (name != null && !name.trim().isEmpty()) {
                Playlist p = manager.createPlaylist(name.trim());
                listModel.addElement(p);
            }
        });

        btnAddSong.addActionListener(e -> {
            Playlist selected = playlistJList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Seleccione primero una lista.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // ask user to choose a song from existing songs
            java.util.List<Song> songs = manager.getSongs();
            if (songs.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay canciones disponibles. Cree una canción primero.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            SongChooserDialog scd = new SongChooserDialog(SwingUtilities.getWindowAncestor(this), songs);
            Song chosen = scd.getSelectedSong();
            if (chosen != null) {
                manager.addSongToPlaylist(chosen, selected);
                // refresh selected entry to show count change
                playlistJList.repaint();
            }
        });

        btnView.addActionListener(e -> {
            Playlist selected = playlistJList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una lista.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (Song s : selected.getSongs()) {
                sb.append(s.toString()).append("\n");
            }
            if (sb.length() == 0) sb.append("(Vacía)");
            JOptionPane.showMessageDialog(this, sb.toString(), "Canciones en " + selected.getName(), JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
