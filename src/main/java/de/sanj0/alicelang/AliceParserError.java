package de.sanj0.alicelang;

public class AliceParserError extends RuntimeException {
    public static final String UNKNOWN_ESCAPE_SEQUENCE_ = "unknown escape sequence ";
    public static String HIT_END_OF_FILE = "hit end of file while parsing";

    public AliceParserError(final String message) {
        super(AliceParser.currentFile + ": " + message);
    }
}
