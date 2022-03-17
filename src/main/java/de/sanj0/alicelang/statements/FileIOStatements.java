package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceRuntimeError;
import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;
import de.sanj0.alicelang.stackelements.StringStackElement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileIOStatements {
    // [... e "path/to/file"] writef -> [...]
    // where e is any stack element
    public static class WriteFileStatement extends Statement {
        @Override
        public void execute(final AliceStack stack, final AliceTable table) {
            final File f = new File(stack.pop().getString());
            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (f.isDirectory()) {
                throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + "cannot write to directory " + f.getAbsolutePath());
            }
            final String content = stack.pop().getString();
            try {
                Files.writeString(f.toPath(), content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // [... "path/to/file"] readf -> [... fileContent]
    public static class ReadFileStatement extends Statement {
        @Override
        public void execute(final AliceStack stack, final AliceTable table) {
            final File f = new File(stack.pop().getString());
            if (!f.exists()) {
                throw new AliceRuntimeError("can't read from nonexistent file " + f.getAbsolutePath());
            } else if (f.isDirectory()) {
                throw new AliceRuntimeError("can't read from directory " + f.getAbsolutePath());
            }
            try {
                stack.push(new StringStackElement(Files.readString(f.toPath())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
