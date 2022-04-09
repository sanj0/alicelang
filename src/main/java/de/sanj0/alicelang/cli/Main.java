package de.sanj0.alicelang.cli;

import de.sanj0.alicelang.AliceParser;
import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Program;
import de.sanj0.alicelang.stackelements.NumberStackElement;
import de.sanj0.alicelang.stackelements.StringStackElement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Entry point of the cli tool
 */
public class Main {

    public static File SDK_HOME;
    private static final String STD_INCLUDE_PHRASE = "\"std.alice\" include";

    public static void main(String[] args) throws IOException {
        setSDK_HOME();
        if (args.length == 0 || "live".equals(args[0])) {
            runLive(args);
        } else {
            runFile(args);
        }
    }

    private static void setSDK_HOME() {
        final String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac")) {
            SDK_HOME = new File("/Library/de.sanj0.alicelang/sdk/");
        } else if (os.contains("windows")) {
            SDK_HOME = new File(System.getProperty("user.dir"), ".alicelang/sdk");
        } else {
            SDK_HOME = new File(System.getProperty("user.dir"), ".alicelang/sdk");
        }
    }

    private static void putArgs(final String[] args, final AliceTable table) {
        table.put("argc", new NumberStackElement((double) args.length));
        for (int i = 0; i < args.length; i++) {
            table.put("arg" + i, new StringStackElement(args[i]));
        }
    }

    private static void runFile(final String[] args) throws IOException {
        final File f = new File(args[0]);
        final String s = Files.readString(f.toPath());
        final AliceTable table = AliceTable.initialize(64);
        putArgs(Arrays.copyOfRange(args, 1, args.length), table);
        AliceParser.currentFile = f.getAbsolutePath();
        final Program program = new AliceParser(STD_INCLUDE_PHRASE + "\n" + s).parse();
        program.execute(AliceStack.initialize(64), table);
    }

    private static void runLive(final String[] args) {
        final Scanner stdin = new Scanner(System.in);
        final AliceStack stack = AliceStack.initialize(64);
        final AliceTable table = AliceTable.initialize(64);
        if (args.length > 0) {
            putArgs(Arrays.copyOfRange(args, 1, args.length), table);
        } else {
            putArgs(new String[0], table);
        }
        AliceParser.currentFile = "live";
        AliceParser.currentLine = 0;
        new AliceParser(STD_INCLUDE_PHRASE).parse().execute(stack, table);
        table.putScope();
        while (true) {
            System.out.print("alice>>");
            final String s = stdin.nextLine();
            if ("/exit".equals(s)) break;
            new AliceParser(s).parse().noScope().execute(stack, table);
        }
    }
}
