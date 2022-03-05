package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;
import de.sanj0.alicelang.stackelements.StringStackElement;

public class ConvertToStringStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        stack.push(new StringStackElement(String.valueOf(stack.pop().getValue())));
    }

    @Override
    public String toString() {
        return "toString()";
    }
}
