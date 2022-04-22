package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.StackElement;
import de.sanj0.alicelang.Statement;
import de.sanj0.alicelang.stackelements.*;

public class TypeStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        stack.push(new StringStackElement(getType(stack.pop())));
    }

    public static String getType(final StackElement<?> e) {
        if (e instanceof StringStackElement) {
            return "string";
        } else if (e instanceof NumberStackElement) {
            return "number";
        } else if (e instanceof ProgramStackElement) {
            return "subprogram";
        } else if (e instanceof Substack) {
            return "substack";
        } else if (e instanceof StructInstance) {
            return e.getString();
        }
        return "unknown";
    }

    // honors "?" wildcard type and "null"
    public static boolean typeMatches(final String required, final String actual) {
        if (required.equals("?")) return true;
        if (actual.equals("null")) return true;
        return required.equals(actual);
    }
}
