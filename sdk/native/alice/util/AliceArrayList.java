package alice.util;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class AliceArrayList implements NativeProvider {
    private static final Map<Double, ArrayList<StackElement<?>>> LISTS = new HashMap<>();
    @Override
    public boolean execute(final String word, final AliceStack stack, final AliceTable table) {
        if ("init".equals(word)) {
            LISTS.put(stack.pop().getDouble(), new ArrayList<>());
            return true;
        }
        return false;
    }

    public void add(final AliceStack stack, final AliceTable table) {
        LISTS.get(getID(table)).add(stack.pop());
    }

    public void get(final AliceStack stack, final AliceTable table) {
    }

    public void set(final AliceStack stack, final AliceTable table) {
    }

    public void remove(final AliceStack stack, final AliceTable table) {
    }

    public void sublist(final AliceStack stack, final AliceTable table) {
    }

    public void add_all(final AliceStack stack, final AliceTable table) {
    }

    public void index_of(final AliceStack stack, final AliceTable table) {
    }

    public void clone(final AliceStack stack, final AliceTable table) {
    }

    public void foreach(final AliceStack stack, final AliceTable table) {
        final ArrayList<StackElement<?>> list = getList(table);
        final Program consumer = (Program) stack.pop().getValue();
        list.forEach(e -> {stack.push(e);consumer.execute(stack, table);});
    }

    public void clear_list(final AliceStack stack, final AliceTable table) {
    }

    public void qmcontains(final AliceStack stack, final AliceTable table) {
    }

    private static Double getID(final AliceTable table) {
        final StructInstance self = (StructInstance) table.get(AliceParser.WRD_SELF);
        return self.get("id").getDouble();
    }

    private static ArrayList<StackElement<?>> getList(final AliceTable table) {
        return LISTS.get(getID(table));
    }
}
