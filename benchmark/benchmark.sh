#!/bin/bash
echo while loop
time alice while.alice
echo 

echo for loop
time alice for.alice
echo

echo run loop
time alice run.alice
echo

echo parse stupidly long stuff
time alice parse.alice
echo

echo read and eval the former script
time alice eval.alice
echo
