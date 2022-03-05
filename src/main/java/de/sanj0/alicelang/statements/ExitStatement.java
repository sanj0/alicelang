package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;

public class ExitStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        // todo: check if exit code != 0 and errprint "stack trace" if so
        System.exit(stack.pop().getInt());
    }

    @Override
    public String toString() {
        return "exit()";
    }
}
