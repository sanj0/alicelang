package de.sanj0.alicelang;

import de.sanj0.alicelang.stackelements.NumberStackElement;
import de.sanj0.alicelang.stackelements.ProgramStackElement;
import de.sanj0.alicelang.stackelements.StringStackElement;
import de.sanj0.alicelang.statements.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class AliceParser {
    public static String currentFile = "";
    public static Set<String> includedFiles = new HashSet<>();

    private final String code;
    private char[] data;
    private int index;
    private int lineNumber;
    private int lastLineBreakIndex = 0;

    public AliceParser(final String code, final int lineNumOffset) {
        this.code = code;
        this.lineNumber = lineNumOffset;
    }

    public AliceParser(final String code) {
        this(code, 0);
    }

    public Program parse() {
        // we are only traversing it in order, so a linked list is better here
        final List<Statement> statements = new LinkedList<>();
        data = code.toCharArray();

        while (index < data.length) {
            if (skipWhitespacesAndComments()) break;
            final int l = lineNumber;
            final int startIndex = index - lastLineBreakIndex;
            final Statement statement = parseStatement();
            if (statement == null) continue;
            statement.lineNumber = l;
            statement.startIndex = startIndex;
            statements.add(statement);
        }

        return new Program(statements, currentFile);
    }

    private Statement parseStatement() {
        final char start = pop();
        if ((isNumber(start) && start != CMD_STRUCT_GET) || (start == '-' && !done() && isNumber(peek()))) {
            return new PushStatement<>(parseNumber(start));
        } else if (start == ':') {
            return new PutOnTableStatement(consumeWord(start).substring(1));
        } else if (start == CMD_STRUCT_GET) {
            return new StructGetStatement(consumeWord(start).substring(1));
        } else if (start == CMD_STRUCT_SET) {
            return new StructSetStatement(consumeWord(start).substring(1));
        } else if (start == '"') {
            return new PushStatement<>(parseString());
        } else if (start == '(' || start == '{') {
            return new PushStatement<>(parseSubProgram(start));
        } else if (startsOperator(start)) {
            return parseOperator(start);
        } else {
            return parseWord(start);
        }
    }

    // sanitizes alice identifiers for use in java native methods:
    // ? -> qm
    // - -> _
    // . -> $
    // ^\d -> ^_\d
    public static String sanitizeAliceIdentifier(final String s) {
        final String s2 = s.replaceAll("\\?", "qm").replace("-", "_").replace("\\.", "\\$");
        if (isNumber(s2.charAt(0)))
            return "_" + s2;
        else
            return s2;
    }

    private static boolean isNumber(final char c) {
        return c >= '0' && c <= '9' || c == '.';
    }

    private Statement handleCommand(final String word) {
        if (SKIPS.contains(word)) return null;
        if (word.length() > 1) return new AccessTableStatement(word);
        return switch (word.charAt(0)) {
            case CMD_PRINT_FULL_STACK -> new PrintFullStackStatement();
            case CMD_EXECUTE_SUB_PROGRAM -> new ExecuteSubProgramStatement();
            case CMD_PRINT_LC -> new PrintStatement(false);
            case CMD_PRINT_UC -> new PrintStatement(true);
            case CMD_READ -> new ReadStatement();
            case CMD_DUPLICATE -> new DuplicateStatement();
            case CMD_PRINT_TABLE -> new PrintTableStatement();
            default -> new AccessTableStatement(word);
        };
    }

    // table contents and while, if etc...
    // a word is only terminated by either a whitespace or
    // a " or a (
    private Statement parseWord(final char start) {
        final String word = consumeWord(start);
        return switch (word) {
            case WRD_TO_NUMBER -> new ConvertToNumStatement();
            case WRD_TO_STRING -> new ConvertToStringStatement();
            case WRD_WHILE -> new WhileStatement();
            case WRD_SWAP -> new SwapStatement();
            case WRD_DROP -> new DropStatement();
            case WRD_CLEAR -> new ClearStatement();
            case WRD_OVER -> new OverStatement();
            case WRD_ROT -> new RotateStatement();
            case WRD_EQ -> new BooleanStatement.EqStatement();
            case WRD_LT -> new BooleanStatement.LtStatement();
            case WRD_GT -> new BooleanStatement.GtStatement();
            case WRD_AND -> new BooleanStatement.AndStatement();
            case WRD_OR -> new BooleanStatement.OrStatement();
            case WRD_FI -> new FiStatement();
            case WRD_IF -> new IfStatement();
            case WRD_ELIF -> null;
            case WRD_INCLUDE -> new IncludeStatement();
            case WRD_EXISTS -> new ExistsStatement();
            case WRD_TYPE -> new TypeStatement();
            case WRD_EXIT -> new ExitStatement();
            //case WRD_GET -> new GetStatement();
            case WRD_BREAK -> new BreakStatement();
            case WRD_RETURN -> new ReturnStatement();
            case WRD_WRITEF -> new FileIOStatements.WriteFileStatement();
            case WRD_READF -> new FileIOStatements.ReadFileStatement();
            case WRD_PROC -> new ProcStatement();
            case WRD_PPROC -> new ParallelProcStatement();
            case WRD_STACK_SIZE -> new StackSizeStatement();
            case WRD_EXPAND -> new ExpandSubstackStatement();
            case WRD_FOLD -> new FoldSubstackStatement();
            case WRD_LN -> new LnStatement();
            case WRD_LENGTH -> new LengthStatement();
            case WRD_RANDOM -> new RandomStatement();
            case WRD_CHAR_AT -> new CharAtStatement();
            case WRD_TIME -> new TimeStatement();
            case WRD_EVAL -> new EvalStatement(currentFile);
            case WRD_EXPORT -> new ExportStatement();
            case WRD_VAR -> new VarStatement();
            case WRD_CONST -> new ConstStatement();
            case WRD_PURGE -> new PurgeStatement();
            case WRD_UNICODE -> new UnicodeStatement();
            case WRD_CHARACTER -> new CharacterStatement();
            case WRD_NUR -> new NurStatement();
            case WRD_STEPSIZE -> new StepsizeStatement();
            case WRD_POLL -> new PollStatement();
            case WRD_PEEK -> new PeekStatement();
            case WRD_STRUCT -> new StructStatement();
            case WRD_NULL -> new NullStatement();
            case WRD_ARGS -> new ArgsStatement();
            case WRD_IMPLEMENT -> new ImplementStatement();
            case WRD_END -> new EndStatement();
            case WRD_HASHCODE -> new HashCodeStatement();
            case WRD_ABSTRACT -> new AbstractStatement();
            case WRD_TYPES -> new TypesStatement();
            case WRD_NINCLUDE -> new NIncludeStatement();
            case WRD_NATIVE -> new NativeStatement();
            case WRD_SYSPROP -> new SyspropStatement();
            case WRD_NFUN -> new NfunStatement();
            default -> handleCommand(word);
        };
    }

    // a word is only terminated by a " or a ( or any whitespace
    private String consumeWord(final char start) {
        final StringBuilder builder = new StringBuilder();
        builder.append(start);
        char next;
        while (!done() && !Character.isWhitespace(next = peek()) && next != '"'
                && next != '(' && next != ':' && next != '{' && next != ')' && next != '}'
                && next != CMD_STRUCT_GET && next != CMD_STRUCT_SET
                && next != ',' && next != ';') {
            builder.append(next);
            pop();
        }
        return builder.toString();
    }

    private Statement parseOperator(final char start) {
        if (start == '*') {
            if (!done() && peek() == '*') {
                pop();
                return new ArithmeticStatement.PowerStatement();
            } else {
                return new ArithmeticStatement.MultiplicationStatement();
            }
        } else if (start == '/') {
            return new ArithmeticStatement.DivisionStatement();
        } else if (start == '+') {
            if (!done() && peek() == '+') {
                pop();
                return new ArithmeticStatement.IncrementStatement();
            } else {
                return new ArithmeticStatement.AdditionStatement();
            }
        } else if (start == '-') {
            if (!done() && peek() == '-') {
                pop();
                return new ArithmeticStatement.DecrementStatement();
            } else {
                return new ArithmeticStatement.SubtractionStatement();
            }
        } else if (start == '%') {
            return new ArithmeticStatement.ModuloStatement();
        } else {
            throw new AliceParserError("unknown operator " + start);
        }
    }

    private boolean startsOperator(final char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
    }

    private ProgramStackElement parseSubProgram(final char parenthesis) {
        final char waitingFor = parenthesis == '(' ? ')' : '}';
        char next = peek();
        if (next == waitingFor) {
            pop();
            return new ProgramStackElement(new Program(new LinkedList<>(), currentFile));
        }
        final List<Statement> statements = new ArrayList<>();
        do {
            if (skipWhitespacesAndComments()) break;
            if (peek() == waitingFor) break;
            final int l = lineNumber;
            final int startIndex = index - lastLineBreakIndex;
            final Statement statement = parseStatement();
            if (statement == null) continue;
            statement.lineNumber = l;
            statement.startIndex = startIndex;
            statements.add(statement);
        } while (peek() != waitingFor);
        pop();
        final Program p = new Program(statements, currentFile);
        p.file = AliceParser.currentFile;
        return new ProgramStackElement(p);
    }

    private StringStackElement parseString() {
        final StringBuilder builder = new StringBuilder();
        boolean escaped = false;
        char next;
        while (((next = pop()) == '"' && escaped) || (next != '"')) {
            if (next == '\\') {
                if (escaped) {
                    builder.append(next);
                    escaped = false;
                } else {
                    escaped = true;
                }
                continue;
            }

            if (escaped) {
                if (next == 'n') {
                    builder.append(System.lineSeparator());
                    escaped = false;
                } else if (next == '\"') {
                    builder.append(next);
                    escaped = false;
                } else {
                    throw new AliceParserError(AliceParserError.UNKNOWN_ESCAPE_SEQUENCE_ + "\\" + next);
                }
            } else {
                builder.append(next);
            }
        }
        return new StringStackElement(builder.toString());
    }

    private NumberStackElement parseNumber(final char start) {
        final StringBuilder builder = new StringBuilder();
        builder.append(start);
        char next;
        while (!done() && isNumber(next = peek())) {
            builder.append(next);
            pop();
        }
        return new NumberStackElement(Double.valueOf(builder.toString()));
    }

    private boolean skipWhitespacesAndComments() {
        if (peek() == '#') {
            pop();
            char c;
            while ((c = pop()) != '#' && c != '\n') {
                if (index >= data.length - 1) {
                    return true;
                }
            }
        }
        while (Character.isWhitespace(peek()) || peek() == ',' || peek() == ';') {
            if (index >= data.length - 1) {
                return true;
            }
            pop();
        }
        if (peek() == '#') return skipWhitespacesAndComments();
        return false;
    }

    private boolean done() {
        return index > data.length - 1;
    }

    private char pop() {
        if (done()) {
            throw new AliceParserError(AliceParserError.HIT_END_OF_FILE);
        }
        final char c = data[index++];
        if (c == '\n') {
            lineNumber++;
            lastLineBreakIndex = index - 1;
        }
        return c;
    }

    private char peek() {
        if (done()) {
            throw new AliceParserError(AliceParserError.HIT_END_OF_FILE);
        }
        return data[index];
    }

    public static boolean isReservedKeyWord(final String s) {
        return KEYWORDS.contains(s);
    }

    public static final char CMD_PRINT_FULL_STACK = 'f';
    // ex. utils
    public static final char CMD_PRINT_TABLE = 't';
    public static final char CMD_EXECUTE_SUB_PROGRAM = 'e';
    public static final char CMD_PRINT_LC = 'p';
    public static final char CMD_PRINT_UC = 'P';
    // ex. stdio
    public static final char CMD_READ = 'r';
    public static final char CMD_DUPLICATE = 'd';
    public static final char CMD_STRUCT_GET = '.';
    public static final char CMD_STRUCT_SET = '>';
    // ex. types
    public static final String WRD_TO_STRING = "!str";
    public static final String WRD_TO_NUMBER = "!num";
    public static final String WRD_WHILE = "do";
    public static final String WRD_FI = "fi";
    public static final String WRD_IF = "if";
    public static final String WRD_ELIF = "elif";
    public static final String WRD_SWAP = "swap";
    public static final String WRD_CLEAR = "clear";
    public static final String WRD_DROP = "drop";
    public static final String WRD_OVER = "over";
    public static final String WRD_ROT = "rot";
    public static final String WRD_EQ = "eq";
    public static final String WRD_LT = "lt";
    public static final String WRD_GT = "gt";
    public static final String WRD_AND = "and";
    public static final String WRD_OR = "or";
    // ex. math
    public static final String WRD_LN = "ln";
    public static final String WRD_LENGTH = "length";
    public static final String WRD_INCLUDE = "include";
    public static final String WRD_EXISTS = "exists";
    // ex. types
    public static final String WRD_TYPE = "type";
    public static final String WRD_EXIT = "exit";
    // ex. utils
    public static final String WRD_STACK_SIZE = "ssize";
    // ex. math
    public static final String WRD_RANDOM = "random";
    // ex. string
    public static final String WRD_CHAR_AT = "charat";
    public static final String WRD_TIME = "time";
    public static final String WRD_BREAK = "break";
    public static final String WRD_RETURN = "return";
    // ex. file
    public static final String WRD_WRITEF = "writef";
    public static final String WRD_READF = "readf";
    public static final String WRD_EVAL = "eval";
    // ex. system
    public static final String WRD_PROC = "proc";
    public static final String WRD_PPROC = "pproc";
    public static final String WRD_EXPAND = "expand";
    public static final String WRD_FOLD = "fold";
    public static final String WRD_EXPORT = "export";
    public static final String WRD_VAR = "var";
    public static final String WRD_CONST = "const";
    public static final String WRD_PURGE = "purge";
    public static final String WRD_UNICODE = "unicode";
    public static final String WRD_CHARACTER = "character";
    public static final String WRD_NUR = "nur";
    public static final String WRD_STEPSIZE = "stepsize";
    public static final String WRD_POLL = "poll";
    public static final String WRD_PEEK = "peek";
    public static final String WRD_STRUCT = "struct";
    public static final String WRD_NULL = "null";
    public static final String WRD_ARGS = "args";
    // doesn't represent a statement, only there to reserve the keyword
    public static final String WRD_SELF = "self";
    public static final String WRD_IMPLEMENT = "implement";
    public static final String WRD_END = "end";
    public static final String WRD_HASHCODE = "hashcode";
    public static final String WRD_ABSTRACT = "abstract";
    // ex. types
    public static final String WRD_TYPES = "types";
    public static final String WRD_NINCLUDE = "ninclude";
    public static final String WRD_NATIVE = "native";
    // ex. system
    public static final String WRD_SYSPROP = "sysprop";
    public static final String WRD_NFUN = "nfun";

    private static final Set<String> SKIPS = new HashSet<>();
    private static final Set<String> KEYWORDS = new HashSet<>();

    static {
        final Field[] fields = AliceParser.class.getDeclaredFields();
        for (final Field f : fields) {
            if (Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers())) {
                if (f.getType() == String.class) {
                    try {
                        KEYWORDS.add((String) f.get(null));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else if (f.getType().isPrimitive()) {
                    try {
                        KEYWORDS.add("" + f.getChar(null));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        SKIPS.add("run");
        SKIPS.add("from");
        SKIPS.add("to");
        SKIPS.add("head");
        SKIPS.add("while");
        SKIPS.add("for");
    }
}
