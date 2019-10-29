package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import card.Deck;
import dice.Dice;
import dice.Util;
import monster.Monster;
import player.Player;
import server.Server;

public class Game {
	private ArrayList<Player> players;
	private Util diceUtil = new Util();
	private RuleBook ruleBook;
	private ArrayList<Dice> dice;

	public Game(ArrayList<Player> players) {
		this.players = players;
		// Shuffle the starting order
		Collections.shuffle(players);
		this.ruleBook = new RuleBook(players);
		Deck deck = new Deck();
		GamePhase gamePhase = new GamePhase();
		
		// Game loop
		while (true) {
			for (int currPlayerID = 0; currPlayerID < players.size(); currPlayerID++) {
				Player currPlayer = players.get(currPlayerID);
				Monster currMon = currPlayer.getMonster();
				
				// Skip if the monster is already dead
				if (!currMon.isAlive()) {
					currMon.setInTokyo(false);
					continue;
				}

				if (currMon.isInTokyo()) {
					currMon.incStars(1);
				}
				
				// DIsplay the sate of the game
				statusUpdate(currPlayer);

				// Begin the roll phase which involves two chances to reroll
				dice = diceUtil.roll(6);
				ruleBook.rollPhase(currPlayer, dice, gamePhase);
				gamePhase.setPhase(Phase.ROLLING, currMon, null);
				ruleBook.rollPhase(currPlayer, dice, gamePhase);
				gamePhase.setPhase(Phase.ROLLING, currMon, null);
				
				// Sum up dice
				diceUtil.sort(dice);
				HashMap<Dice, Integer> result = new HashMap<Dice, Integer>();
				for (Dice unique : new HashSet<Dice>(dice)) {
					result.put(unique, Collections.frequency(dice, unique));
				}
				Server.sendMessage(currPlayer, "ROLLED:You rolled " + result + " Press [ENTER]\n");

				// Resolve the dice
				ruleBook.resolveDice(currPlayerID, result, gamePhase);
				gamePhase.setPhase(Phase.RESOLVING, currMon, null);

				// Decide to buy things for energy
				gamePhase.setPhase(Phase.BUYING, currMon, null);
				ruleBook.buy(currPlayer, deck, gamePhase);

				// 8. Check victory conditions
				if (ruleBook.gameEnded()) {
					System.exit(0);
				}
			}
		}
	}
	
	private void statusUpdate(Player player) {
		String status_update = "You are " + player.getMonster().getName() + " and it is your turn. Here are the stats";
		for (int count = 0; count < players.size(); count++) {
			Monster mon = players.get(count).getMonster();
			status_update += ":" + mon.getName()
					+ (mon.isInTokyo() ? " is in Tokyo " : " is not in Tokyo ");
			status_update += "with " + mon.getHealth() + " health, "
					+ mon.getStars() + " stars, ";
			status_update += mon.getEnergy() + " energy, and owns the following cards:";
			status_update += mon.cardsToString();
		}
		Server.sendMessage(player, status_update + "\n");
	}
}