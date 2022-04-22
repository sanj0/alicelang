package de.sanj0.alicelang;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AliceTable {
    private LinkedList<Map<String, StackElement<?>>> scopes = new LinkedList<>();
    private Map<String, AliceStruct> structs = new HashMap<>();

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
    public List<Map<String, StackElement<?>>> getScopes() {
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

    public StackElement<?> putNew(final String key, final StackElement<?> value) {
        return scopes.peekFirst().put(key, value);
    }

    public StackElement<?> assign(final String key, final StackElement<?> value) {
        for (final Map<String, StackElement<?>> m : scopes) {
            if (m.containsKey(key)) {
                return m.put(key, value);
            }
        }
        throw new AliceRuntimeError(AliceRuntimeError.VARIABLE_NOT_FOUND_ + key);
    }

    // puts into the last scope
    public StackElement<?> putGlobal(final String key, final StackElement<?> value) {
        return scopes.peekLast().put(key, value);
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

    public void putScope(final Map<String, StackElement<?>> items) {
        scopes.push(items);
    }

    public Map<String, StackElement<?>> dropScope() {
        return scopes.pop();
    }

    public StackElement<?> purgeFirst(final String key) {
        for (final Map<String, StackElement<?>> scope : scopes) {
            if (scope.containsKey(key)) {
                return scope.remove(key);
            }
        }
        return null;
    }

    public void putStruct(final AliceStruct struct) {
        structs.put(struct.getName(), struct);
    }

    /**
     * Gets {@link #structs}.
     *
     * @return the value of {@link #structs}
     */
    public Map<String, AliceStruct> getStructs() {
        return structs;
    }
}
