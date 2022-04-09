package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.StackElement;
import de.sanj0.alicelang.Statement;

import java.util.Map;
import java.util.Map.Entry;

public class PrintTableStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        String indentation = "";
        for (final Map<String, StackElement<?>> scope : table.getScopes()) {
            for (final Entry<String, StackElement<?>> e : scope.entrySet()) {
                System.out.println(indentation + e.getKey() + "->" + e.getValue().toString().replaceAll("\n", "\\n"));
            }
            indentation += "    ";
        }
    }

    @Override
    public String toString() {
        return "printTable()";
    }
}
