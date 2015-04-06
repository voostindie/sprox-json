package nl.ulso.sprox.json;

import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

/**
 *
 */
class JsonCharactersEvent extends JsonEvent implements Characters {
    private final String data;

    JsonCharactersEvent(String data) {
        this.data = data;
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public boolean isWhiteSpace() {
        return false;
    }

    @Override
    public boolean isCData() {
        return false;
    }

    @Override
    public boolean isIgnorableWhiteSpace() {
        return false;
    }

    @Override
    public int getEventType() {
        return XMLEvent.CHARACTERS;
    }

    @Override
    public boolean isCharacters() {
        return true;
    }

    @Override
    public Characters asCharacters() {
        return this;
    }

    @Override
    public String toString() {
        return "CHARACTERS: " + data;
    }
}
