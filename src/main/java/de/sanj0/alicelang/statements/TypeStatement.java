package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.StackElement;
import de.sanj0.alicelang.Statement;
import de.sanj0.alicelang.stackelements.*;

import java.util.List;

import static java.util.Collections.*;

public class TypeStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        stack.push(new StringStackElement(getType(stack.pop()).get(0)));
    }

    public static List<String> getType(final StackElement<?> e) {
        if (e instanceof StringStackElement) {
            return singletonList("string");
        } else if (e instanceof NumberStackElement) {
            return singletonList("number");
        } else if (e instanceof ProgramStackElement) {
            return singletonList("subprogram");
        } else if (e instanceof Substack) {
            return singletonList("substack");
        } else if (e instanceof StructInstance) {
            return ((StructInstance) e).getTypes();
        }
        return singletonList("unknown");
    }

    // honors "?" wildcard type and "null"
    public static boolean typeMatches(final String required, final List<String> actual) {
        if (required.equals("?")) return true;
        if (actual.get(0).equals("null")) return true;
        return actual.contains(required);
    }
}
