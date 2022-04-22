package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.StructInstance;

public class StructGetStatement extends Statement {
    private String member;

    public StructGetStatement(final String member) {
        this.member = member;
    }

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final StructInstance struct = (StructInstance) stack.pop();
        final Program func = struct.getFunctions().getOrDefault(member, null);
        if (func != null) {
            table.putNew(AliceParser.WRD_SELF, struct);
            func.execute(stack, table);
        } else {
            stack.push(struct.get(member));
        }
    }

    @Override
    public String toString() {
        return "." + member;
    }
}
