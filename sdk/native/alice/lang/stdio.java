package alice.lang;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.*;

import java.util.Scanner;

public class Stdio implements NativeProvider {
    private static final Scanner stdin = new Scanner(System.in);

    public boolean execute(final String word, final AliceStack stack, final AliceTable table) {
        return false;
    }

    public void print(final AliceStack stack, final AliceTable table) {
        System.out.print(stack.peek().getString());
    }

    public void p(final AliceStack stack, final AliceTable table) {
        System.out.print(stack.peek().getString());
    }

    public void Print(final AliceStack stack, final AliceTable table) {
        System.out.print(stack.pop().getString());
    }

    public void P(final AliceStack stack, final AliceTable table) {
        System.out.print(stack.pop().getString());
    }

    public void println(final AliceStack stack, final AliceTable table) {
        System.out.println(stack.peek().getString());
    }

    public void Println(final AliceStack stack, final AliceTable table) {
        System.out.println(stack.pop().getString());
    }

    public void err_print(final AliceStack stack, final AliceTable table) {
        System.err.print(stack.peek().getString());
    }

    public void err_Print(final AliceStack stack, final AliceTable table) {
        System.err.print(stack.pop().getString());
    }

    public void err_println(final AliceStack stack, final AliceTable table) {
        System.err.println(stack.peek().getString());
    }

    public void err_Println(final AliceStack stack, final AliceTable table) {
        System.err.println(stack.pop().getString());
    }

    public void read_string(final AliceStack stack, final AliceTable table) {
        stack.push(new StringStackElement(stdin.nextLine()));
    }

    public void r(final AliceStack stack, final AliceTable table) {
        stack.push(new StringStackElement(stdin.nextLine()));
    }

    public void read_number(final AliceStack stack, final AliceTable table) {
        stack.push(new NumberStackElement(Double.valueOf(stdin.nextLine())));
    }
}
