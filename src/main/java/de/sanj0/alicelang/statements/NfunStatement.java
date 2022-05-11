package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;

public class NfunStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        PutOnTableStatement.nextIsNfun = true;
    }

    @Override
    public String toString() {
        return "nfun";
    }
}
