#!/bin/bash
mvn clean install
sudo mkdir -p /Library/de.sanj0.alicelang/sdk
sudo mkdir -p /Library/de.sanj0.alicelang/bin
sudo cp target/alicelang-*-jar-with-dependencies.jar /Library/de.sanj0.alicelang/bin/alice.jar
sudo cp sdk/* /Library/de.sanj0.alicelang/sdk/
touch /usr/local/bin/alice
cp alice.sh /usr/local/bin/alice
chmod +x /usr/local/bin/alice
echo alicelang installed in path as \"alice\"
