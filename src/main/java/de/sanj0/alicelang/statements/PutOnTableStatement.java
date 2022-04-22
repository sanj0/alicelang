package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.ProgramStackElement;

import java.util.*;

public class PutOnTableStatement extends Statement {

    public static boolean nextIsGlobal = false;
    public static boolean nextIsDeclaration = false;
    public static boolean nextIsStruct = false;
    public static boolean nextIsPriv = false;

    private final String key;

    public PutOnTableStatement(final String key) {
        this.key = key;
        if (AliceParser.isReservedKeyWord(key)) {
            throw new AliceParserError(AliceParserError.RESERVED_KEYWORD_ + key);
        }
    }

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        if (nextIsStruct) {
            final Program body = (Program) stack.pop().getValue();
            final int size = body.getStatements().size();
            if (size % 2 != 0) {
                throw new AliceRuntimeError("malformed struct definition!");
            }
            final Map<String, String> members = new LinkedHashMap<>(size / 2);
            for (int i = 0; i < size; i += 2) {
                final Statement stype = body.getStatements().get(i);
                final Statement sname = body.getStatements().get(i + 1);
                if (!(stype instanceof AccessTableStatement && sname instanceof AccessTableStatement)) {
                    throw new AliceRuntimeError("malformed struct definition!");
                }
                members.put(((AccessTableStatement) sname).getKey(), ((AccessTableStatement) stype).getKey());
            }
            table.putStruct(new AliceStruct(key, members));
            nextIsGlobal = nextIsStruct = false;
            return;
        }
        if (!nextIsDeclaration && !table.containsKey(key)) {
            throw new AliceRuntimeError(AliceRuntimeError.VARIABLE_NOT_FOUND_ + key);
        }

        if (nextIsGlobal) {
            if (Character.isUpperCase(key.charAt(0)) && table.containsKey(key)) {
                throw new AliceRuntimeError(AliceRuntimeError.WRITE_TO_CONST_ + key);
            }
            table.putGlobal(key, stack.pop());
            nextIsGlobal = false;
            nextIsDeclaration = false;
        } else {
            if (nextIsDeclaration) {
                final StackElement<?> e = stack.pop();
                if (nextIsPriv && e instanceof ProgramStackElement) {
                    ((Program) e.getValue()).isPriv = true;
                }
                table.putNew(key, e);
                nextIsDeclaration = false;
            } else {
                if (Character.isUpperCase(key.charAt(0)) && table.containsKey(key)) {
                    throw new AliceRuntimeError(AliceRuntimeError.WRITE_TO_CONST_ + key);
                }
                table.assign(key, stack.pop());
            }
        }
        if (nextIsPriv) {
            throw new AliceRuntimeError("illegal use of priv statement!");
        }
    }

    @Override
    public String toString() {
        return ":" + key;
    }
}
