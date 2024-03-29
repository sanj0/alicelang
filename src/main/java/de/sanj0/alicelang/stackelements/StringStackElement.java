package de.sanj0.alicelang.stackelements;

import de.sanj0.alicelang.StackElement;

/**
 * Stack element containing a String.
 */
public class StringStackElement extends StackElement<String> {
    public StringStackElement(final String value) {
        super(value);
    }

    @Override
    public StackElement<String> dublicate() {
        return new StringStackElement(getValue());
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
