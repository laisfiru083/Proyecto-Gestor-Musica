package model;

public class Song {
    private String id;
    private String title;
    private int durationSec;
    private Artist artist;

    public Song(String id, String title, Artist artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public Artist getArtist() { return artist; }
    public int getDurationSec() { return durationSec; }
    public void setDurationSec(int s) { this.durationSec = s; }

    @Override
    public String toString() {
        return title + " - " + (artist != null ? artist.getName() : "Sin artista");
    }
}
