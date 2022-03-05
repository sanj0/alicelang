package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.ProgramStackElement;

/**
 * Executes if the element is a subprogram and pushes if it's not.
 */
public class PushFromTableStatement extends Statement {
    private final String key;

    public PushFromTableStatement(final String key) {
        this.key = key;
    }

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final StackElement<?> e = table.getOrDefault(key, null);
        if (e == null) throw new AliceRuntimeError("word not found in table: " + key);
        if (e instanceof ProgramStackElement) {
            ((ProgramStackElement) e).getValue().execute(stack, table);
        } else {
            stack.push(table.get(key));
        }
    }

    @Override
    public String toString() {
        return "accessTable()";
    }
}
