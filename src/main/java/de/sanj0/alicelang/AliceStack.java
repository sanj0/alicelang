package de.sanj0.alicelang;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AliceStack {
    private List<StackElement<?>> elements;

    private AliceStack(final List<StackElement<?>> elements) {
        this.elements = elements;
    }
    public static AliceStack initialize(final int initialSize) {
        return new AliceStack(new ArrayList<>(initialSize));
    }

    public int size() {
        return elements.size();
    }

    public void push(final StackElement<?> e) {
        elements.add(e);
    }

    public StackElement<?> pop() {
        return elements.remove(elements.size() - 1);
    }

    public StackElement<?> pop(final int offset) {
        return elements.remove(elements.size() - 1 - offset);
    }

    public StackElement<?> peek() {
        return elements.get(elements.size() - 1);
    }

    public StackElement<?> peek(final int offset) {
        return elements.get(elements.size() - 1 - offset);
    }

    /**
     * Gets {@link #elements}.
     *
     * @return the value of {@link #elements}
     */
    public List<StackElement<?>> getElements() {
        return elements;
    }
}
