package nl.ulso.sprox.json;

import javax.xml.stream.events.StartDocument;

class StartJsonDocumentEvent extends JsonEvent implements StartDocument {

    @Override
    public String getSystemId() {
        return "";
    }

    @Override
    public String getCharacterEncodingScheme() {
        return "UTF-8";
    }

    @Override
    public boolean encodingSet() {
        return false;
    }

    @Override
    public boolean isStandalone() {
        return true;
    }

    @Override
    public boolean standaloneSet() {
        return false;
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public int getEventType() {
        return START_DOCUMENT;
    }

    @Override
    public boolean isStartDocument() {
        return true;
    }

}
