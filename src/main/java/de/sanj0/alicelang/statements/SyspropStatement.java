package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.StringStackElement;

public class SyspropStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        stack.push(new StringStackElement(System.getProperty(stack.pop().getString())));
    }
}
