package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.*;

// pops a value and pushes it length:
// for string: number of characters
// for number: number of digits before the decimal point
// for subprogram: number of instructions; not recursive
// for substack: number of elements; not recursive
public class LengthStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final StackElement<?> e = stack.pop();
        double length = (double) -1f;
        if (e instanceof NumberStackElement) {
            length = String.valueOf(e.getInt()).length();
        } else if (e instanceof StringStackElement) {
            length = e.getString().length();
        } else if (e instanceof ProgramStackElement) {
            length = ((ProgramStackElement) e).getValue().getStatements().size();
        } else if (e instanceof Substack) {
            length = ((Substack) e).getValue().size();
        }
        stack.push(new NumberStackElement(length));
    }
}
