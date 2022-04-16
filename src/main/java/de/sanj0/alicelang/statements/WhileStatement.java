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
        final Program body = (Program) stack.pop().getValue();
        final Program cond = (Program) stack.pop().getValue();
        cond.execute(stack, table);
        while (stack.pop().getDouble() != 0) {
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
