package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.StackElement;
import de.sanj0.alicelang.Statement;

/**
 * A statement that pushes a value onto the stack.
 */
public class PushStatement<T extends StackElement<?>> extends Statement {
    private T value;

    public PushStatement(final T value) {
        this.value = value;
    }

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        stack.push(value);
    }

    /**
     * Gets {@link #value}.
     *
     * @return the value of {@link #value}
     */
    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "push(" + value + ")";
    }
}
