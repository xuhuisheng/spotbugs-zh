#!/bin/sh

set -e

javac Main.java

java Main messages.xml
java Main spotbugs-messages_zh.xml

clear

diff dest/messages.xml dest/spotbugs-messages_zh.xml

