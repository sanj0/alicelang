package de.sanj0.alicelang;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * A list of statements
 */
public class Program extends Statement {
    private List<Statement> statements;
    public boolean isFunctionCall = false;
    public String file;

    public Program(final List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        if (AliceParser.DEBUG) {
            System.out.println("\n--- start local --- (file: " + file + "; size: " + statements.size() + "; fun call: " + isFunctionCall + ")");
            System.out.println("prompt before each statement with original stack and table, empty to proceed, .l and .sl available");
        }
        boolean skipLocalDebug = false;
        for (final Statement statement : statements) {
            statement.thenReturn = false;
            statement.thenBreak = false;
            if (AliceParser.DEBUG && !skipLocalDebug) {
                System.out.println();
                AliceParser.DEBUG = false;
                final Scanner stdin = new Scanner(System.in);
                System.out.println("next: " + statement);
                System.out.print("alice>");
                String in = stdin.nextLine();
                while (!in.isEmpty()) {
                    if (".l".equals(in)) {
                        for (final Statement s : statements) {
                            if (s == statement) {
                                System.out.print("->");
                            }
                            System.out.println(s);
                        }
                    } else if (".sl".equals(in)) {
                        skipLocalDebug = true;
                        break;
                    } else {
                        try {
                            new AliceParser(in, -1).parse().execute(stack, table);
                        } catch (Exception e) {
                            System.err.println("an error occurred in debug prompt ...");
                        }
                    }
                    System.out.print("alice>");
                    in = stdin.nextLine();
                }
                AliceParser.DEBUG = true;
            }
            try {
                statement.execute(stack, table);
            } catch (Exception e) {
                AliceParser.currentFile = file;
                throw new AliceRuntimeError("\n" + statement + "\t@" + statement.lineNumber + "(" + statement.startIndex + "...):\t" + e.getMessage());
            }
            if (statement.thenBreak) {
                thenBreak = true;
                break;
            }
            if (statement.thenReturn) {
                if (!isFunctionCall) thenReturn = true;
                return;
            }
        }
        if (AliceParser.DEBUG) {
            System.out.println("\n--- end local --- (file: " + file + "; size: " + statements.size() + "; fun call: " + isFunctionCall + ")");
        }
    }

    /**
     * Gets {@link #statements}.
     *
     * @return the value of {@link #statements}
     */
    public List<Statement> getStatements() {
        return statements;
    }

    /**
     * Sets {@link #statements}.
     *
     * @param statements the new value of {@link #statements}
     */
    public void setStatements(final List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Program)) return false;
        Program program = (Program) o;
        return Objects.equals(statements, program.statements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statements);
    }
}
