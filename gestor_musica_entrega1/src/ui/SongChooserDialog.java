package ui;

import model.Song;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SongChooserDialog extends JDialog {
    private Song selected = null;

    public SongChooserDialog(Window owner, List<Song> songs) {
        super(owner, "Elegir canción", ModalityType.APPLICATION_MODAL);
        setSize(400,300);
        setLayout(new BorderLayout());
        DefaultListModel<Song> dm = new DefaultListModel<>();
        for (Song s : songs) dm.addElement(s);
        JList<Song> jl = new JList<>(dm);
        add(new JScrollPane(jl), BorderLayout.CENTER);
        JPanel p = new JPanel();
        JButton ok = new JButton("Seleccionar");
        JButton cancel = new JButton("Cancelar");
        p.add(ok); p.add(cancel);
        add(p, BorderLayout.SOUTH);

        ok.addActionListener(e -> {
            selected = jl.getSelectedValue();
            dispose();
        });
        cancel.addActionListener(e -> {
            selected = null;
            dispose();
        });

        setLocationRelativeTo(owner);
        setVisible(true);
    }

    public Song getSelectedSong() { return selected; }
}
