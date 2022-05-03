import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.*;
import de.sanj0.alicelang.statements.*;

public class run {
    {}
    public static void main(String[] args) {
        final AliceStack stack = AliceStack.initialize(64);
        final AliceTable table = AliceTable.initialize(64);
        new de.sanj0.alicelang.statements.AccessTableStatement().execute(stack, table);
        new de.sanj0.alicelang.statements.PushStatement().execute(stack, table);
        new de.sanj0.alicelang.statements.AccessTableStatement().execute(stack, table);
        new de.sanj0.alicelang.statements.PushStatement().execute(stack, table);
        new de.sanj0.alicelang.statements.AccessTableStatement().execute(stack, table);
        new de.sanj0.alicelang.statements.PushStatement().execute(stack, table);
        new de.sanj0.alicelang.statements.PushStatement().execute(stack, table);
        new de.sanj0.alicelang.statements.NurStatement().execute(stack, table);
    }
}
