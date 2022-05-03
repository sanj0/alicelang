package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;
import de.sanj0.alicelang.stackelements.NumberStackElement;
import de.sanj0.alicelang.stackelements.StringStackElement;

import java.util.List;

// pushes all types of the head element onto the stack
// and lastly the count of its types
public class TypesStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final List<String> types = TypeStatement.getType(stack.pop());
        for (final String s : types) {
            stack.push(new StringStackElement(s));
        }
        stack.push(new NumberStackElement((double) types.size()));
    }
}
