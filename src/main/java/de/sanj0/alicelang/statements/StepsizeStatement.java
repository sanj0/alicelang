package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;

public class StepsizeStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        NurStatement.stepsize = stack.pop().getDouble();
    }
}
