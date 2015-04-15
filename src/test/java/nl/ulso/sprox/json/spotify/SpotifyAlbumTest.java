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

import nl.ulso.sprox.XmlProcessor;
import nl.ulso.sprox.impl.StaxBasedXmlProcessorBuilderFactory;
import nl.ulso.sprox.json.JsonXmlInputFactory;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SpotifyAlbumTest {

    @Test
    public void testSpotifyAlbum() throws Exception {
        final XmlProcessor<Album> processor = new StaxBasedXmlProcessorBuilderFactory()
                .createXmlProcessorBuilder(Album.class)
                .setXmlInputFactory(new JsonXmlInputFactory("album"))
                .addParser((String dateString) ->
                        LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE), LocalDate.class)
                .addControllerClass(AlbumFactory.class)
                .buildXmlProcessor();

        final Album album = processor.execute(SpotifyAlbumTest.class.getResourceAsStream("/0Y3eZqsEK2g4T6ecqw8ucR.json"));

        assertThat(album, notNullValue());
        assertThat(album.getTitle(), is("Insurgentes"));
        assertThat(album.getArtist().getName(), is("Steven Wilson"));
        assertThat(album.getReleaseDate(), is(LocalDate.parse("2009-02-23")));
        assertThat(album.getTracks().size(), is(10));
        assertThat(album.getTracks().get(0).getTitle(), is("Harmony Korine"));
        assertThat(album.getTracks().get(9).getTitle(), is("Insurgentes"));
    }
}
