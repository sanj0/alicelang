package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;

// pops a string and evaluates it as regular alice code
// on the same stack and table as the current program
public class EvalStatement extends Statement {
    private final String location;

    public EvalStatement(final String location) {
        this.location = location;
    }

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final String code = stack.pop().toString();
        AliceParser.currentFile = location;
        new AliceParser(code, lineNumber).parse().execute(stack, table);
    }

    @Override
    public String toString() {
        return "eval";
    }
}
