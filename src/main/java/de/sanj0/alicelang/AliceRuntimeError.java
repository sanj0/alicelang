package de.sanj0.alicelang;

public class AliceRuntimeError extends RuntimeException {
    public static final String ARITHMETIC_ERROR_ = "arithmetic error ";
    public static final String WRITE_TO_CONST_ = "cannot write to constant ";
    public static String INVALID_TYPE_ = "invalid type ";
    public AliceRuntimeError(final String message) {
        super(AliceParser.currentFile + ": " + message);
    }
}
