package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;

public class PutOnTableStatement extends Statement {

    public static boolean nextIsGlobal = false;
    public static boolean nextIsDeclaration = false;
    public static boolean nextIsInline = false;

    private final String key;

    public PutOnTableStatement(final String key) {
        this.key = key;
        if (AliceParser.isReservedKeyWord(key)) {
            throw new AliceParserError(AliceParserError.RESERVED_KEYWORD_ + key);
        }
    }

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
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
                table.putNew(key, stack.pop());
                nextIsDeclaration = false;
            } else {
                if (Character.isUpperCase(key.charAt(0)) && table.containsKey(key)) {
                    throw new AliceRuntimeError(AliceRuntimeError.WRITE_TO_CONST_ + key);
                }
                table.assign(key, stack.pop());
            }
        }
    }

    @Override
    public String toString() {
        return ":" + key;
    }
}
