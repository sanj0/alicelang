" Vim syntax file
" Language: alice
" Maintainer: Malte Dostal

if exists("b:current_syntax")
    finish
endif

syn keyword alice_word !str !num while do run from to nur fi if else fun var const swap clear drop over rot eq lt gt and or ln length include exists type exit ssize random charat readf time get break continue return eval writef readf export exp clob glob head poll peek

syn keyword alice_command f t e i p P r d

set iskeyword+=-

syn match alice_num '\v\c<\d%(\d|_*\d)*L=>'
syn match alice_float '\v\c<\d%(\d|_*\d)*%(E[+-]=\d%(\d|_*\d)*[FD]=|[FD])>'
syn match alice_float '\v\c<\d%(\d|_*\d)*\.%(\d%(\d|_*\d)*)=%(E[+-]=\d%(\d|_*\d)*)=[FD]='
syn match alice_float '\v\c\.\d%(\d|_*\d)*%(E[+-]=\d%(\d|_*\d)*)=[FD]='
syn region alice_string start='"' end='"' skip="\\\""

syn region alice_subprogram start='(' end=')' transparent
syn region alice_subprogram start='{' end='}' transparent
syn match alice_identifier ':.+'

syn region alice_comment start=/#/ end=/#/ end=/$/

hi def link alice_word          Keyword
hi def link alice_command       Keyword
hi def link alice_string        String
hi def link alice_num           Number
hi def link alice_float         Number
hi def link alice_subprogram    Function
hi def link alice_identifier    Constant
hi def link alice_comment       Comment
