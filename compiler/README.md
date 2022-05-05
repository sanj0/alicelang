# The alice compiler
... compiles alice to Java code

The compiler takes a pre-parsed Program (list of Statements) and generates a
Java-Representation using rules as defined by simple .calices files that are
themselfes compiled to Java code. This way, the compiler is dynamic and
automatically adapts to changes in the language.

```
# this is a compilable alice statement
# it is parsed in a very simple way so only line comment like this are allowed

# This has to be the full class name, trailing -Statement is added automatically
target: Drop

# how to compile the statement
# simply inline the java code from the "execute" method:
compile-strategy: inline-source

# initialize an instance of the statement class ...
# compile-strategy: init
# ... with optional contructor args
# with-constructor: === return "final String args, final String fromAnyJava" ===

# compile-strategy: dynamic
# generator: ===// any valid java code that returns a string of java code
#            "System.exit(0);===
```
