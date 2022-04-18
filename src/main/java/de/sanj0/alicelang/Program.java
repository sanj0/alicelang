package de.sanj0.alicelang;

import de.sanj0.alicelang.statements.PrintFullStackStatement;

import java.util.*;

/**
 * A list of statements
 */
public class Program extends Statement {
    private static boolean alreadyErroring = false;

    private List<Statement> statements;
    private Map<String, StackElement<?>> locals = null;
    private boolean createScope = true;
    public boolean isFunctionCall = false;
    public String file;

    public Program(final List<Statement> statements, final String location) {
        this.statements = statements;
        this.file = location;
    }

    public Program noScope() {
        createScope = false;
        return this;
    }

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        if (locals != null) {
            table.putScope(locals);
        } else if (createScope)
            table.putScope();

        for (final Statement statement : statements) {
            statement.thenReturn = false;
            statement.thenBreak = false;
            try {
                statement.execute(stack, table);
            } catch (Exception e) {
                if (!alreadyErroring) {
                    System.err.print("Error ");
                    alreadyErroring = true;
                } else {
                    System.err.print("\t");
                }
                System.err.print("when executing " + statement + " in ");
                System.err.println(file + "@" + statement.lineNumber + ":" + statement.startIndex);
                throw e;
            }
            if (statement.thenBreak) {
                thenBreak = true;
                break;
            }
            if (statement.thenReturn) {
                if (!isFunctionCall) thenReturn = true;
                break;
            }
        }
        if (createScope || locals != null)
            locals = table.dropScope();
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
