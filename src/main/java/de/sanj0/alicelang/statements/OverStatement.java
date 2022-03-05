package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;

public class OverStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        stack.push(DuplicateStatement.duplicate(stack.peek(1), table));
    }

    @Override
    public String toString() {
        return "over()";
    }
}
