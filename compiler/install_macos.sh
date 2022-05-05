#!/bin/bash
mvn clean install
mkdir -p ~/Library/de.sanj0.alicelang/bin
sudo cp target/alicecomp-*-jar-with-dependencies.jar /Library/de.sanj0.alicelang/bin/alicec.jar
sudo cp alicec.sh /usr/local/bin/alicec
chmod +x /usr/local/bin/alicec
echo alice compiler installed in path as \"alicec\"
