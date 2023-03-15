package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.*;

import java.util.LinkedList;

/**
 * Peeks the stack and pushes a duplicate of the peeked element
 */
public class DuplicateStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        stack.push(duplicate(stack.peek()));
    }

    public static StackElement<?> duplicate(final StackElement<?> e) {
        return e.dublicate();
    }

    @Override
    public String toString() {
        return "duplicate()";
    }
}
