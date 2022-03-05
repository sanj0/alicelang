package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.StackElement;
import de.sanj0.alicelang.Statement;

import java.util.Map.Entry;

public class PrintTableStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        for (final Entry<String, StackElement<?>> e : table.getMap().entrySet()) {
            System.out.println(e.getKey() + "->" + e.getValue());
        }
    }

    @Override
    public String toString() {
        return "printTable()";
    }
}
