package de.sanj0.alicelang;

public abstract class Statement {
    public int lineNumber;

    public abstract void execute(final AliceStack stack, final AliceTable table);
}
