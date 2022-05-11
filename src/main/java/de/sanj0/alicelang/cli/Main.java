package de.sanj0.alicelang.cli;

import de.sanj0.alicelang.*;
import static de.sanj0.alicelang.statements.NIncludeStatement.*;
import de.sanj0.alicelang.stackelements.NumberStackElement;
import de.sanj0.alicelang.stackelements.StringStackElement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.net.URL;

/**
 * Entry point of the cli tool.
 * Usage:
 * alice [-np / --nativepath paths/of:natives] file [program args...]
 */
public class Main {

    public static File SDK_HOME;
    public static String SDK_N_PATH;
    private static final String STD_INCLUDE_PHRASE = "\"std.alice\" include";

    public static final String P_NATIVE_PATH_KEY_SHORT = "-np";
    public static final String P_NATIVE_PATH_KEY_LONG = "--nativepath";
    public static final String P_RUN_SHORT = "-r";
    public static final String P_RUN_LONG = "--run";
    public static final String P_INCLUDE_STD_SHORT = "-std";
    public static final String P_INCLUDE_STD_LONG = "--include-std";

    public enum Parameter {
        // a File object
        FILE,
        // an Array of Strings
        NATIVE_PATH,
        // an ArrayList
        PARGS,
        // a boolean
        RUN,
        // a boolean
        INCLUDE_STD
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        setSDK_HOME();
        final Map<Parameter, Object> parameters = parseParameters(args);
        final String[] np = (String[]) parameters.getOrDefault(Parameter.NATIVE_PATH, new String[0]);
        N_PATH = new URL[np.length + 1]; // static import from NIncludeStatement
        N_PATH[0] = new File(SDK_N_PATH).toURI().toURL();
        for (int i = 1; i < np.length; i++) {
            N_PATH[i] = new File(np[i]).toURI().toURL();
        }
        if (args.length == 0 || "live".equals(args[0])) {
            runLive(parameters);
        } else {
            runFile(parameters);
        }
    }

    private static Map<Parameter, Object> parseParameters(final String[] args) {
        final Map<Parameter, Object> map = new HashMap<>(args.length / 2);
        final List<String> pargs = new ArrayList<>();
        boolean fileNameParsed = false;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case P_NATIVE_PATH_KEY_LONG:
                case P_NATIVE_PATH_KEY_SHORT:
                    map.put(Parameter.NATIVE_PATH, args[++i].split(":"));
                    break;
                case P_RUN_LONG:
                case P_RUN_SHORT:
                    map.put(Parameter.RUN, Boolean.valueOf(args[++i]));
                    break;
                case P_INCLUDE_STD_LONG:
                case P_INCLUDE_STD_SHORT:
                    map.put(Parameter.INCLUDE_STD, Boolean.valueOf(args[++i]));
                    break;
                default:
                    if (fileNameParsed)
                        pargs.add(args[i]);
                    else {
                        map.put(Parameter.FILE, new File(args[i]));
                        fileNameParsed = true;
                    }
            }
        }
        map.put(Parameter.PARGS, pargs);
        return map;
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
        SDK_N_PATH = new File(SDK_HOME, "native").getAbsolutePath();
    }

    private static void putArgs(final List<String> pargs, final AliceTable table) {
        table.putNew("argc", new NumberStackElement((double) pargs.size()));
        for (int i = 0; i < pargs.size(); i++) {
            table.putNew("arg" + i, new StringStackElement(pargs.get(i)));
        }
    }

    private static void runFile(final Map<Parameter, Object> p) throws IOException {
        final File f = (File) p.get(Parameter.FILE);
        final String s = Files.readString(f.toPath());
        final AliceTable table = AliceTable.initialize(64);
        if ((boolean) p.getOrDefault(Parameter.INCLUDE_STD, true)) {
            // ensures no stack-bleed
            new AliceParser(STD_INCLUDE_PHRASE).parse().execute(AliceStack.initialize(32), table);
        }
        putArgs((List<String>) p.get(Parameter.PARGS), table);
        AliceParser.currentFile = f.getPath();
        final Program program = new AliceParser(s).parse();
        if ((boolean) p.getOrDefault(Parameter.RUN, true)) {
            program.execute(AliceStack.initialize(64), table);
        } else {
            System.out.println("done parsing " + s.lines().count() + " lines into "
                    + program.getStatements().size() + " level 0 statements");
        }
    }

    private static void runLive(final Map<Parameter, Object> p) {
        final Scanner stdin = new Scanner(System.in);
        final AliceStack stack = AliceStack.initialize(64);
        final AliceTable table = AliceTable.initialize(64);
        putArgs((List<String>) p.get(Parameter.PARGS), table);
        AliceParser.currentFile = "live";
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
