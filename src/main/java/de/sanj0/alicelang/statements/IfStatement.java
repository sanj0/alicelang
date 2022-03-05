package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.NumberStackElement;
import de.sanj0.alicelang.stackelements.ProgramStackElement;

/**
 * condition (subprogram) if
 */
public class IfStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final StackElement<?> body = stack.pop();
        final StackElement<?> condition = stack.pop();
        if (!(body instanceof ProgramStackElement && condition instanceof NumberStackElement)) {
            throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + " if excepts a subprogram and a number");
        }

        if (condition.getDouble() != 0) {
            ((ProgramStackElement) body).getValue().execute(stack, table);
        }
    }

    @Override
    public String toString() {
        return "if()";
    }
}
