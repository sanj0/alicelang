package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceRuntimeError;
import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;

public class PutOnTableStatement extends Statement {
    private final String key;

    public PutOnTableStatement(final String key) {
        this.key = key;
    }

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        if (Character.isUpperCase(key.charAt(0)) && table.containsKey(key)) {
            throw new AliceRuntimeError(AliceRuntimeError.WRITE_TO_CONST_ + key);
        }
        table.put(key, stack.pop());
    }
}
