package model;

public class Artist {
    private String id;
    private String name;
    private String country;

    public Artist(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setCountry(String country) { this.country = country; }
    public String getCountry() { return country; }

    @Override
    public String toString() {
        return name + (country != null ? " ("+country+")" : "");
    }
}
