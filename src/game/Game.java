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
	
	public Game(ArrayList<Player> players) {
		this.players = players;
		// Shuffle the starting order
		Collections.shuffle(players);
		this.ruleBook = new RuleBook(players);
		Deck deck = new Deck();
		
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
				ArrayList<Dice> dice = diceUtil.roll(6);
				ruleBook.rollPhase(currPlayer, dice);
				
				// Sum up dice
				diceUtil.sort(dice);
				HashMap<Dice, Integer> result = new HashMap<Dice, Integer>();
				for (Dice unique : new HashSet<Dice>(dice)) {
					result.put(unique, Collections.frequency(dice, unique));
				}
				Server.sendMessage(currPlayer, "ROLLED:You rolled " + result + " Press [ENTER]\n");

				// Resolve the dice
				ruleBook.resolveDice(currPlayerID, result);
				
				// Decide to buy things for energy
				String msg = "PURCHASE:Do you want to buy any of the cards from the store? (you have "
						+ currMon.getEnergy() + " energy) [#/-1]:" + deck + "\n";
				String answer = Server.sendMessage(currPlayer, msg);
				int buy = Integer.parseInt(answer);
				if (buy > 0 && (currMon.getEnergy() >= (deck.store[buy].cost
						- currPlayer.cardEffect("cardsCostLess")))) { // Alien Metabolism
					if (deck.store[buy].discard) {
						// 7a. Play "DISCARD" cards immediately
						currMon.incStars(deck.store[buy].effect.stars);
					} else
						currPlayer.cards.add(deck.store[buy]);
					// Deduct the cost of the card from energy
					currMon.decEnergy(deck.store[buy].cost - currPlayer.cardEffect("cardsCostLess")); // Alient
																													// Metabolism
					// Draw a new card from the deck to replace the card that was bought
					deck.store[buy] = deck.deck.remove(0);
				}
				// 8. Check victory conditions
				if (ruleBook.endOfGame()) {
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
			status_update += players.get(count).cardsToString();
		}
		Server.sendMessage(player, status_update + "\n");
	}
}