package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;

/**
 * Prints a single stack element
 */
public class PrintStatement extends Statement {
    private boolean pop;

    public PrintStatement(final boolean pop) {
        this.pop = pop;
    }

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        System.out.print(pop ? stack.pop() : stack.peek());
    }

    @Override
    public String toString() {
        return (pop ? "P" : "p") + "rint";
    }
}
