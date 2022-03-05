package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;
import de.sanj0.alicelang.stackelements.NumberStackElement;

/**
 * Does the popped element exist on the table - yes=1 no=0.
 */
public class ExistsStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        stack.push(new NumberStackElement(table.containsKey(stack.pop().getValue()) ? 1d : 0d));
    }
}
