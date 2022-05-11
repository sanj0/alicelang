package alice.lang;

import de.sanj0.alicelang.*;

public class stdio implements NativeProvider {
    public boolean execute(final String word, final AliceStack stack, final AliceTable table) {
        return false;
    }

    public void pln(final AliceStack stack, final AliceTable table) {
        System.out.println(stack.peek().getString());
    }

    public void Pln(final AliceStack stack, final AliceTable table) {
        System.out.println(stack.pop().getString());
    }
}
