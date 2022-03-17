#!/bin/bash
if [ $# = 0 ]; then
    java -jar /Library/de.sanj0.alicelang/bin/alice.jar
else
    java -jar /Library/de.sanj0.alicelang/bin/alice.jar $@
fi
