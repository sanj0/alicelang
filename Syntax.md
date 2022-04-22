# The syntax of alice
(the specs are subject of constant change in the current state of development)

## 1 Pushing onto the stack
Three different kinds of elements can be pushed onto the stack by simply stating
them.

### 1.1 Numbers
In alice, there is one single type for numbers, which is backed by type
`double` in Java and follows the syntax below:

```regex
[+-]?[0-9]+\.?[0-9]*
```
Note that number literals cannot start with the decimal point as that denotes a struct member
access statement  
Example: `10 +13.5 0.125 -3`

### 1.2 Strings
Strings work as they do in Java, following the syntax below:

```regex
".*"
```
Example: `"hi" "hello, world" "1.37"`

### 1.3 Subprograms
Subprograms are alice code that is not executed immediately, but stored on the
stack for later execution (or capturing as a function). They are stated using
the following syntax:

```regex
(\(.*\))|\{.*\}
```
Example: `(1 2 3 4) {"hi"P} ((3 {"hello, world"P}) "welcome")`

## 2 Stack manipulation
Basic stack manipulation is very much inspired by forth:
```alice
d           # duplicates the topmost element
drop        # drops the topmost element
swap        # wsaps the two topmost elements
rot         # rotates out of three elements: a b c -> b c a
d2          # duplicates two elements: a b -> a b a b
clear       # clears the stack
NUMBER fold # folds NUMBER of elements into a substack:
            #   (which, in turn, is pushed onto the stack)
            #   1 2 ... n -> [1 2 ... n]
expand      # expands the substack on top of the stack:
            #   [a b c] -> a b c
```

## 3 The table
Besides the stack, there is another main data structure, the table.
In the java implementation, it is backed by a HashMap. Onto the table, anything
from the stack can be put onto it using a key:

```alice
"hi" :greeting
(greeting P)
    :greet
```
In the example above, the stack is empty after running the three lines, but the
table contains a STRING greeting->"hi" and a subprogram greet->(...). Retrieving
a value from the stack is done by stating the key. If the corresponding value is
subprogram, it is not put back onto the stack, but executed immediately.

Each program (file, function, if-body etc.) places a new, local table onto a
stack that os popped once the program is done. Since there is no difference
(both semantically and regarding syntax) between creating a new variable and
assigning an existing one

Each program (file, function, if-body etc.) places a new, local table onto a
stack that os popped once the program is done. In order to perform an assign (or
declaration) globally (meaning on the very last scope on the stack), `export` is
to be stated before:

```bash
export fun {
    "this is a global functtion available in all scopes!"P
}:my-function
```

Since there is no difference (both semantically and regarding syntax) between
creating a new variable and assigning an existing one, each assignment to a variable
out of scope has to be prefixed by stating `export`:

```bash

```

## 4 Functions
Using the syntax above, functions an be defined and invoked. Additionally, there
is a marker-function called `fun` which does nothing aside from clarifying and
pretty-fying code:

```alice
fun {
    greeting P
}:greet
```
The above example is semantically identical to the function definition under
[3 The table](#3-the-table).

## 5 Commands

## 6 Words

## 7 The sdk
