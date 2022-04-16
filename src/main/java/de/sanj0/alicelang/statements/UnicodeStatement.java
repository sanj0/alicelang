package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.NumberStackElement;
import de.sanj0.alicelang.stackelements.StringStackElement;

// "A" unicode -> 65
public class UnicodeStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final StackElement<?> e = stack.pop();
        if (!(e instanceof StringStackElement && e.getString().length() == 1)) {
            throw new AliceRuntimeError("unicode words expects a single character string!");
        }
        stack.push(new NumberStackElement((double) e.getString().charAt(0)));
    }
}
