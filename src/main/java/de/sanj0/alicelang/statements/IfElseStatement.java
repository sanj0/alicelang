package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.NumberStackElement;
import de.sanj0.alicelang.stackelements.ProgramStackElement;

/**
 * condition (subprogram branch true) (subprogram branch false) ifelse
 */
public class IfElseStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final StackElement<?> elseBody = stack.pop();
        final StackElement<?> ifBody = stack.pop();
        final StackElement<?> condition = stack.pop();
        if (!(elseBody instanceof ProgramStackElement && ifBody instanceof ProgramStackElement && condition instanceof NumberStackElement)) {
            throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + " if excepts two subprograms and a number");
        }

        if (condition.getDouble() != 0) {
            ((ProgramStackElement) ifBody).getValue().execute(stack, table);
        } else {
            ((ProgramStackElement) elseBody).getValue().execute(stack, table);
        }
    }

    @Override
    public String toString() {
        return "ifElse()";
    }
}
