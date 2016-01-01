package nl.ulso.sprox.json;

public final class JsonXmlConstants {

    /**
     * Null values in the input are mapped to this String value. This is necessary because XML doesn't know the
     * concept of null values.
     * <p>
     * The literal value here aims to be unlikely to be used in real-world scenario's, but still easily identifiable
     * for debugging purposes.
     * </p>
     */
    public static final String NULL_VALUE = "-<[:=NULL=:]>-";

    private JsonXmlConstants() {
    }
}
