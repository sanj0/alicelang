package de.sanj0.alicelang;

import java.util.HashMap;
import java.util.Map;

public class AliceTable {
    private final Map<String, StackElement<?>> table;

    private AliceTable(final Map<String, StackElement<?>> table) {
        this.table = table;
    }

    public static AliceTable initialize(final int initialSize) {
        return new AliceTable(new HashMap<>(initialSize));
    }

    public Map<String, StackElement<?>> getMap() {
        return table;
    }

    public int size() {
        return table.size();
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }

    public boolean containsKey(final Object key) {
        return table.containsKey(key);
    }

    public StackElement<?> get(final Object key) {
        return table.get(key);
    }

    public StackElement<?> put(final String key, final StackElement<?> value) {
        return table.put(key, value);
    }

    public StackElement<?> remove(final Object key) {
        return table.remove(key);
    }

    public void clear() {
        table.clear();
    }

    public StackElement<?> getOrDefault(final Object key, final StackElement<?> defaultValue) {
        return table.getOrDefault(key, defaultValue);
    }
}
