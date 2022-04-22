package de.sanj0.alicelang;

import de.sanj0.alicelang.stackelements.StructInstance;
import de.sanj0.alicelang.statements.TypeStatement;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AliceStruct {
    private final String name;
    // maps name - type
    private final Map<String, String> members;
    private final Map<String, Program> functions = new HashMap<>();
    private final int size;

    public AliceStruct(final String name, final Map<String, String> members) {
        this.name = name;
        this.members = members;
        this.size = members.size();
    }

    public StructInstance instantiate(final AliceStack stack) {
        final Map<String, StackElement<?>> m = new HashMap<>(size);
        int i = 0;
        for (final Map.Entry<String, String> member : members.entrySet()) {
            final StackElement<?> e = stack.pop(size - i - 1);
            final String t = TypeStatement.getType(e);
            if (!TypeStatement.typeMatches(member.getValue(), t)) {
                throw new AliceParserError("wrong types on stack for struct " + name + "; index " + i + " should be " + member.getValue() + " but is " + t);
            }
            m.put(member.getKey(), e);
            i++;
        }

        return new StructInstance(name, m, functions);
    }

    /**
     * Gets {@link #name}.
     *
     * @return the value of {@link #name}
     */
    public String getName() {
        return name;
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
     * Gets {@link #functions}.
     *
     * @return the value of {@link #functions}
     */
    public Map<String, Program> getFunctions() {
        return functions;
    }
}
