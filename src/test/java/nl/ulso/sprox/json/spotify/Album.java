package nl.ulso.sprox.json.spotify;

import java.time.LocalDate;
import java.util.List;

public class Album {

    private final String title;
    private final LocalDate releaseDate;
    private final Artist artist;
    private final List<Track> tracks;

    public Album(String title, LocalDate releaseDate, Artist artist, List<Track> tracks) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.artist = artist;
        this.tracks = tracks;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Artist getArtist() {
        return artist;
    }

    public List<Track> getTracks() {
        return tracks;
    }
}
