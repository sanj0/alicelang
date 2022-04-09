package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceRuntimeError;
import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;

public class PutOnTableStatement extends Statement {

    public static boolean nextIsLocal = false;

    private final String key;

    public PutOnTableStatement(final String key) {
        this.key = key;
    }

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        if (Character.isUpperCase(key.charAt(0)) && table.containsKey(key)) {
            throw new AliceRuntimeError(AliceRuntimeError.WRITE_TO_CONST_ + key);
        }
        if (nextIsLocal) {
            table.putLocal(key, stack.pop());
        } else {
            table.put(key, stack.pop());
            nextIsLocal = false;
        }
    }

    @Override
    public String toString() {
        return ":" + key;
    }
}
