package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.ProgramStackElement;
import de.sanj0.alicelang.stackelements.StringStackElement;

import java.util.List;

public class AbstractStatement extends Statement {
    private static final List<Statement> bodyStatements = List.of(
            new PushStatement<>(new StringStackElement("abstract method not implemented!\n")),
            new AccessTableStatement("throw")
    );

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        stack.push(new ProgramStackElement(new Program(bodyStatements, AliceParser.currentFile)));
    }
}
