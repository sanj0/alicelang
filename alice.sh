#!/bin/bash
if [[ $# = 0 ]]; then
  java -jar /Library/de.sanj0.alicelang/bin/alice.jar
else
#  if [[ $1 == *.alice || $1 == "compile" ]]; then
    java -jar /Library/de.sanj0.alicelang/bin/alice.jar "$@"
#  else
#    java -cp /Library/de.sanj0.alicelang/bin/alice.jar:. "$@"
#  fi
fi
