package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.NumberStackElement;
import de.sanj0.alicelang.stackelements.ProgramStackElement;

/**
 * Pops one item from the stack. First one popped is the loop body, second
 * one is peeked and checked for not being 0 for the first iteration. After every iteration,
 * one value is peeked  and if it zero, the loop is exited.
 * Syntax:
 * [code that puts a number onto the stack] (loop body 1-) while
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
            subprogram.execute(stack, table);
            while (true) {
                final StackElement<?> cond = stack.peek();
                if (!(cond instanceof NumberStackElement)) {
                    throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + " loop condition no longer a number");
                }
                if (cond.getDouble() == 0) break;
                subprogram.execute(stack, table);
            }
        }
    }
}
