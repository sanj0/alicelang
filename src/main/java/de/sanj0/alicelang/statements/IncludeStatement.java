package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.cli.Main;
import de.sanj0.alicelang.stackelements.StringStackElement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class IncludeStatement extends Statement {
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        final StackElement<?> e = stack.pop();
        if (!(e instanceof StringStackElement)) {
            throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + " include expects a string");
        }
        File f = new File(Main.SDK_HOME, e.getString());
        if (!f.exists()) {
            f = new File(e.getString());
        }
        if (AliceParser.includedFiles.contains(f.getAbsolutePath())) {
            return;
        } else {
            AliceParser.includedFiles.add(f.getAbsolutePath());
        }
        final String previousFile = AliceParser.currentFile;
        AliceParser.currentFile = f.getPath();
        try {
            final Program program = new AliceParser(Files.readString(f.toPath())).parse();
            program.file = f.getAbsolutePath();
            program.execute(stack, table);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        AliceParser.currentFile = previousFile;
    }

    @Override
    public String toString() {
        return "include()";
    }
}
