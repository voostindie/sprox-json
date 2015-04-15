/*
 * Copyright 2015 Vincent Oostindië
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

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
    public Album createAlbum(@Node String name, @Node("release_date") LocalDate releaseDate, Artist artist,
                             List<Track> tracks) {
        return new Album(name, releaseDate, artist, tracks);
    }

    @Node("artists")
    public Artist createArtist(@Node String name) {
        return new Artist(name);
    }

    @Node("items")
    public Track createTrack(@Node("track_number") Integer trackNumber, @Node String name) {
        return new Track(trackNumber, name);
    }
}
