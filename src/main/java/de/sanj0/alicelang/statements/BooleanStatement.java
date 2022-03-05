package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.NumberStackElement;

/**
 * eq: pops two elements and pushes 1 if they are equal, 0 if they aren't
 * lt: pops two elements and pushes 1 if the second one is less than the first
 *      e.g.: 1 2 lt = 1 ("1 < 2 = true")
 * gt: pops two elements and pushes 1 if the second one is less than the first
 * and: pops two elements and pushes 1 if both were not 0 and 0 otherwise
 * or: pops two elements and pushed 1 if either of them was not 0
 */
public class BooleanStatement {
    public static class EqStatement extends Statement {
        @Override
        public void execute(final AliceStack stack, final AliceTable table) {
            final StackElement<?> b = stack.pop();
            final StackElement<?> a = stack.pop();
            stack.push(new NumberStackElement(a.equals(b) ? 1d : 0d));
        }

        @Override
        public String toString() {
            return "equals()";
        }
    }

    public static class LtStatement extends Statement {
        @Override
        public void execute(final AliceStack stack, final AliceTable table) {
            final StackElement<?> b = stack.pop();
            final StackElement<?> a = stack.pop();
            if (!(a instanceof NumberStackElement && b instanceof NumberStackElement)) {
                throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + " lt expects two numbers");
            }
            stack.push(new NumberStackElement(a.getDouble() < b.getDouble() ? 1d : 0d));
        }

        @Override
        public String toString() {
            return "lessThan()";
        }
    }

    public static class GtStatement extends Statement {
        @Override
        public void execute(final AliceStack stack, final AliceTable table) {
            final StackElement<?> b = stack.pop();
            final StackElement<?> a = stack.pop();
            if (!(a instanceof NumberStackElement && b instanceof NumberStackElement)) {
                throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + " gt expects two numbers");
            }
            stack.push(new NumberStackElement(a.getDouble() > b.getDouble() ? 1d : 0d));
        }

        @Override
        public String toString() {
            return "GreaterThan()";
        }
    }

    public static class AndStatement extends Statement {
        @Override
        public void execute(final AliceStack stack, final AliceTable table) {
            final StackElement<?> b = stack.pop();
            final StackElement<?> a = stack.pop();
            if (!(a instanceof NumberStackElement && b instanceof NumberStackElement)) {
                throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + " and expects two numbers");
            }
            stack.push(new NumberStackElement(a.getDouble() != 0 && b.getDouble() != 0 ? 1d : 0d));
        }

        @Override
        public String toString() {
            return "and()";
        }
    }

    public static class OrStatement extends Statement {
        @Override
        public void execute(final AliceStack stack, final AliceTable table) {
            final StackElement<?> b = stack.pop();
            final StackElement<?> a = stack.pop();
            if (!(a instanceof NumberStackElement && b instanceof NumberStackElement)) {
                throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + " and expects two numbers");
            }
            stack.push(new NumberStackElement(a.getDouble() != 0 || b.getDouble() != 0 ? 1d : 0d));
        }

        @Override
        public String toString() {
            return "or()";
        }
    }
}
