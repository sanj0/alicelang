package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;
import de.sanj0.alicelang.stackelements.StructInstance;

public class StructSetStatement extends Statement {
    private final String name;

    public StructSetStatement(final String name) {
        this.name = name;
    }

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final StructInstance struct = (StructInstance) stack.pop();
        struct.getMembers().put(name, stack.pop());
    }
}
