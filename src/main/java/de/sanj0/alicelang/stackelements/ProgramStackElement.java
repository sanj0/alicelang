package de.sanj0.alicelang.stackelements;

import de.sanj0.alicelang.Program;
import de.sanj0.alicelang.StackElement;
import de.sanj0.alicelang.Statement;

/**
 * A stack elements that contains a {@link Program}.
 */
public class ProgramStackElement extends StackElement<Program> {
    public ProgramStackElement(final Program value) {
        super(value);
    }

    @Override
    public StackElement<Program> dublicate() {
        return new ProgramStackElement(new Program(getValue().getStatements(), getValue().file));
    }

    @Override
    public String toString() {
        if (getValue().getStatements().isEmpty()) return "()";
        final StringBuilder builder = new StringBuilder("(");
        for (final Statement s : getValue().getStatements()) {
            builder.append(s.toString()).append(" ");
        }
        builder.setLength(builder.length() - 1);
        return builder.append(")").toString();
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
