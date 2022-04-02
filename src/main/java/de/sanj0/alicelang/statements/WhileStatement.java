package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.ProgramStackElement;

/**
 * usage:
 * (condition) {body} do
 * pops both before first iteration
 * before every iteration, condition is executed and throw topmost
 * stack element popped and checked against 0.
 */
public class WhileStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final StackElement<?> bodyElement = stack.pop();
        final StackElement<?> condElement = stack.pop();
        if (!(bodyElement instanceof ProgramStackElement && condElement instanceof ProgramStackElement)) {
            throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + "while expects two subprograms");
        }
        final Program body = (Program) bodyElement.getValue();
        final Program cond = (Program) condElement.getValue();
        cond.execute(stack, table);
        while (stack.pop().getInt() != 0) {
            body.execute(stack, table);
            if (body.thenBreak) {
                body.thenBreak = false;
                break;
            }
            if (body.thenReturn) {
                this.thenReturn = !(body.thenReturn = false);
                return;
            }
            cond.execute(stack, table);
        }
    }

    @Override
    public String toString() {
        return "while()";
    }
}
