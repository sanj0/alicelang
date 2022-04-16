package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.NumberStackElement;
import de.sanj0.alicelang.stackelements.ProgramStackElement;

import java.util.ArrayList;
import java.util.List;

/**
 * condition (subprogram) if
 */
public class FiStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final int numElements = stack.size() - IfStatement.stackSizeStack.pollLast();
        if (numElements < 2 || numElements % 2 == 1) {
            throw new AliceRuntimeError("malformed if!");
        }
        // have to pop everything first as to make the stack accessible by the conds and bodies
        final List<StackElement<?>> elements = new ArrayList<>(numElements);
        for (int i = 0; i < numElements; i++) {
            elements.add(stack.pop(numElements - i - 1));
        }
        for (int i = 0; i < numElements; i +=2) {
            ((Program) elements.get(i).getValue()).execute(stack, table);
            if (stack.pop().getInt() != 0) {
                final Program body = (Program) elements.get(i + 1).getValue();
                body.execute(stack, table);
                if (body.thenBreak) {
                    this.thenBreak = !(body.thenBreak = false);
                }
                if (body.thenReturn) {
                    this.thenReturn = !(body.thenReturn = false);
                }
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "fi()";
    }
}
