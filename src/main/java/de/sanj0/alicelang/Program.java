package de.sanj0.alicelang;

import java.util.List;
import java.util.Objects;

/**
 * A list of statements
 */
public class Program extends Statement {
    private List<Statement> statements;

    public Program(final List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void execute(final AliceStack stack, final AliceTable table) {
        for (final Statement statement : statements) {
            AliceParser.currentLine = statement.lineNumber;
            if (AliceParser.DEBUG)
                System.out.println(">" + statement);
            statement.execute(stack, table);
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
