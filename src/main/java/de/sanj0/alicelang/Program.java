package de.sanj0.alicelang;

import de.sanj0.alicelang.statements.PrintFullStackStatement;

import java.util.*;

/**
 * A list of statements
 */
public class Program extends Statement {
    private List<Statement> statements;
    private boolean createScope = true;
    public boolean isFunctionCall = false;
    public String file;

    public Program(final List<Statement> statements) {
        this.statements = statements;
    }

    public Program noScope() {
        createScope = false;
        return this;
    }

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        if (createScope)
            table.putScope();
        for (final Statement statement : statements) {
            statement.thenReturn = false;
            statement.thenBreak = false;
            try {
                statement.execute(stack, table);
            } catch (Exception e) {
                if (AliceParser.DEBUG) {
                    System.out.println("last statement: " + statement);
                    System.out.println("stack:");
                    new PrintFullStackStatement().execute(stack, table);
                    AliceParser.DEBUG = false;
                }
                AliceParser.currentFile = file;
                throw new AliceRuntimeError("\n" + statement + "\t@" + statement.lineNumber + "(" + statement.startIndex + "...):\t" + e.getMessage());
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
        if (createScope)
            table.dropScope();
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
