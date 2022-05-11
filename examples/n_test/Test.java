import de.sanj0.alicelang.*;

public class Test implements NativeProvider {
    public boolean execute(final String word, final AliceStack stack, final AliceTable table) {
        switch (word) {
            case "test":
                System.err.println("yessssssssss!");
                return true;
            default: return false;
        }
    }
}
