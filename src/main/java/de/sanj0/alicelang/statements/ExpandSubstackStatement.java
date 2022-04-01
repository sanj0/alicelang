package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.Substack;

public class ExpandSubstackStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final StackElement<?> e = stack.pop();
        if (!(e instanceof Substack)) {
            throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + "substack expected");
        }
        stack.getElements().addAll(((Substack) e).getValue().getElements());
    }
}
