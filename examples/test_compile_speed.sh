#!/bin/bash

if [[ $# != 1 ]]; then
    echo expecting one arg
fi

echo interpreter
time alice "${1}.alice"
echo 

echo compiling
time alice compile "${1}.alice"
echo

echo running compiled
time alice "${1}"
echo
