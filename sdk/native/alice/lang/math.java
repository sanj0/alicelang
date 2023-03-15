package alice.lang;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.*;

public class Math implements NativeProvider {
    @Override
    public boolean execute(final String word, final AliceStack stack, final AliceTable table) {
        return false;
    }

    public void qmprime_not_1(final AliceStack stack, final AliceTable table) {
        final double num = stack.pop().getDouble();
        if (num == 2 || num == 3) {
            stack.push(new NumberStackElement(1d));
            return;
        }

        if (num % 2 == 0 || num % 3 == 0) {
            stack.push(new NumberStackElement(0d));
            return;
        }

        final int limit = (int) java.lang.Math.sqrt(num) + 1;
        for (int i = 5; i < limit; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) {
                stack.push(new NumberStackElement(0d));
                return;
            }
        }
        stack.push(new NumberStackElement(1d));
        return;
    }
}
