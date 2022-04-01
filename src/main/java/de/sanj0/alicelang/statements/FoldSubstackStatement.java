package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;
import de.sanj0.alicelang.stackelements.Substack;

public class FoldSubstackStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final int n = stack.pop().getInt();
        if (n > 0) {
            final AliceStack s = AliceStack.initialize(n);
            for (int i = 0; i < n; i++) {
                s.push(stack.pop(n - i - 1));
            }
            stack.push(new Substack(s));
        } else {
            stack.push(new Substack(AliceStack.initialize(0)));
        }
    }
}
