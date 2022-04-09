package de.sanj0.alicelang;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class AliceTable {
    private LinkedList<Map<String, StackElement<?>>> scopes = new LinkedList<>();

    private AliceTable(final Map<String, StackElement<?>> table) {
        scopes.add(table);
    }

    public static AliceTable initialize(final int initialSize) {
        return new AliceTable(new HashMap<>(initialSize));
    }

    /**
     * Gets {@link #scopes}.
     *
     * @return the value of {@link #scopes}
     */
    public LinkedList<Map<String, StackElement<?>>> getScopes() {
        return scopes;
    }

    public int size() {
        return scopes.stream().mapToInt(Map::size).sum();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(final Object key) {
        for (final Map<String, StackElement<?>> m : scopes) {
            if (m.containsKey(key)) {
                return true;
            }
        }
        return false;
    }

    public StackElement<?> get(final Object key) {
        for (final Map<String, StackElement<?>> m : scopes) {
            if (m.containsKey(key)) {
                return m.get(key);
            }
        }
        return null;
    }

    public StackElement<?> put(final String key, final StackElement<?> value) {
        for (final Map<String, StackElement<?>> m : scopes) {
            if (m.containsKey(key)) {
                return m.put(key, value);
            }
        }
        return scopes.peekLast().put(key, value);
    }

    // normal put only puts to local if it already has key
    public StackElement<?> putLocal(final String key, final StackElement<?> value) {
        return scopes.peekFirst().put(key, value);
    }

    public StackElement<?> getOrDefault(final Object key, final StackElement<?> defaultValue) {
        if (containsKey(key)) {
            return get(key);
        } else {
            return defaultValue;
        }
    }

    public void putScope() {
        scopes.push(new HashMap<>(3));
    }

    public Map<String, StackElement<?>> dropScope() {
        return scopes.pop();
    }
}
