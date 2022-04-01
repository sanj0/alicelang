package de.sanj0.alicelang.stackelements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.StackElement;
import de.sanj0.alicelang.Statement;

public class Substack extends StackElement<AliceStack> {
    public Substack(final AliceStack value) {
        super(value);
    }

    @Override
    public String toString() {
        if (getValue().getElements().isEmpty()) return "[]";
        final StringBuilder builder = new StringBuilder("[");
        for (final StackElement s : getValue().getElements()) {
            builder.append(s.toString()).append(" ");
        }
        builder.setLength(builder.length() - 1);
        return builder.append("]").toString();
    }
}
