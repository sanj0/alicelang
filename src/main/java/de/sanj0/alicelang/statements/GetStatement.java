package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;

/**
 * Pops a value s and executes a {@link PushFromTableStatement}
 * with s as the key.
 */
public class GetStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        new PushFromTableStatement(stack.pop().getString()).execute(stack, table);
    }
}
