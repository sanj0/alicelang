package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.NumberStackElement;

import java.util.Map;

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

        table.putNew(var, new NumberStackElement(min));
        // buffer the Entry to speed up the loop
        for (int i = 1; i <= times; i++) {
            body.execute(stack, table);
            if (body.thenBreak) {
                body.thenBreak = false;
                break;
            }
            if (body.thenReturn) {
                this.thenReturn = !(body.thenReturn = false);
                break;
            }
            table.assign(var, new NumberStackElement(min + i * step));
        }
        table.purgeFirst(var);
    }
}
