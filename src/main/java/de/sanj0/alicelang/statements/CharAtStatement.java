package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.StringStackElement;

// pops two values a and b from the stack, respectively, and
// pushes the character index a in b onto the stack; works on strings
// example:
// "hello, world!" 7 charat -> [w]
public class CharAtStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final int index = stack.pop().getInt();
        final StackElement<?> str = stack.pop();
        if (str instanceof StringStackElement) {
            stack.push(new StringStackElement("" + str.toString().charAt(index)));
        } else {
            throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + "charat expectes a string at second position");
        }
    }
}
