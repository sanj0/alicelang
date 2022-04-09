package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;

// usage:
// local (var) [val]:[name]
public class LocalStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        PutOnTableStatement.nextIsLocal = true;
    }
}
