package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;

// pops a string from the stack and purges the first
// table elements with this key
// example: "my-var" purge
public class PurgeStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final String key = stack.pop().getString();
        table.purgeFirst(key);
    }
}
