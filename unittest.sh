#!/usr/bin/env bash
echo "Running unittests..."
cd bin
java -cp .:../lib/org.junit4-4.3.1.jar org.junit.runner.JUnitCore tests.game.evolutioncard.AlienoidTests tests.game.evolutioncard.GigazaurTests tests.game.evolutioncard.KongTests tests.game.evolutioncard.KrakenTests tests.game.playing.GameLogicTests tests.game.setup.SetupTests tests.game.storecard.StoreCardTests