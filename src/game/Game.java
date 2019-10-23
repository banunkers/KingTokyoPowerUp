package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import card.Deck;
import dice.Dice;
import dice.Util;
import monster.Monster;
import server.Server;

public class Game {
	private ArrayList<Monster> monsters; 
	private Util diceUtil = new Util();
	private RuleBook ruleBook;
	
	public Game(ArrayList<Monster> monsters) {
		this.monsters = monsters;
		this.ruleBook = new RuleBook(monsters);
		// Shuffle which player is which monster

		// TODO: Encapsulate shuffle in monster class to support testing ish
		Collections.shuffle(monsters);
		Deck deck = new Deck();
		
		// Shuffle the starting order
		Collections.shuffle(monsters);
		
		/*
		 * Game loop: pre: Award a monster in Tokyo 1 star 1. Roll 6 dice 2. Decide
		 * which dice to keep 3. Reroll remaining dice 4. Decide which dice to keep 5.
		 * Reroll remaining dice 6. Sum up totals 6a. Hearts = health (max 10 unless a
		 * cord increases it) 6b. 3 hearts = power-up 6c. 3 of a number = victory points
		 * 6d. claws = attack (if in Tokyo attack everyone, else attack monster in
		 * Tokyo) 6e. If you were outside, then the monster inside tokyo may decide to
		 * leave Tokyo 6f. energy = energy tokens 7. Decide to buy things for energy 7a.
		 * Play "DISCARD" cards immediately 8. Check victory conditions
		 */
		while (true) {
			for (int currMonID = 0; currMonID < monsters.size(); currMonID++) {
				Monster currMon = monsters.get(currMonID);
				
				// Skip if the monster is already dead
				if (currMon.currentHealth <= 0) {
					currMon.inTokyo = false;
					continue;
				}

				if (currMon.inTokyo) {
					currMon.stars += 1;
				}

				statusUpdate(currMon);

				// Begin the roll phase which involves two chances to reroll
				ArrayList<Dice> dice = diceUtil.roll(6);
				rollPhase(currMon, dice);
				rollPhase(currMon, dice);
				
				// 6. Sum up totals
				diceUtil.sort(dice);
				HashMap<Dice, Integer> result = new HashMap<Dice, Integer>();
				for (Dice unique : new HashSet<Dice>(dice)) {
					result.put(unique, Collections.frequency(dice, unique));
				}
				Server.sendMessage(currMon, "ROLLED:You rolled " + result + " Press [ENTER]\n");
				ruleBook.resolveDice(currMonID, result);
				// 7. Decide to buy things for energy
				String msg = "PURCHASE:Do you want to buy any of the cards from the store? (you have "
						+ currMon.energy + " energy) [#/-1]:" + deck + "\n";
				String answer = Server.sendMessage(currMon, msg);
				int buy = Integer.parseInt(answer);
				if (buy > 0 && (currMon.energy >= (deck.store[buy].cost
						- currMon.cardEffect("cardsCostLess")))) { // Alien Metabolism
					if (deck.store[buy].discard) {
						// 7a. Play "DISCARD" cards immediately
						currMon.stars += deck.store[buy].effect.stars;
					} else
						currMon.cards.add(deck.store[buy]);
					// Deduct the cost of the card from energy
					currMon.energy += -(deck.store[buy].cost - currMon.cardEffect("cardsCostLess")); // Alient
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
	
	public void statusUpdate(Monster currMon) {
		String status_update = "You are " + currMon.name + " and it is your turn. Here are the stats";
		for (int count = 0; count < 3; count++) {
			status_update += ":" + monsters.get(count).name
					+ (monsters.get(count).inTokyo ? " is in Tokyo " : " is not in Tokyo ");
			status_update += "with " + monsters.get(count).currentHealth + " health, "
					+ monsters.get(count).stars + " stars, ";
			status_update += monsters.get(count).energy + " energy, and owns the following cards:";
			status_update += monsters.get(count).cardsToString();
		}
		Server.sendMessage(currMon, status_update + "\n");
	}
	
	/**
	 * Displays the rolled dice and lets the player decide what to reroll
	 * @param currMon the monster whos turn it is
	 * @param dice	the rolled dice
	 * @return	an array list containing the rerolled dice
	 */
	public void rollPhase(Monster currMon, ArrayList<Dice> dice) {
		String rolledDice = "ROLLED:You rolled:\t[1]\t[2]\t[3]\t[4]\t[5]\t[6]:";
		for (int allDice = 0; allDice < dice.size(); allDice++) {
			rolledDice += "\t[" + dice.get(allDice) + "]";
		}
		// 2. Decide which dice to keep
		rolledDice += ":Choose which dice to reroll, separate with comma and in decending order (e.g. 5,4,1   0 to skip)\n";
		String[] reroll = Server.sendMessage(currMon, rolledDice).split(",");
		
		// 3. Reroll remaining dice
		if (Integer.parseInt(reroll[0]) != 0) {
			diceUtil.reroll(dice, reroll);
		}
	}
}