package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;

// usage:
// local (var) [val]:[name]
public class ExportStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        PutOnTableStatement.nextIsGlobal = true;
    }

    @Override
    public String toString() {
        return "export";
    }
}
