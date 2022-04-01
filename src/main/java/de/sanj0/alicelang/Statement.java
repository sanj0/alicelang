package de.sanj0.alicelang;

public abstract class Statement {
    public int lineNumber;
    public int startIndex;
    public boolean thenBreak = false;
    public boolean thenReturn = false;

    public abstract void execute(final AliceStack stack, final AliceTable table);
    // default implementation returns this
    public Statement copy() {
        return this;
    }
}
