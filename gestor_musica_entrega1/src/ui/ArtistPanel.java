package ui;

import model.Artist;
import model.MusicManager;

import javax.swing.*;
import java.awt.*;

public class ArtistPanel extends JPanel {
    private JList<Artist> artistJList;
    private DefaultListModel<Artist> listModel;
    private MusicManager manager;

    public ArtistPanel(MusicManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Artistas"));

        listModel = new DefaultListModel<>();
        artistJList = new JList<>(listModel);
        add(new JScrollPane(artistJList), BorderLayout.CENTER);

        JPanel south = new JPanel();
        JButton btnNew = new JButton("Nuevo artista");
        south.add(btnNew);
        add(south, BorderLayout.SOUTH);

        manager.getArtists().forEach(listModel::addElement);

        btnNew.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Nombre del artista:");
            if (name != null && !name.trim().isEmpty()) {
                String country = JOptionPane.showInputDialog(this, "País (opcional):");
                Artist a = manager.createArtist(name.trim());
                if (country != null && !country.trim().isEmpty()) a.setCountry(country.trim());
                listModel.addElement(a);
            }
        });
    }
}
