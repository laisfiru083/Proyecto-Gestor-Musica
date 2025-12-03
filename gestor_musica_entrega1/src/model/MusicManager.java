package model;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class MusicManager {
    private List<Playlist> playlists = new ArrayList<>();
    private List<Song> songs = new ArrayList<>();
    private List<Artist> artists = new ArrayList<>();

    public MusicManager() {}

    public List<Playlist> getPlaylists() { return playlists; }
    public List<Song> getSongs() { return songs; }
    public List<Artist> getArtists() { return artists; }

    public Playlist createPlaylist(String name) {
        Playlist p = new Playlist(UUID.randomUUID().toString(), name);
        playlists.add(p);
        return p;
    }

    public Artist createArtist(String name) {
        Artist a = new Artist(UUID.randomUUID().toString(), name);
        artists.add(a);
        return a;
    }

    public Song createSong(String title, Artist artist, int durationSec) {
        Song s = new Song(UUID.randomUUID().toString(), title, artist);
        s.setDurationSec(durationSec);
        songs.add(s);
        return s;
    }

    public void addSongToPlaylist(Song s, Playlist p) {
        if (s != null && p != null) p.addSong(s);
    }

    // Persistence: simple CSV formats in a folder
    public void loadAllIfExists(String folder) {
        try {
            Path base = Paths.get(folder);
            if (!Files.exists(base)) return;
            // artists
            Path artistsFile = base.resolve("artists.txt");
            if (Files.exists(artistsFile)) {
                List<String> lines = Files.readAllLines(artistsFile);
                for (String l : lines) {
                    if (l.trim().isEmpty() || l.startsWith("#")) continue;
                    String[] parts = l.split("\\|", -1);
                    if (parts.length >= 2) {
                        Artist a = new Artist(parts[0], parts[1]);
                        if (parts.length >=3) a.setCountry(parts[2].isEmpty()?null:parts[2]);
                        artists.add(a);
                    }
                }
            }
            // songs
            Path songsFile = base.resolve("songs.txt");
            Map<String, Artist> artistById = new HashMap<>();
            for (Artist a : artists) artistById.put(a.getId(), a);
            if (Files.exists(songsFile)) {
                List<String> lines = Files.readAllLines(songsFile);
                for (String l : lines) {
                    if (l.trim().isEmpty() || l.startsWith("#")) continue;
                    String[] parts = l.split("\\|", -1);
                    if (parts.length >= 3) {
                        String id = parts[0];
                        String title = parts[1];
                        String artistId = parts[2];
                        int dur = 0;
                        if (parts.length >=4) {
                            try { dur = Integer.parseInt(parts[3]); } catch(Exception e) {}
                        }
                        Artist a = artistById.get(artistId);
                        Song s = new Song(id, title, a);
                        s.setDurationSec(dur);
                        songs.add(s);
                    }
                }
            }
            // playlists
            Path playlistsFile = base.resolve("playlists.txt");
            Map<String, Song> songById = new HashMap<>();
            for (Song s : songs) songById.put(s.getId(), s);
            if (Files.exists(playlistsFile)) {
                List<String> lines = Files.readAllLines(playlistsFile);
                for (String l : lines) {
                    if (l.trim().isEmpty() || l.startsWith("#")) continue;
                    String[] parts = l.split("\\|", -1);
                    if (parts.length >= 2) {
                        String id = parts[0];
                        String name = parts[1];
                        Playlist p = new Playlist(id, name);
                        if (parts.length >= 3 && !parts[2].isEmpty()) {
                            String[] songIds = parts[2].split(",");
                            for (String sid : songIds) {
                                Song s = songById.get(sid);
                                if (s != null) p.addSong(s);
                            }
                        }
                        playlists.add(p);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAll(String folder) {
        try {
            Path base = Paths.get(folder);
            if (!Files.exists(base)) Files.createDirectories(base);
            // artists
            Path artistsFile = base.resolve("artists.txt");
            try (BufferedWriter w = Files.newBufferedWriter(artistsFile)) {
                for (Artist a : artists) {
                    w.write(String.join("|", a.getId(), a.getName(), a.getCountry()==null?"":a.getCountry()));
                    w.newLine();
                }
            }
            // songs
            Path songsFile = base.resolve("songs.txt");
            try (BufferedWriter w = Files.newBufferedWriter(songsFile)) {
                for (Song s : songs) {
                    w.write(String.join("|", s.getId(), s.getTitle(), s.getArtist()==null?"":s.getArtist().getId(), Integer.toString(s.getDurationSec())));
                    w.newLine();
                }
            }
            // playlists
            Path playlistsFile = base.resolve("playlists.txt");
            try (BufferedWriter w = Files.newBufferedWriter(playlistsFile)) {
                for (Playlist p : playlists) {
                    StringJoiner sj = new StringJoiner(",");
                    for (Song s : p.getSongs()) sj.add(s.getId());
                    w.write(String.join("|", p.getId(), p.getName(), sj.toString()));
                    w.newLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
