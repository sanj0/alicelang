#!/bin/zsh

export BIN_TARGET="/Library/de.sanj0.alicelang/bin/alice.jar"

# compile the language
mvn clean install

# copy the executable
sudo cp target/alicelang-*-jar-with-dependencies.jar $BIN_TARGET

./install_sdk_macos.sh

# copy the start script into local path
cp alice.sh /usr/local/bin/alice

# make it executable
chmod +x /usr/local/bin/alice

# done!
echo alicelang installed in path as \"alice\"
