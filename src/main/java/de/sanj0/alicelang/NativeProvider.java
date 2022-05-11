package de.sanj0.alicelang;

// provides native statements
// to be included using "ninclude" and
// executed using "native".
public interface NativeProvider {
    // returns true if the given word is provided, false if it isn't
    boolean execute(final String word, final AliceStack stack, final AliceTable table);
}
