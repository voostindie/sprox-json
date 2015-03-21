package nl.ulso.sprox.json;

import javax.xml.stream.events.EndDocument;

/**
 *
 */
class EndJsonDocumentEvent extends JsonEvent implements EndDocument {

    @Override
    public int getEventType() {
        return END_DOCUMENT;
    }

    @Override
    public boolean isEndDocument() {
        return true;
    }
}
