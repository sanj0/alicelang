package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import static de.sanj0.alicelang.cli.Main.*;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import java.net.*;

public class NIncludeStatement extends Statement {

    public static final Map<String, NativeProvider> NATIVES = new HashMap<>();
    public static URL[] N_PATH = new URL[0];
    public static ClassLoader nLoader = null;

    public NIncludeStatement() {
        if (nLoader == null) {
            nLoader = URLClassLoader.newInstance(N_PATH, getClass().getClassLoader());
        }
    }

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final String name = stack.pop().getString();
        try {
            final Class<?> rc = Class.forName(name, true, nLoader);
            final Class<? extends NativeProvider> clazz = rc.asSubclass(NativeProvider.class);
            NATIVES.put(name, clazz.newInstance());
        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new AliceRuntimeError("loading of native provider failed: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "ninclude()";
    }
}
