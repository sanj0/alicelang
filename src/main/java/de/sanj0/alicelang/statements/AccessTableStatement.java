package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.ProgramStackElement;
import de.sanj0.alicelang.stackelements.StringStackElement;
import de.sanj0.alicelang.stackelements.StructInstance;

/**
 * Executes if the element is a subprogram and pushes if it's not.
 */
public class AccessTableStatement extends Statement {
    public static boolean nextIsImplement = false;
    private final String key;

    public AccessTableStatement(final String key) {
        this.key = key;
    }

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final StackElement<?> e = table.getOrDefault(key, null);
        if (nextIsImplement) {
            if (!table.getStructs().containsKey(key)) {
                throw new AliceRuntimeError("cannot implement for unknown struct " + key);
            }
            stack.push(new StringStackElement(key));
            nextIsImplement = false;
            return;
        }
        if (table.getStructs().containsKey(key)) {
            stack.push(table.getStructs().get(key).instantiate(stack, table));
            return;
        }

        if (e == null) throw new AliceRuntimeError("word not found in table: " + key);
        if (e instanceof ProgramStackElement) {
            final ProgramStackElement programStackElement = ((ProgramStackElement) e);
            programStackElement.getValue().isFunctionCall = true;
            programStackElement.getValue().execute(stack, table);
            thenReturn = false;
        } else {
            stack.push(table.get(key));
        }
    }

    /**
     * Gets {@link #key}.
     *
     * @return the value of {@link #key}
     */
    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "table(" + key + ")";
    }
}
