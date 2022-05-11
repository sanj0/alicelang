package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.ProgramStackElement;

import java.util.*;

public class PutOnTableStatement extends Statement {

    public static boolean nextIsGlobal = false;
    public static boolean nextIsDeclaration = false;
    public static boolean nextIsStruct = false;
    public static boolean nextIsPriv = false;
    public static boolean nextIsNfun = false;

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
            final Map<String, StackElement<?>> defaultValues = new LinkedHashMap<>(size / 2);
            final Map<String, Program> inheritedFunctions = new HashMap<>();
            final List<String> types = new ArrayList<>();
            types.add(key);
            for (int i = 0; i < size; i += 2) {
                final Statement stype = body.getStatements().get(i);
                final Statement sname = body.getStatements().get(i + 1);
                final boolean defaultPossible = size - i >= 4;
                final Statement next = defaultPossible ? body.getStatements().get(i + 2) : null;
                if (!(stype instanceof AccessTableStatement && sname instanceof AccessTableStatement)) {
                    throw new AliceRuntimeError("malformed struct definition!");
                }
                final String t = ((AccessTableStatement) stype).getKey();
                if (defaultPossible && ((AccessTableStatement) next).getKey().equals("=")) {
                    final Statement value = body.getStatements().get(i + 3);
                    if (!(value instanceof PushStatement<?>)) {
                        throw new AliceRuntimeError("value for " + ((AccessTableStatement) sname).getKey() + " missing!");
                    }
                    if (TypeStatement.typeMatches(t, TypeStatement.getType(((PushStatement<?>) value).getValue()))) {
                        final String name = ((AccessTableStatement) sname).getKey();
                        defaultValues.put(name, ((PushStatement<?>) value).getValue());
                        members.put(name, t);
                    } else {
                        throw new AliceRuntimeError("wrong value " + ((PushStatement<?>) value).getValue() + " for default member " + ((AccessTableStatement) sname).getKey());
                    }
                    i += 2;
                } else if (defaultPossible && ((AccessTableStatement) next).getKey().equals("=!")) {
                    final Program value = ((PushStatement<ProgramStackElement>) body.getStatements().get(i + 3)).getValue().getValue();
                    final AliceStack tmp = AliceStack.initialize(10);
                    value.execute(tmp, table);
                    final StackElement<?> actualValue = tmp.pop();
                    if (TypeStatement.typeMatches(t, TypeStatement.getType(actualValue))) {
                        final String name = ((AccessTableStatement) sname).getKey();
                        defaultValues.put(name, actualValue);
                        members.put(name, t);
                    } else {
                        throw new AliceRuntimeError("wrong value " + actualValue + " for default member " + ((AccessTableStatement) sname).getKey());
                    }
                    i += 2;
                } else {
                    if ("extends".equals(t)) {
                        final String key = ((AccessTableStatement) sname).getKey();
                        final AliceStruct struct = table.getStructs().getOrDefault(key, null);
                        if (struct == null) {
                            throw new AliceRuntimeError("cannot extend unknown struct " + key);
                        }
                        types.addAll(struct.getTypes());
                        inheritedFunctions.putAll(struct.getFunctions());
                        defaultValues.putAll(struct.getDefaultValues());
                        members.putAll(struct.getMembers());
                    } else {
                        members.put(((AccessTableStatement) sname).getKey(), t);
                    }
                }
            }
            table.putStruct(new AliceStruct(types, members, defaultValues, inheritedFunctions));
            nextIsGlobal = nextIsStruct = false;
            return;
        }
        // export nfun "alice.lang.stdio":Pln
        if (nextIsNfun) {
            ProgramStackElement e = null; 
            try {
                e = new ProgramStackElement(new NativeProgram(stack.pop().getString(), AliceParser.sanitizeAliceIdentifier(key)));
            } catch (final NoSuchMethodException ex) {
                ex.printStackTrace();
                throw new AliceRuntimeError(ex.getMessage());
            }
            if (nextIsGlobal) {
                table.putGlobal(key, e);
                nextIsGlobal = false;
            } else {
                table.putNew(key, e);
            }
            nextIsNfun = false;
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
