package de.sanj0.alicelang.stackelements;

import de.sanj0.alicelang.StackElement;

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
            return String.valueOf(getValue()).replace(".0", "");
        } else {
            return String.valueOf(getValue());
        }
    }
}
