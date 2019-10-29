package game;

import java.util.ArrayList;
import java.util.HashMap;

import card.Deck;
import card.evolutioncard.EvolCard;
import dice.Dice;
import dice.Util;
import monster.Monster;
import player.Player;
import server.Server;

/**
 * The RuleBook class performs game logic according to the rules specified in this class
 * e.g. determines when the games is over and what specific dice does
 */
public class RuleBook {
	private static final Dice HEART = new Dice(Dice.HEART);
	private static final Dice CLAW = new Dice(Dice.CLAWS);
	private static final Dice ENERGY = new Dice(Dice.ENERGY);
	private static final int VICTORY_STARS = 20;

	private Util diceUtil = new Util();
	private ArrayList<Monster> monsters;
	private ArrayList<Player> players;

	public RuleBook(ArrayList<Player> players, ArrayList<Monster> monsters) {
		this.monsters = monsters;
		this.players = players;
	}

	/**
	 * Handles the rolling phase of the game for each player.
	 * Lets the player choose if they want to reroll
	 * @param player the player whos turn it is
	 * @param dice the six rolled dice
	 * @return	an array list containing the rerolled dice
	 */
	public void rollPhase(Player player, ArrayList<Dice> dice, GamePhase gamePhase) {
		String rolledDice = "ROLLED: You rolled:\t[1]\t[2]\t[3]\t[4]\t[5]\t[6]:";
		for (int allDice = 0; allDice < dice.size(); allDice++) {
			rolledDice += "\t[" + dice.get(allDice) + "]";
		}
		// Decide which dice to reroll
		rolledDice += ": Choose which dice to reroll, separate with comma and in decending order (e.g. 5,4,1   0 to skip)\n";
		String[] reroll = Server.sendMessage(player, rolledDice).split(",");
		
		// Reroll the chosen dice
		if (Integer.parseInt(reroll[0]) != 0) {
			diceUtil.reroll(dice, reroll);
		}
	}

	/**
	 * Resolves the result of the dice rolled by a player and applies it to the monsters
	 * @param currMonID the ID of monster who rolled the dice
	 * @param result the rolled dice
	 */
	public void resolveDice(int currPlayerID, HashMap<Dice, Integer> result, GamePhase gamePhase) {
		Player currPlayer = players.get(currPlayerID);
		Monster currMon = currPlayer.getMonster();
		
		if (result.containsKey(HEART)) { 
			if (!currMon.isInTokyo()) {	
				// +1 currentHealth per heart, up to max health
				currMon.incHealth(result.get(HEART).intValue());
			}
			// 3 hearts = power-up
			if (result.get(HEART).intValue() >= 3) {
				EvolCard activatedEvol = currMon.activateEvolCard();
				if (activatedEvol != null) {	// Make the player aware that an evolution card was activated
					Server.sendMessage(currPlayer, "POWERUP:" + activatedEvol.toString() + "\n");
				}
			}
		}
		// 3 or more of a number = victory points
		for (int num = 1; num < 4; num++) {
			if (result.containsKey(new Dice(num))) {
				int numDice = result.get(new Dice(num)).intValue();
				if (numDice >= 3) {
					currMon.incStars(num + (numDice - 3));
				}
			}
		}	
		// claws = attack (if in Tokyo attack everyone, else attack monster in Tokyo)
		if (result.containsKey(CLAW)) {
			gamePhase.setPhase(Phase.ATTACKING, currMon, null);	// Trigger any attacking effects
			currMon.setTotalDamage(result.get(CLAW).intValue());
			if (currMon.isInTokyo()) {
				for (int mon = 0; mon < monsters.size(); mon++) {
					if (monsters.get(mon).equals(currMon)) {
						gamePhase.setPhase(Phase.TAKING_DAMAGE, monsters.get(mon), currMon); // Trigger defensive effects of attacked monster
						monsters.get(mon).decHealth(currMon.getTotalDamage());
					}
				}
			} else {
				boolean monsterInTokyo = false;
				for (int mon = 0; mon < monsters.size(); mon++) {
					if (monsters.get(mon).isInTokyo()) {
						monsterInTokyo = true;
						gamePhase.setPhase(Phase.TAKING_DAMAGE, monsters.get(mon), currMon);
						monsters.get(mon).decHealth(currMon.getTotalDamage());

						// Promt the attacked user if they want to yield Tokyo to the attacker
						String answer = Server.sendMessage(players.get(mon), "ATTACKED: You have " + monsters.get(mon).getHealth()
								+ " health left. Do you wish to leave Tokyo? [YES/NO]\n");
						if (answer.equalsIgnoreCase("YES")) {
							monsters.get(mon).setInTokyo(gamePhase, false);
							monsterInTokyo = false;
						}
					}
				}
				if (!monsterInTokyo) {
					currMon.setInTokyo(gamePhase, true);
					currMon.incStars(1);
				}
			}
		} else {
			// Trigger cards which do not require any rolled CLAWS to still take effect i.e. AcidAttack
			gamePhase.setPhase(Phase.ATTACKING_NO_CLAW, currMon, null);
		}
		
		// 6f. energy = energy tokens
		if (result.containsKey(ENERGY))
			currMon.incEnergy(result.get(ENERGY).intValue());
	}

	public void buy(Player player, Deck deck, GamePhase gamePhase) {
		Monster monster = player.getMonster();
		String msg = "PURCHASE:Do you want to buy any of the cards from the store? (you have "
		+ monster.getEnergy() + " energy) [#/-1]:" + deck + "\n";
		String answer = Server.sendMessage(player, msg);
		int buy = Integer.parseInt(answer);
		if ((buy > 0) && (monster.getEnergy() >= (deck.store[buy].getCost()
				- monster.getCostReduction()))) {
			if (deck.store[buy].isDiscard()) {
				gamePhase.setPhase(Phase.DISCARDING, monster, null);
			} else {
				monster.addStoreCard(deck.store[buy]);
			}
			// Deduct the cost of the card from energy
			monster.decEnergy(deck.store[buy].getCost() - monster.getCostReduction());
			// Draw a new card from the deck to replace the card that was bought
			deck.store[buy] = deck.deck.remove(0);
		}
	}

	/**
	 * Determines if the game has ended by checking the win conditions
	 * @return if the game has ended
	 */
	public boolean gameEnded() {
		int alive = 0;
		String aliveMonster = "";
		for (int player = 0; player < players.size(); player++) {
			Monster mon = players.get(player).getMonster();
			if (mon.getStars() >= VICTORY_STARS) {
				for (int i = 0; i < players.size(); i++) {
					Server.sendMessage(players.get(i), "Victory: " + mon.getName() + " has won by stars\n");
				}
				return true;
			}
			if (mon.isAlive()) {
				alive++;
				aliveMonster = mon.getName();
			}
		}
		if (alive == 1) {
			for (int i = 0; i < players.size(); i++) {
				Server.sendMessage(players.get(i), "Victory: " + aliveMonster + " has won by being the only one alive\n");
			}
			return true;
		}
		return false;
	}
}