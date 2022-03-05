package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;

public class RotateStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        stack.push(stack.pop(2));
    }

    @Override
    public String toString() {
        return "rotate()";
    }
}
