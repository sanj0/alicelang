package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.NumberStackElement;
import de.sanj0.alicelang.stackelements.StringStackElement;

// 65 character -> "A"
public class CharacterStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final StackElement<?> e = stack.pop();
        if (!(e instanceof NumberStackElement)) {
            throw new AliceRuntimeError("character word expects a number!");
        }
        stack.push(new StringStackElement(String.valueOf((char) e.getInt())));
    }
}
