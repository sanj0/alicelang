package de.sanj0.alicelang;

import de.sanj0.alicelang.statements.*;

import java.util.*;
import java.lang.reflect.*;

/**
 * A native subprogram
 */
public class NativeProgram extends Program {

    private final NativeProvider source;
    private final Method method;

    public NativeProgram(final String clazz, final String fun) throws SecurityException, NoSuchMethodException {
        super(null, "");
        this.source = NIncludeStatement.NATIVES.get(clazz);
        this.method = this.source.getClass().getMethod(fun, AliceStack.class, AliceTable.class);
    }

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        try {
        method.invoke(source, stack, table);
        } catch (final IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            throw new AliceRuntimeError(e.getMessage());
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof NativeProgram)) return false;
        NativeProgram program = (NativeProgram) o;
        return Objects.equals(method, program.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method);
    }
}
