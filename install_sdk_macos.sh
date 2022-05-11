#!/bin/zsh

SDK_TARGET="/Library/de.sanj0.alicelang/sdk"

# make sure necessary system dirs exist
# and empty them
if [[ -d $SDK_TARGET ]]; then
    sudo rm -R $SDK_TARGET/*
else
    sudo mkdir /Library/de.sanj0.alicelang/sdk/
fi

# copy the whole skd
sudo cp -R sdk/ $SDK_TARGET

# compile the native part of the sdk
sudo javac -cp $BIN_TARGET ${SDK_TARGET}/native/**/*.java
echo alicelang sdk installed!
