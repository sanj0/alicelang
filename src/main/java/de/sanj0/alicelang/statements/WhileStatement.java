package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.NumberStackElement;
import de.sanj0.alicelang.stackelements.ProgramStackElement;

/**
 * usage:
 * (condition) {body} do
 * pops both before first iteration
 */
public class WhileStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final StackElement<?> body = stack.pop();
        final StackElement<?> condition = stack.peek();
        if (!(body instanceof ProgramStackElement) || !(condition instanceof NumberStackElement)) {
            throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + " while expects a subprogram and a number");
        }
        if (condition.getDouble() != 0) {
            final Program subprogram = ((ProgramStackElement) body).getValue();
            subprogram.thenReturn = false;
            subprogram.thenBreak = false;
            subprogram.execute(stack, table);
            if (subprogram.thenBreak) return;
            if (subprogram.thenReturn) {
                thenReturn = true;
                return;
            }
            while (true) {
                final StackElement<?> cond = stack.peek();
                if (!(cond instanceof NumberStackElement)) {
                    throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + " loop condition no longer a number");
                }
                if (cond.getDouble() == 0) return;
                subprogram.execute(stack, table);
                if (subprogram.thenBreak) return;
                if (subprogram.thenReturn) {
                    thenReturn = true;
                    return;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "while()";
    }
}
