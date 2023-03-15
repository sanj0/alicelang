package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.ProgramStackElement;

import java.util.Map;
import java.util.Map.Entry;

// implement Struct (fun ():function) end
public class EndStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final Program body = (Program) stack.pop().getValue();
        final String structName = (String) stack.pop().getValue();
        final AliceStruct struct = table.getStructs().getOrDefault(structName, null);
        if (struct == null) {
            throw new AliceRuntimeError("cannot implement for unknown struct " + structName);
        }
        table.putScope();
        body.noScope().execute(stack, table);
        final Map<String, AliceTable.TableValue> scope = table.dropScope();
        for (final Entry<String, AliceTable.TableValue> e : scope.entrySet()) {
            if (!(e.getValue().value instanceof ProgramStackElement)) {
                throw new AliceRuntimeError("struct implementation can only define functions!");
            }
            struct.getFunctions().put(e.getKey(), ((ProgramStackElement) e.getValue().value).getValue());
        }
    }
}
