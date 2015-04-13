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
