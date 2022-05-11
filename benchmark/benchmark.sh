#!/bin/bash
SAMPLE_SIZE=5
{
    echo while loop
    multitime -n $SAMPLE_SIZE alice while.alice
    echo 

    echo for loop
    multitime -n $SAMPLE_SIZE alice for.alice
    echo

    echo run loop
    multitime -n $SAMPLE_SIZE alice run.alice
    echo

    echo parse 1 million lines
    multitime -n $SAMPLE_SIZE alice -r false -std false parse.alice
    echo

    echo read and eval the 1 million lines
    multitime -n $SAMPLE_SIZE alice -std false eval.alice
    echo
} 2>&1 >/dev/null | tee -a "logs/$(date +%F-%T).txt"
