package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import static de.sanj0.alicelang.cli.Main.*;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import java.net.*;

// "provider" "word" native
public class NativeStatement extends Statement {

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final String word = stack.pop().getString();
        final String provider = stack.pop().getString();
        final NativeProvider np = NIncludeStatement.NATIVES.getOrDefault(provider, null);
        if (np == null) {
            throw new AliceRuntimeError("native provider " + provider + " not found");
        }
        final boolean provided = np.execute(word, stack, table);
        if (!provided) {
            throw new AliceRuntimeError("native provider " + provider + " doesn't provide word " + word + "!");
        }
    }

    @Override
    public String toString() {
        return "native()";
    }
}
