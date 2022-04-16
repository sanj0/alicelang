package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;
// 0 fetch does nothing
// in a stack [1 0], 1 fetch reverses stack order
public class PollStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final int offset = stack.pop().getInt();
        if (offset > 0) {
            stack.push(stack.pop(offset));
        }
    }
}
