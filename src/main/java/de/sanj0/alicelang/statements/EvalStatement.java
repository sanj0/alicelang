package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;

// pops a string and evaluates it as regular alice code
// on the same stack and table as the current program
public class EvalStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final String code = stack.pop().toString();
        new AliceParser(code).parse().execute(stack, table);
    }
}
