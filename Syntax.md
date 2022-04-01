# The syntax of alice
(the specs are subject of constant change in the current state of development)

## Pushing onto the stack
Three different kinds of elements can be pushed onto the stack by simply stating
them:

```regex
[+-]?[0-9]*\.?[0-9]*
```
puts a number onto the stack. Example: `10 +13.5 .125 -3`

```regex
".*"
```
puts a string onto the stack. Example: `"hi" "hello, world" "1.37"`

```regex
(\(.*\))|\{.*\}
```
puts a subprogram onto the stack. Example: `(1 2 3 4) {"hi"P} (({"hello,
world"P}))`.

## Stack manipulation

