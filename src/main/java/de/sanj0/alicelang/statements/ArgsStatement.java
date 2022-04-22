package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;

public class ArgsStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final Program list = (Program) stack.pop().getValue();
        final int size = list.getStatements().size();
        if (size % 2 != 0) {
            throw new AliceRuntimeError("malformed args list!");
        }
        for (int i = size - 1; i >= 0; i -= 2) {
            final Statement type = list.getStatements().get(i - 1);
            final Statement name = list.getStatements().get(i);
            if (!(type instanceof AccessTableStatement && name instanceof AccessTableStatement)) {
                throw new AliceRuntimeError("malformed args declaration!");
            }
            final StackElement<?> val = stack.pop();
            if (!TypeStatement.typeMatches(((AccessTableStatement) type).getKey(), TypeStatement.getType(val))) {
                throw new AliceRuntimeError("mismatched arguments; '" + ((AccessTableStatement) type).getKey() + "'" +
                        " required at index " + i/2 + " but got " + TypeStatement.getType(val));
            }
            table.putNew(((AccessTableStatement) name).getKey(), val);
        }
    }
}
