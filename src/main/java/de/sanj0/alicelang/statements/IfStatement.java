package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;

import java.util.Deque;
import java.util.LinkedList;

public class IfStatement extends Statement {
    public static final Deque<Integer> stackSizeStack = new LinkedList<>();

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        stackSizeStack.offer(stack.size());
    }

    @Override
    public String toString() {
        return "if()";
    }
}
