package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceRuntimeError;
import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;

public class ExitStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final int code = stack.pop().getInt();
        if (code != 0) {
            throw new AliceRuntimeError("stacksize: " + stack.size());
        }
        System.exit(stack.pop().getInt());
    }

    @Override
    public String toString() {
        return "exit()";
    }
}
