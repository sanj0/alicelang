package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;

/**
 * Returns out of the subprogram that was called by
 * stating its word.
 */
public class ReturnStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        thenReturn = true;
    }

    @Override
    public String toString() {
        return "return";
    }
}
