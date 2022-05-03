package de.sanj0.alicelang;

import java.io.Serializable;
import java.util.Objects;

public class StackElement<T> implements Serializable {
    private T value;

    public StackElement(final T value) {
        this.value = value;
    }

    public double getDouble() {
        return (double) value;
    }

    public int getInt() {
        return Integer.parseInt(String.valueOf(value));
    }

    public String getString() {
        return String.valueOf(value);
    }

    /**
     * Gets {@link #value}.
     *
     * @return the value of {@link #value}
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets {@link #value}.
     *
     * @param value the new value of {@link #value}
     */
    public void setValue(final T value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof StackElement)) return false;
        StackElement<?> that = (StackElement<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
