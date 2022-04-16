# Alice
A fully stack based, interpreted programming language with an additional table for named stack elements.

## Showcase
Before the dry stuff, let's take a look at some examples.

### hello, world!
Obviously, we have to start with a "Hello World" example:
```bash
"Hello World\n" # pushes the string "Hello World\n" onto the stack
P               # prints out the top most element from the stack,
                # removing it. lower-case p would not have removed it
# stack is now empty [ ]
```
### greet me!
Next, let's take a look at simple user input for a "greet me" program:
```bash
"What is your name? "P # closing " makes it possible to put the next token without white spaces
r # reads user input until new line and pushed it onto the stack
"Welcome, "P P  # prints the welcome string first, removing it
                # from the stack and then prints what
                # left on the stack, i.e. the user input.
```
### something silly to showcase more
```bash
"io.alice" include
"bool.alice" include
"What is your age? "
read-num    # convenience subprogram from io.alice for "r n"
            # (read and convert to number)
if (d 10 % not) {
    "You are "P 10 / P " decades old!"P
} fi
```

## The stack
The stack is a theoretically endless data structure (backed by an `ArrayList` in the Java implementation

## The table
You can put strings, numbers and subprograms onto a fully separate data structure called the `table`.
It is backed by a `HashMap` in Java and thus stores key-value pairs. You can put anything from the top
of the stack onto the table as follows:
```bash
# some code that puts something onto the stack #
:key
```
You can now push the value back onto the stack at any time by simply stating the key. If the corresponding value
is a subprogram, it is not put onto the stack but rather executed immediately.
