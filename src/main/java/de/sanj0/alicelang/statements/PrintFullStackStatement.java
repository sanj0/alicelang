package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.StackElement;
import de.sanj0.alicelang.Statement;

import java.util.ListIterator;

/**
 * Prints the full stack.
 */
public class PrintFullStackStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final ListIterator<StackElement<?>> itr = stack.getElements().listIterator(stack.size());
        while (itr.hasPrevious()) {
            System.out.println(itr.previous().toString().replaceAll("\n", "\\n"));
        }
    }

    @Override
    public String toString() {
        return "printFullStack()";
    }
}
