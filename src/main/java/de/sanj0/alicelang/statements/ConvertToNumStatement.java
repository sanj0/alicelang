package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;
import de.sanj0.alicelang.stackelements.NumberStackElement;

public class ConvertToNumStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        stack.push(new NumberStackElement(Double.valueOf(stack.pop().toString())));
    }

    @Override
    public String toString() {
        return "toNumber()";
    }
}
