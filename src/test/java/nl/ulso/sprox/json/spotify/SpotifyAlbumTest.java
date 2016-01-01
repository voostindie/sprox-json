package nl.ulso.sprox.json.spotify;

import nl.ulso.sprox.XmlProcessor;
import nl.ulso.sprox.impl.StaxBasedXmlProcessorBuilderFactory;
import nl.ulso.sprox.json.JsonXmlInputFactory;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_DATE;
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
                        parse(dateString, ISO_DATE), LocalDate.class)
                .addControllerClass(AlbumFactory.class)
                .buildXmlProcessor();

        final Album album = processor.execute(
                SpotifyAlbumTest.class.getResourceAsStream("/spotify/0Y3eZqsEK2g4T6ecqw8ucR.json"));

        assertThat(album, notNullValue());
        assertThat(album.getTitle(), is("Insurgentes"));
        assertThat(album.getArtist().getName(), is("Steven Wilson"));
        assertThat(album.getReleaseDate(), is(parse("2009-02-23")));
        assertThat(album.getTracks().size(), is(10));
        assertThat(album.getTracks().get(0).getTitle(), is("Harmony Korine"));
        assertThat(album.getTracks().get(9).getTitle(), is("Insurgentes"));
    }
}
