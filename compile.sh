#!/usr/bin/env bash
mkdir -p bin
echo "Compiling code..."
javac -d ./bin -cp ./src ./src/card/*.java ./src/client/*.java ./src/dice/*.java ./src/game/*.java ./src/monster/*.java ./src/player/*.java ./src/server/*.java 

echo "Compiling tests..."
javac -d ./bin -cp ./lib/org.junit4-4.3.1.jar:./src ./src/tests/game/evolutioncard/*.java ./src/tests/game/playing/*.java ./src/tests/game/setup/*.java ./src/tests/game/setup/*.java ./src/tests/game/storecard/*.java