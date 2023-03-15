package de.sanj0.alicelang;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AliceTable {
    private LinkedList<Map<String, TableValue>> scopes = new LinkedList<>();
    private Map<String, AliceStruct> structs = new HashMap<>();

    private AliceTable(final Map<String, TableValue> table) {
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
    public List<Map<String, TableValue>> getScopes() {
        return scopes;
    }

    public int size() {
        return scopes.stream().mapToInt(Map::size).sum();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(final Object key) {
        for (final Map<String, TableValue> m : scopes) {
            if (m.containsKey(key)) {
                return true;
            }
        }
        return false;
    }

    public StackElement<?> get(final Object key) {
        for (final Map<String, TableValue> m : scopes) {
            if (m.containsKey(key)) {
                return m.get(key).value;
            }
        }
        return null;
    }

    public StackElement<?> putNew(final String key, final StackElement<?> value, final boolean isConst) {
        final TableValue v = scopes.peekFirst().put(key, new TableValue(value, isConst));
        return v == null ? null : v.value;
    }

    public StackElement<?> assign(final String key, final StackElement<?> value) {
        for (final Map<String, TableValue> m : scopes) {
            if (m.containsKey(key)) {
                final TableValue e = m.get(key);
                if (e.isConst) {
                    throw new AliceRuntimeError("cannot assign to constant var " + key + "!");
                }
                final StackElement<?> old = e.value;
                e.value = value;
                return old;
            }
        }
        throw new AliceRuntimeError(AliceRuntimeError.VARIABLE_NOT_FOUND_ + key);
    }

    // puts into the last scope
    public StackElement<?> putGlobal(final String key, final StackElement<?> value, final boolean isConst) {
        final TableValue v = scopes.peekLast().put(key, new TableValue(value, isConst));
        if (v != null) {
            if (v.isConst) {
                throw new AliceRuntimeError("cannot assign to constant global " + key + "!");
            }
            return v.value;
        }
        return null;
    }

    public StackElement<?> getOrDefault(final Object key, final StackElement<?> defaultValue) {
        if (containsKey(key)) {
            return get(key);
        } else {
            return defaultValue;
        }
    }

    public void putScope() {
        scopes.push(new HashMap<>());
    }

    public void putScope(final Map<String, TableValue> items) {
        scopes.push(items);
    }

    public Map<String, TableValue> dropScope() {
        return scopes.pop();
    }

    public StackElement<?> purgeFirst(final String key) {
        for (final Map<String, TableValue> scope : scopes) {
            if (scope.containsKey(key)) {
                return scope.remove(key).value;
            }
        }
        return null;
    }

    public void putStruct(final AliceStruct struct) {
        structs.put(struct.getTypes().get(0), struct);
    }

    /**
     * Gets {@link #structs}.
     *
     * @return the value of {@link #structs}
     */
    public Map<String, AliceStruct> getStructs() {
        return structs;
    }

    public static class TableValue {
        public StackElement<?> value;
        public final boolean isConst;

        public TableValue(final StackElement<?> value, final boolean isConst) {
            this.value = value;
            this.isConst = isConst;
        }
    }
}
