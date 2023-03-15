package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.AlicePathStackElement;
import de.sanj0.alicelang.stackelements.ProgramStackElement;

public class FunctionCallStatement extends Statement {
    private final String type;

    public FunctionCallStatement(final String type) {
        this.type = type;
    }

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final StackElement<?> path = stack.pop();
        if (!(path instanceof AlicePathStackElement)) {
            throw new AliceRuntimeError("@ function call expects AlicePath element on stack");
        }
        final StackElement<?> function = table.get(path.toString());
        if (!(function instanceof ProgramStackElement)) {
            throw new AliceRuntimeError(path + " is not a function!");
        }
        ((ProgramStackElement) function).getValue().execute(stack, table);
    }

    @Override
    public String toString() {
        return AliceParser.PREFIX_FUNCTION_CALL + type;
    }
}
