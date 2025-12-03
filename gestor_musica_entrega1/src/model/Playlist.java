package model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String id;
    private String name;
    private List<Song> songs = new ArrayList<>();

    public Playlist(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Song> getSongs() { return songs; }

    public void addSong(Song s) { if (s != null && !songs.contains(s)) songs.add(s); }
    public void removeSong(Song s) { songs.remove(s); }

    @Override
    public String toString() { return name + " ("+songs.size()+" canciones)"; }
}
