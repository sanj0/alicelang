package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.*;

// pushes the current system time in milliseconds onto the stack
public class TimeStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        stack.push(new NumberStackElement((double) System.currentTimeMillis()));
    }
}
