package game;

import java.util.ArrayList;
import java.util.HashMap;

import dice.Dice;
import monster.Monster;
import server.Server;

public class RuleBook {
	private static final Dice HEART = new Dice(Dice.HEART);
	private static final Dice CLAW = new Dice(Dice.CLAWS);
	private static final Dice ENERGY = new Dice(Dice.ENERGY);
	private static final int VICTORY_STARS = 20;
	private ArrayList<Monster> monsters;

	public RuleBook(ArrayList<Monster> monsters) {
		this.monsters = monsters;
	}

	/**
	 * Resolves the result of the dice rolled by a player and applies it to the monsters
	 * @param currMonID the ID of monster who rolled the dice
	 * @param result the rolled dice
	 */
	public void resolveDice(int currMonID, HashMap<Dice, Integer> result) {
		Monster currMon = monsters.get(currMonID);
		// 6a. Hearts = health (max 10 unless a cord increases it)
		// TODO: make monster incHealth(int amount)
		if (result.containsKey(HEART)) { 
			if (!currMon.inTokyo) {	
				// +1 currentHealth per heart, up to max health (If monster not in tokyo)
				if (currMon.currentHealth + result.get(HEART).intValue() >= currMon.maxHealth) {
					currMon.currentHealth = currMon.maxHealth;
				} else {
					currMon.currentHealth += result.get(HEART).intValue();
				}
			}
			// 6b. 3 hearts = power-up
			if (result.get(HEART).intValue() >= 3) {
				// Deal a power-up card to the currMon
				if (currMon.name.equals("Kong")) {
					// Todo: Add support for more cards.
					// Current support is only for the Red Dawn card
					// Add support for keeping it secret until played
					Server.sendMessage(currMon, "POWERUP:Deal 2 damage to all others\n");
					for (int mon = 0; mon < monsters.size(); mon++) {
						if (mon != currMonID) {
							monsters.get(mon).currentHealth += -2;
						}
					}
				}
				if (currMon.name.equals("Gigazaur")) {
					// Todo: Add support for more cards.
					// Current support is only for the Radioactive Waste
					// Add support for keeping it secret until played
					Server.sendMessage(currMon, "POWERUP:Receive 2 energy and 1 health\n");
					currMon.energy += 2;
					if (currMon.currentHealth + 1 >= currMon.maxHealth) {
						currMon.currentHealth = currMon.maxHealth;
					} else {
						currMon.currentHealth += 1;
					}
				}
				if (currMon.name.equals("Alienoid")) {
					// Todo: Add support for more cards.
					// Current support is only for the Alien Scourge
					// Add support for keeping it secret until played
					Server.sendMessage(currMon, "POWERUP:Receive 2 stars\n");
					currMon.stars += 2;
				}
			}
		}
		// 6c. 3 of a number = victory points
		for (int num = 1; num < 4; num++) {
			if (result.containsKey(new Dice(num)))
				if (result.get(new Dice(num)).intValue() >= 3)
					currMon.stars += num + (result.get(new Dice(num)).intValue() - 3);
		}
		// 6d. claws = attack (if in Tokyo attack everyone, else attack monster in
		// Tokyo)
		if (result.containsKey(CLAW)) {
			currMon.stars += currMon.cardEffect("starsWhenAttacking"); // Alpha Monster
			if (currMon.inTokyo) {
				for (int mon = 0; mon < monsters.size(); mon++) {
					int moreDamage = currMon.cardEffect("moreDamage"); // Acid Attack
					int totalDamage = result.get(CLAW).intValue() + moreDamage;
					if (mon != currMonID && totalDamage > monsters.get(mon).cardEffect("armor")) { // Armor Plating
						monsters.get(mon).currentHealth += -totalDamage;
					}
				}
			} else {
				boolean monsterInTokyo = false;
				for (int mon = 0; mon < monsters.size(); mon++) {
					if (monsters.get(mon).inTokyo) {
						monsterInTokyo = true;
						int moreDamage = currMon.cardEffect("moreDamage"); // Acid Attack
						int totalDamage = result.get(CLAW).intValue() + moreDamage;
						if (totalDamage > monsters.get(mon).cardEffect("armor")) // Armor Plating
							monsters.get(mon).currentHealth += -totalDamage;
						// 6e. If you were outside, then the monster inside tokyo may decide to leave
						// Tokyo
						String answer = Server.sendMessage(monsters.get(mon), "ATTACKED:You have " + monsters.get(mon).currentHealth
								+ " health left. Do you wish to leave Tokyo? [YES/NO]\n");
						if (answer.equalsIgnoreCase("YES")) {
							monsters.get(mon).inTokyo = false;
							monsterInTokyo = false;
						}
					}
				}
				if (!monsterInTokyo) {
					currMon.inTokyo = true;
					currMon.stars += 1;
				}
			}
		}
		// 6f. energy = energy tokens
		if (result.containsKey(ENERGY))
			currMon.energy += result.get(ENERGY).intValue();
	}

	/**
	 * Determines if the game has ended by checking the win conditions
	 * @return if the game has ended
	 */
	public boolean endOfGame() {
		int alive = 0;
		String aliveMonster = "";
		for (int mon = 0; mon < monsters.size(); mon++) {
			if (monsters.get(mon).stars >= VICTORY_STARS) {
				for (int i = 0; i < monsters.size(); i++) {
					Server.sendMessage(monsters.get(i), "Victory: " + monsters.get(mon).name + " has won by stars\n");
				}
				return true;
			}
			if (monsters.get(mon).currentHealth > 0) {
				alive++;
				aliveMonster = monsters.get(mon).name;
			}
		}
		if (alive == 1) {
			for (int i = 0; i < monsters.size(); i++) {
				Server.sendMessage(monsters.get(i), "Victory: " + aliveMonster + " has won by being the only one alive\n");
			}
			return true;
		}
		return false;
	}
}