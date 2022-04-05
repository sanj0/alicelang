package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.*;

// pushes a random number between 0 and 1
public class RandomStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        stack.push(new NumberStackElement(Math.random()));
    }
}
