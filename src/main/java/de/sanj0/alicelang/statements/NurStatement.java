package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.NumberStackElement;

// usage:
// 1 stepsize # not required as 1 is default
// run "i" from 1 to 10 {
// } nur
public class NurStatement extends Statement {
    public static  double stepsize = 1;

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final double step = stepsize;
        stepsize = 1;
        final Program body = (Program) stack.pop().getValue();
        final double max = Math.floor(stack.pop().getDouble());
        final double min = Math.floor(stack.pop().getDouble());
        if (min > max) {
            return;
        }
        final String var = stack.pop().getString();
        final double times = Math.abs(min - max) / step;
        final NumberStackElement n = new NumberStackElement(min);

        table.putNew(var, n);
        for (int i = 0; i < times; i++) {
            n.setValue(min + i * step);
            body.execute(stack, table);
            if (body.thenBreak) {
                body.thenBreak = false;
                break;
            }
            if (body.thenReturn) {
                this.thenReturn = !(body.thenReturn = false);
                break;
            }
        }
        table.purgeFirst(var);
    }
}