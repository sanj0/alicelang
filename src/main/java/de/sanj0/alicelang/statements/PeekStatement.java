package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;

// pops an int and copies stack element at that offset to the top of the stack
public class PeekStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final int offset = stack.pop().getInt();
        stack.push(DuplicateStatement.duplicate(stack.peek(offset)));
    }
}
