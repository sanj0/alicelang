package alice.lang;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.*;

import java.io.IOException;

public class AliceSystem implements NativeProvider {
    @Override
    public boolean execute(final String word, final AliceStack stack, final AliceTable table) {
        return false;
    }

    // string -> string
    public void get_property(final AliceStack stack, final AliceTable table) {
        stack.push(new StringStackElement(System.getProperty(stack.pop().getString())));
    }

    // string... number n -> number
    public void proc(final AliceStack stack, final AliceTable table) {
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

    // string... number n ->
    public void pproc(final AliceStack stack, final AliceTable table) {
        final int num = stack.pop().getInt();
        final String[] parts = new String[num];
        for (int i = 0; i < num; i++) {
            parts[num - 1 - i] = stack.pop().getString();
        }
        final ProcessBuilder pb = new ProcessBuilder(parts).inheritIO();
        try {
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
