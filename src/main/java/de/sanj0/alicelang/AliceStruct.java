package de.sanj0.alicelang;

import de.sanj0.alicelang.stackelements.StructInstance;
import de.sanj0.alicelang.statements.TypeStatement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AliceStruct {
    public static final String INIT_FUNCTION_NAME = "_init";
    private final List<String> types;
    // maps name - type
    private final Map<String, String> members;
    private final Map<String, StackElement<?>> defaultValues;
    private final Map<String, Program> functions = new HashMap<>();
    private final int size;

    public AliceStruct(final List<String> types, final Map<String, String> members, final Map<String, StackElement<?>> defaultValues, final Map<String, Program>... functions) {
        this.types = types;
        this.members = members;
        this.size = members.size();
        this.defaultValues = defaultValues;
        if (functions.length == 1) {
            this.functions.putAll(functions[0]);
        }
    }

    // table is needed to execute _init function
    public StructInstance instantiate(final AliceStack stack, final AliceTable table) {
        final Map<String, StackElement<?>> m = new HashMap<>(size);
        final int numMemFromStack = size - defaultValues.size();
        int i = 0;
        for (final Map.Entry<String, String> member : members.entrySet()) {
            StackElement<?> e;
            if (defaultValues.containsKey(member.getKey())) {
                e = defaultValues.get(member.getKey());
            } else {
                e = stack.pop(numMemFromStack - i - 1);
                final List<String> t = TypeStatement.getType(e);
                if (!TypeStatement.typeMatches(member.getValue(), t)) {
                    throw new AliceParserError("wrong types on stack for struct " + types.get(0) + "; index " + i + " should be " + member.getValue() + " but is " + t);
                }
                i++;
            }
            m.put(member.getKey(), e);
        }

        final StructInstance instance = new StructInstance(types, m, functions);
        final Program initFunction = this.functions.getOrDefault(INIT_FUNCTION_NAME, null);
        if (initFunction != null) {
            table.putNew(AliceParser.WRD_SELF, instance);
            initFunction.execute(stack, table);
            table.purgeFirst(AliceParser.WRD_SELF);
        }
        return instance;
    }

    /**
     * Gets {@link #types}.
     *
     * @return the value of {@link #types}
     */
    public List<String> getTypes() {
        return types;
    }

    /**
     * Gets {@link #size}.
     *
     * @return the value of {@link #size}
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets {@link #members}.
     *
     * @return the value of {@link #members}
     */
    public Map<String, String> getMembers() {
        return members;
    }

    /**
     * Gets {@link #defaultValues}.
     *
     * @return the value of {@link #defaultValues}
     */
    public Map<String, StackElement<?>> getDefaultValues() {
        return defaultValues;
    }

    /**
     * Gets {@link #functions}.
     *
     * @return the value of {@link #functions}
     */
    public Map<String, Program> getFunctions() {
        return functions;
    }
}
