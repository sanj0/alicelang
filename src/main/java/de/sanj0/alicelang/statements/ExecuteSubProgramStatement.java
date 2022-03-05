package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.NumberStackElement;
import de.sanj0.alicelang.stackelements.ProgramStackElement;

/**
 * Pops two elements from the stack and executes the first one n number of times,
 * where n is the second popped element.
 */
public class ExecuteSubProgramStatement extends Statement {

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        if (stack.size() < 2) {
            throw new AliceRuntimeError("stack has too few elements to execute subprogram");
        }
        final StackElement<?> program = stack.pop();
        final StackElement<?> num = stack.pop();
        if (!(program instanceof ProgramStackElement) || !(num instanceof NumberStackElement)) {
            throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + "execute expects a subprogram and a number");
        }
        final double n = num.getDouble();
        final Program p = ((ProgramStackElement) program).getValue();
        for (int i = 0; i < n; i++) {
            p.execute(stack, table);
        }
    }

    @Override
    public String toString() {
        return "executeSubProgram()";
    }
}
