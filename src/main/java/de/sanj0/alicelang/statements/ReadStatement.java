package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.AliceStack;
import de.sanj0.alicelang.AliceTable;
import de.sanj0.alicelang.Statement;
import de.sanj0.alicelang.stackelements.StringStackElement;

import java.util.Scanner;

public class ReadStatement extends Statement {
    private static final Scanner STD_IN = new Scanner(System.in);
    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        stack.push(new StringStackElement(STD_IN.nextLine()));
    }

    @Override
    public String toString() {
        return "read()";
    }
}
