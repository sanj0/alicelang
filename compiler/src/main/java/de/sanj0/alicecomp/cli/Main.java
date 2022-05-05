package de.sanj0.alicecomp.cli;

import java.util.*;
import java.io.*;
import java.nio.file.*;
import de.sanj0.alicelang.*;

public class Main {

    public static final String OPTION_TARGET_KEY = "-t";
    public static final String OPTION_TARGET_KEY_L = "--target";
    public static final String OPTION_TARGET_VAL_SOURCE= "source";
    public static final String OPTION_TARGET_VAL_CLASS= "class";

    public static void main(String[] args) throws IOException {
        final Map<Option, String> options = parseOptions(args);
        final File f = new File(options.get(Option.FILE));
        final Program script = new AliceParser(Files.readString(f.toPath())).parse();
    }

    private static Map<Option, String> parseOptions(final String[] args) {
        final Map<Option, String> map = new HashMap<>(args.length / 2);
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case OPTION_TARGET_KEY_L:
                case OPTION_TARGET_KEY:
                    map.put(Option.TARGET, args[++i]);
                    break;
                default:
                    map.put(Option.FILE, args[i]);
            }
        }
        if (!map.containsKey(Option.TARGET)) {
            map.put(Option.TARGET, OPTION_TARGET_VAL_CLASS);
        }
        return map;
    }

    public enum Option {
        TARGET,
        FILE
    }
}
