package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;

// drops the top most element from the stack.
public class DropStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        stack.pop();
    }

    @Override
    public String toString() {
        return "drop()";
    }
}
