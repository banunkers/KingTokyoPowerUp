package game;

import java.util.ArrayList;
import java.util.HashMap;

import card.Deck;
import card.evolutioncard.EvolCard;
import card.storecard.StoreCard;
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

	/**
	 * Handles the start of a players round.
	 * Checks if they are still alive and if they are inside tokyo they will be rewared
	 * @param monster the monster whose turn it is
	 * @return if the monster is still alive
	 */
	public boolean startingPhase(Monster monster, GamePhase gamePhase, ArrayList<Monster> monsters) {
		if (monster.isAlive()) {
			if (monster.isInTokyo()) {
				monster.incStars(1);
			}
		} else {
			monster.setInTokyo(gamePhase, false, monsters);
		}
		return monster.isAlive();
	}

	/**
	 * Handles the rolling phase of the game for each player.
	 * Lets the player choose if they want to reroll
	 * @param player the player whose turn it is
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
			diceUtil.selectReroll(dice, reroll);
			diceUtil.replaceRerolled(dice);
		}
	}

	/**
	 * Resolves the result of the dice rolled by a player and applies it to the monsters
	 * @param currPlayer the player whose dice is getting resolved
	 * @param result the rolled dice
	 * @param gamePhase the game phase
	 * @param monsters the monsters in the game
	 * @param players the players in the game
	 */
	public void resolveDice(Player currPlayer, HashMap<Dice, Integer> result, GamePhase gamePhase, ArrayList<Monster> monsters, ArrayList<Player> players) {
		Monster currMon = currPlayer.getMonster();
		
		// Resolve any hearts rolled and make the player aware if an evolution card was acivated
		// by rolling at least 3 HEARTS
		EvolCard activatedEvol = resolveHearts(currMon, result, monsters);
		if (activatedEvol != null) {
			Server.sendMessage(currPlayer, "POWERUP:" + activatedEvol.toString() + "\n");
		}

		// 3 or more of a number = victory points
		resolveNums(currMon, result);

		// Check for claws. If the monster attacks a monster in Tokyo ask if they want to yield Tokyo
		if (result.containsKey(CLAW)) {
			Player attackedInTokyo = resolveClaw(currMon, result, gamePhase, monsters, players);
			if (attackedInTokyo != null) {
				// Promt the attacked user if they want to yield Tokyo to the attacker
				String answer = Server.sendMessage(attackedInTokyo, "ATTACKED: You have " + attackedInTokyo.getMonster().getHealth()
						+ " health left. Do you wish to leave Tokyo? [YES/NO]\n");
				if (answer.equalsIgnoreCase("YES")) {
					attackedInTokyo.getMonster().setInTokyo(gamePhase, false, monsters);
					currMon.setInTokyo(gamePhase, true, monsters);
					currMon.incStars(1);
				}
			}
		} else {
			// Trigger cards which do not require any rolled CLAWS to still take effect i.e. AcidAttack
			gamePhase.setPhase(Phase.ATTACKING_NO_CLAW, currMon, null, monsters);	
		}
		
		// Check for energy dice
		resolveEnergy(currMon, result);
	}

	/**
	 * Checks the players dice of any rolled hearts and applies the effects of them
	 * @param currMon the monster of the player who rolled the dice
	 * @param result the rolled dice
	 * @param monsters the monsters in the game
	 * @return the activated evolution card from rolling 3 HEARTS, if any
	 */
	private EvolCard resolveHearts(Monster currMon, HashMap<Dice, Integer> result, ArrayList<Monster> monsters) {
		if (result.containsKey(HEART)) { 
			if (!currMon.isInTokyo()) {	
				// +1 currentHealth per heart, up to max health
				currMon.incHealth(result.get(HEART).intValue());
			}
			// 3 hearts = power-up
			if (result.get(HEART).intValue() >= 3) {
				return currMon.activateEvolCard(monsters);
			}
			return null;
		}
		return null;
	}

	/**
	 * Resolves all of the number dice the player rolled
	 * @param currMon the players monster
	 * @param result the rolled dice
	 */
	private void resolveNums(Monster currMon, HashMap<Dice, Integer> result) {
		for (int num = 1; num < 4; num++) {
			if (result.containsKey(new Dice(num))) {
				int numDice = result.get(new Dice(num)).intValue();
				if (numDice >= 3) {
					currMon.incStars(num + (numDice - 3));
				}
			}
		}	
	}

	/**
	 * Resolves all of the energy dice the player rolled
	 * @param currMon the players monster
	 * @param result the rolled dice
	 */
	private void resolveEnergy(Monster currMon, HashMap<Dice, Integer> result) {
		if (result.containsKey(ENERGY))
			currMon.incEnergy(result.get(ENERGY).intValue());
	}

	/**
	 * Resolves any claw dice rolled by the player
	 * @param currMon the players monster
	 * @param result the rolled dice
	 * @param gamePhase the game phase
	 * @param monsters the monsters in the game
	 * @param players the players in the game
	 * @return the player, if any, in Tokyo who got attacked by the monster
	 */
	private Player resolveClaw(Monster currMon, HashMap<Dice, Integer> result, GamePhase gamePhase, ArrayList<Monster> monsters, ArrayList<Player> players) {
		gamePhase.setPhase(Phase.ATTACKING, currMon, null, monsters);	// Trigger any attacking effects
		currMon.setTotalDamage(result.get(CLAW).intValue());
		if (currMon.isInTokyo()) {
			for (int mon = 0; mon < monsters.size(); mon++) {
				if (!monsters.get(mon).equals(currMon)) {
					gamePhase.setPhase(Phase.TAKING_DAMAGE, monsters.get(mon), currMon, monsters); // Trigger defensive effects of attacked monster
					monsters.get(mon).decHealth(currMon.getTotalDamage());
				}
			}
			return null;
		} else {
			// boolean monsterInTokyo = false;
			for (int mon = 0; mon < monsters.size(); mon++) {
				if (monsters.get(mon).isInTokyo()) {
					// monsterInTokyo = true;
					gamePhase.setPhase(Phase.TAKING_DAMAGE, monsters.get(mon), currMon, monsters);
					monsters.get(mon).decHealth(currMon.getTotalDamage());
					return players.get(mon); // The attacked player
				}
			}
			// No monster in Tokyo so move in and gain 1 star
			currMon.setInTokyo(gamePhase, true, monsters);
			currMon.incStars(1);
			return null;
		}
	}

	/**
	 * Buys a card from the store if the monster has enough energy
	 * @param monster the monster whose player is buying the card
	 * @param cardIndex the index, in the store, of the card that is being bought
	 * @param deck the games deck
	 * @param gamePhase the game phase
	 * @param monsters the monsters in the game
	 * @return if a car was bought or not
	 */
	public boolean buy(Monster monster, int cardIndex, Deck deck, GamePhase gamePhase, ArrayList<Monster> monsters) {
		ArrayList<StoreCard> store = deck.getStore();
		if ((cardIndex >= 0 && cardIndex < 3) && (monster.getEnergy() >= (store.get(cardIndex).getCost()
				- monster.getCostReduction()))) {
			StoreCard boughtCard = deck.buyFromStore(cardIndex);
			if (boughtCard.isDiscard()) {
				gamePhase.setPhase(Phase.DISCARDING, monster, null, monsters);
			} else {
				monster.addStoreCard(boughtCard);
			}
			// Deduct the cost of the card from energy
			monster.decEnergy(boughtCard.getCost() - monster.getCostReduction());
			return true;
		} else if (monster.getEnergy() >= 2) { // Can afford reseting store
			deck.resetStore();
			monster.decEnergy(2);
		}
		return false;
	}

	/**
	 * Determines if the game has ended by checking the win conditions
	 * @param players the players in the game
	 * @return if a player won the game
	 */
	public boolean gameEnded(ArrayList<Player> players) {
		Player victorByStars = victoryByStars(players);
		if (victorByStars != null) {
			for (int player = 0; player < players.size(); player++) {
				Server.sendMessage(players.get(player), "Victory: " + victorByStars.getMonster().getName() + " has won by stars\n");	
			}
			return true;
		}

		Player victorBySurviving = victoryBySurviving(players);
		if (victorBySurviving != null) {
			for (int player = 0; player < players.size(); player++) {
				Server.sendMessage(players.get(player), "Victory: " + victorBySurviving.getMonster().getName() + " has won by being the only one alive\n");
			}
			return true;
		}
		return false;
	}

	/**
	 * Determines if a player has won by aquiring the needed stars for a victory
	 * @param players the players in the game
	 * @return the player, if any, who won the game
	 */
	private Player victoryByStars(ArrayList<Player> players) {
		for (int player = 0; player < players.size(); player++) {
			Monster mon = players.get(player).getMonster();
			if (mon.getStars() >= VICTORY_STARS) {
				return players.get(player);
			}
		}
		return null;
	}

	/**
	 * Determines if a player has won by being the sole survivor
	 * @param players the players in the game
	 * @return the player, if any, who survived
	 */
	private Player victoryBySurviving(ArrayList<Player> players) {
		int alive = 0;
		Player alivePlayer = null;
		for (int player = 0; player < players.size(); player++) {
			Monster mon = players.get(player).getMonster();
			if (mon.isAlive()) {
				alive++;
				alivePlayer = players.get(player);
			}
		}
		if (alive == 1) {
			return alivePlayer;
		}
		return null;
	}
}