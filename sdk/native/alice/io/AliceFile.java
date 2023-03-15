package alice.io;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.*;

import java.io.File;

public class AliceFile implements NativeProvider {
    @Override
    public boolean execute(final String word, final AliceStack stack, final AliceTable table) {
        return false;
    }

    public void qmexists(final AliceStack stack, final AliceTable table) {

    }

    public void create_new_file(final AliceStack stack, final AliceTable table) {
    }

    public void mkdir(final AliceStack stack, final AliceTable table) {
    }

    public void writes(final AliceStack stack, final AliceTable table) {
    }

    public void reads(final AliceStack stack, final AliceTable table) {
    }

    private static String getPath(final AliceTable t) {
        return ((StructInstance) t.get(AliceParser.WRD_SELF)).get("path").getString();
    }
}
