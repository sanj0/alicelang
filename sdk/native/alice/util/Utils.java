package alice.util;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.*;

import java.util.Map;
import java.util.Map.Entry;

public class Utils implements NativeProvider {
    @Override
    public boolean execute(final String word, final AliceStack stack, final AliceTable table) {
        return false;
    }

    public void print_table(final AliceStack stack, final AliceTable table) {
        String indentation = "";
        for (final Map<String, AliceTable.TableValue> scope : table.getScopes()) {
            for (final Entry<String, AliceTable.TableValue> e : scope.entrySet()) {
                System.out.println(indentation + e.getKey() + "->" + e.getValue().value.toString().replaceAll("\n", "\\n"));
            }
            indentation += "    ";
        }
    }

    public void ssize(final AliceStack stack, final AliceTable table) {
        stack.push(new NumberStackElement((double) stack.size()));
    }
}
