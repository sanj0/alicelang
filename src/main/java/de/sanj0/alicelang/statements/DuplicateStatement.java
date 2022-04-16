package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.NumberStackElement;
import de.sanj0.alicelang.stackelements.ProgramStackElement;
import de.sanj0.alicelang.stackelements.StringStackElement;
import de.sanj0.alicelang.stackelements.Substack;

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
        if (e instanceof NumberStackElement) {
            return new NumberStackElement(e.getDouble());
        } else if (e instanceof StringStackElement) {
            return new StringStackElement(e.getString());
        } else if (e instanceof ProgramStackElement) {
            return new ProgramStackElement(new Program(new LinkedList<>(((ProgramStackElement) e).getValue().getStatements())));
        } else if (e instanceof Substack) {
            final AliceStack src = ((Substack) e).getValue();
            final AliceStack dst = AliceStack.initialize(src.size());
            dst.getElements().addAll(src.getElements());
            return new Substack(dst);
        }
        return null;
    }

    @Override
    public String toString() {
        return "duplicate()";
    }
}
