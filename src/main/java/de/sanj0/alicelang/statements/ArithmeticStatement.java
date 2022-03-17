package de.sanj0.alicelang.statements;

import de.sanj0.alicelang.*;
import de.sanj0.alicelang.stackelements.NumberStackElement;
import de.sanj0.alicelang.stackelements.StringStackElement;

/**
 * Pops two values, performs some arithmetic and pushes the result.
 * Addition and multiplication also works on strings where the result will always
 * be a string.
 * ++ and -- only pop one element and it better be a number
 */
public abstract class ArithmeticStatement extends Statement {
    public static class MultiplicationStatement extends ArithmeticStatement {
        @Override
        public void execute(final AliceStack stack, final AliceTable table) {
            final StackElement<?> b = stack.pop();
            final StackElement<?> a = stack.pop();
            if (a instanceof NumberStackElement) {
                if (b instanceof NumberStackElement) {
                    stack.push(new NumberStackElement(a.getDouble() * b.getDouble()));
                } else if (b instanceof StringStackElement) {
                    stack.push(new StringStackElement(b.getString().repeat(a.getInt())));
                } else {
                    throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + "multiplication expects two of number and/or string");
                }
            } else if (a instanceof StringStackElement) {
                if (b instanceof NumberStackElement) {
                    stack.push(new StringStackElement(a.getString().repeat(b.getInt())));
                } else if (b instanceof StringStackElement) {
                    throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + "can't multiply string and string");
                } else {
                    throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + "multiplication expects two of number and/or string");
                }
            }
        }

        @Override
        public String toString() {
            return "*";
        }
    }

    public static class SubtractionStatement extends ArithmeticStatement {
        @Override
        public void execute(final AliceStack stack, final AliceTable table) {
            final StackElement<?> b = stack.pop();
            final StackElement<?> a = stack.pop();
            if (a instanceof NumberStackElement) {
                if (b instanceof NumberStackElement) {
                    stack.push(new NumberStackElement(a.getDouble() - b.getDouble()));
                } else {
                    throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + "subtraction expects two numbers");
                }
            } else {
                throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + "subtraction expects two numbers");
            }
        }

        @Override
        public String toString() {
            return "-";
        }
    }

    public static class DivisionStatement extends ArithmeticStatement {
        @Override
        public void execute(final AliceStack stack, final AliceTable table) {
            final StackElement<?> b = stack.pop();
            final StackElement<?> a = stack.pop();
            if (a instanceof NumberStackElement) {
                if (b instanceof NumberStackElement) {
                    if (b.getDouble() == 0) {
                        throw new AliceRuntimeError(AliceRuntimeError.ARITHMETIC_ERROR_ + "division by 0");
                    }
                    stack.push(new NumberStackElement(a.getDouble() / b.getDouble()));
                } else {
                    throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + "division expects two numbers");
                }
            } else {
                throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + "division expects two numbers");
            }
        }

        @Override
        public String toString() {
            return "/";
        }
    }

    public static class AdditionStatement extends ArithmeticStatement {
        @Override
        public void execute(final AliceStack stack, final AliceTable table) {
            final StackElement<?> b = stack.pop();
            final StackElement<?> a = stack.pop();
            if (a instanceof NumberStackElement) {
                if (b instanceof NumberStackElement) {
                    stack.push(new NumberStackElement(a.getDouble() + b.getDouble()));
                } else {
                    stack.push(new StringStackElement(a.getDouble() + b.getString()));
                }
            } else {
                if (b instanceof NumberStackElement) {
                    stack.push(new StringStackElement(a.getString() + b.getDouble()));
                } else {
                    stack.push(new StringStackElement(a.getString() + b.getString()));
                }
            }
        }

        @Override
        public String toString() {
            return "+";
        }
    }

    // a**b = a^b
    public static class PowerStatement extends ArithmeticStatement {
        @Override
        public void execute(final AliceStack stack, final AliceTable table) {
            final StackElement<?> b = stack.pop();
            final StackElement<?> a = stack.pop();
            if (a instanceof NumberStackElement) {
                if (b instanceof NumberStackElement) {
                    stack.push(new NumberStackElement(Math.pow(a.getDouble(), b.getDouble())));
                } else {
                    throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + "power expects two numbers");
                }
            } else {
                throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + "power expects two numbers");
            }
        }

        @Override
        public String toString() {
            return "**";
        }
    }

    // a % b
    public static class ModuloStatement extends ArithmeticStatement {
        @Override
        public void execute(final AliceStack stack, final AliceTable table) {
            final StackElement<?> b = stack.pop();
            final StackElement<?> a = stack.pop();
            if (a instanceof NumberStackElement) {
                if (b instanceof NumberStackElement) {
                    if (b.getDouble() == 0) {
                        throw new AliceRuntimeError(AliceRuntimeError.ARITHMETIC_ERROR_ + "division by 0");
                    }
                    stack.push(new NumberStackElement(a.getDouble() % b.getDouble()));
                } else {
                    throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + "modulo expects two numbers");
                }
            } else {
                throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + "modulo expects two numbers");
            }
        }

        @Override
        public String toString() {
            return "%";
        }
    }

    // a++ / ++a
    public static class IncrementStatement extends ArithmeticStatement {
        @Override
        public void execute(final AliceStack stack, final AliceTable table) {
            final StackElement<?> a = stack.pop();
            if (a instanceof NumberStackElement) {
                stack.push(new NumberStackElement(a.getDouble() + 1));
            } else {
                throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + "increment expects a number");
            }
        }

        @Override
        public String toString() {
            return "++";
        }
    }

    // a-- / --a
    public static class DecrementStatement extends ArithmeticStatement {
        @Override
        public void execute(final AliceStack stack, final AliceTable table) {
            final StackElement<?> a = stack.pop();
            if (a instanceof NumberStackElement) {
                stack.push(new NumberStackElement(a.getDouble() - 1));
            } else {
                throw new AliceRuntimeError(AliceRuntimeError.INVALID_TYPE_ + "decrement expects a number");
            }
        }

        @Override
        public String toString() {
            return "--";
        }
    }
}
