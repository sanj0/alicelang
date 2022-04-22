#!/bin/bash
echo while loop
multitime -n 10 alice while.alice
echo 

echo for loop
multitime -n 10 alice for.alice
echo

echo run loop
multitime -n 10 alice run.alice
echo

echo parse stupidly long stuff
multitime -n 10 alice parse.alice
echo

echo read and eval the former script
multitime -n 10 alice eval.alice
echo
