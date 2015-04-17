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