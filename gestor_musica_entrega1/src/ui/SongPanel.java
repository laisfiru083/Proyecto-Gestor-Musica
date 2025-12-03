package ui;

import model.MusicManager;
import model.Song;
import model.Artist;

import javax.swing.*;
import java.awt.*;

public class SongPanel extends JPanel {
    private JList<Song> songJList;
    private DefaultListModel<Song> listModel;
    private MusicManager manager;

    public SongPanel(MusicManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Canciones"));

        listModel = new DefaultListModel<>();
        songJList = new JList<>(listModel);
        add(new JScrollPane(songJList), BorderLayout.CENTER);

        JPanel south = new JPanel();
        JButton btnNew = new JButton("Nueva canción");
        south.add(btnNew);
        add(south, BorderLayout.SOUTH);

        manager.getSongs().forEach(listModel::addElement);

        btnNew.addActionListener(e -> {
            String title = JOptionPane.showInputDialog(this, "Título de la canción:");
            if (title == null || title.trim().isEmpty()) return;
            java.util.List<Artist> artists = manager.getArtists();
            if (artists.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay artistas. Cree un artista primero.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            // choose artist
            Artist chosen = (Artist) JOptionPane.showInputDialog(this, "Seleccione artista:", "Artista", JOptionPane.PLAIN_MESSAGE, null, artists.toArray(), artists.get(0));
            if (chosen == null) return;
            String durStr = JOptionPane.showInputDialog(this, "Duración en segundos (opcional):");
            int dur = 0;
            try { if (durStr != null && !durStr.trim().isEmpty()) dur = Integer.parseInt(durStr.trim()); } catch(Exception ex) { dur = 0; }
            Song s = manager.createSong(title.trim(), chosen, dur);
            listModel.addElement(s);
        });
    }
}
