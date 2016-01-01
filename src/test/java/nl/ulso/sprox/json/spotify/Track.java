package nl.ulso.sprox.json.spotify;

public class Track {
    private final int trackNumber;
    private final String title;

    public Track(int trackNumber, String title) {
        this.trackNumber = trackNumber;
        this.title = title;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public String getTitle() {
        return title;
    }
}
