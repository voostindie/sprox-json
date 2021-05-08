package nl.ulso.sprox.json.spotify;

import nl.ulso.sprox.Node;

import java.time.LocalDate;
import java.util.List;

/**
 * Sprox processor for Spotify API album data. This is a very simple processor that ignores most data.
 * <p>
 * This implementation creates an Artist object for each and every artist in the response. But only the first one on
 * album level is kept in the end.
 * </p>
 */
public class AlbumFactory {

    @Node("album")
    public Album createAlbum(@Node("name") String name, @Node("release_date") LocalDate releaseDate, Artist artist,
                             List<Track> tracks) {
        return new Album(name, releaseDate, artist, tracks);
    }

    @Node("artists")
    public Artist createArtist(@Node("name") String name) {
        return new Artist(name);
    }

    @Node("items")
    public Track createTrack(@Node("track_number") Integer trackNumber, @Node("name") String name) {
        return new Track(trackNumber, name);
    }
}
