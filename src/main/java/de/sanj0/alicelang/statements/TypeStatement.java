package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.StackElement;
import de.sanj0.alicelang.Statement;
import de.sanj0.alicelang.stackelements.NumberStackElement;
import de.sanj0.alicelang.stackelements.ProgramStackElement;
import de.sanj0.alicelang.stackelements.StringStackElement;
import de.sanj0.alicelang.stackelements.Substack;

public class TypeStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final StackElement<?> e = stack.pop();
        if (e instanceof StringStackElement) {
            stack.push(new StringStackElement("string"));
        } else if (e instanceof NumberStackElement) {
            stack.push(new StringStackElement("number"));
        } else if (e instanceof ProgramStackElement) {
            stack.push(new StringStackElement("subprogram"));
        } else if (e instanceof Substack) {
            stack.push(new StringStackElement("substack"));
        } else {
            stack.push(new StringStackElement("unknown"));
        }
    }
}
