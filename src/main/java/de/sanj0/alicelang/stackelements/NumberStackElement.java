package de.sanj0.alicelang.stackelements;

import de.sanj0.alicelang.StackElement;

import java.util.Locale;

/**
 * A stack element holding a number of type double.
 */
public class NumberStackElement extends StackElement<Double> {
    public NumberStackElement(final Double value) {
        super(value);
    }

    @Override
    public int getInt() {
        return (int) getDouble();
    }

    @Override
    public String toString() {
        if (getDouble() % 1 == 0) {
            return String.format("%.0f", getValue());
        } else {
            return String.format(Locale.US, "%f", getValue());
        }
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
