package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;
import de.sanj0.alicelang.stackelements.NumberStackElement;

import java.io.IOException;

// [... "java" "-jar" "alice.jar" "script.alice" 4] proc -> [exitCode]
// blocks until command is finished
public class ProcStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final int num = stack.pop().getInt();
        final String[] parts = new String[num];
        for (int i = 0; i < num; i++) {
            parts[num - 1 - i] = stack.pop().getString();
        }
        final ProcessBuilder pb = new ProcessBuilder(parts).inheritIO();
        try {
            stack.push(new NumberStackElement((double) pb.start().waitFor()));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
