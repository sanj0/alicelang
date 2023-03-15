#!/bin/bash
SAMPLE_SIZE=3
LOG_NAME="logs/$(date +%F-%T).txt"
for f in *.alice;
do
    {
        echo $f
        multitime -n $SAMPLE_SIZE alice $f 2>&1 >/dev/null | sed -n 4p | awk '{print $2;}'
        echo
    } | tee -a $LOG_NAME
done;
