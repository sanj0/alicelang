package de.sanj0.alicelang.stackelements;

import de.sanj0.alicelang.AliceRuntimeError;
import de.sanj0.alicelang.Program;
import de.sanj0.alicelang.StackElement;

import java.util.List;
import java.util.Map;
import java.util.Objects;

// this is the actual instance. the "type" is to be stored as a simple string
public class StructInstance extends StackElement<String> {
    // e.g. "string" "number"
    private Map<String, StackElement<?>> members;
    private final Map<String, Program> functions;
    private final List<String> types;

    public StructInstance(final List<String> types, final Map<String, StackElement<?>> members, final Map<String, Program> functions) {
        super(types.get(0));
        this.types = types;
        this.members = members;
        this.functions = functions;
    }

    public StackElement<?> get(final String s) {
        final StackElement<?> e = members.getOrDefault(s, null);
        if (e == null) {
            throw new AliceRuntimeError("struct " + getValue() + " doesn't have " + s);
        }
        return e;
    }

    /**
     * Gets {@link #members}.
     *
     * @return the value of {@link #members}
     */
    public Map<String, StackElement<?>> getMembers() {
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

    /**
     * Gets {@link #types}.
     *
     * @return the value of {@link #types}
     */
    public List<String> getTypes() {
        return types;
    }

    @Override
    public String toString() {
        return getValue() + members.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(members, functions);
    }
}
