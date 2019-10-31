package card.storecard;

import java.util.ArrayList;

import game.Phase;
import monster.Monster;

public abstract class StoreEffect {
	protected boolean done = false;	// Used to make effects non-repeatable

	/**
	 * Checks if the effect should be triggered in the current phase of the game
	 * @param monster the monsers whos card is being checked
	 * @param gamePhase the game phase
	 * @param attacker optional parameter of attacking monster
	 * @param monsters the monsters in the game
	 */
	public abstract void checkTrigger(Monster monster, Phase phase, Monster attacker, ArrayList<Monster> monsters);

	/**
	 * Triggers and applies the effect of the card
	 * @param monster the monster whos card is being triggered
	 * @param attacker optional parameter of attacking monster
	 * @param monsters the monsters in the game
	 */
	protected abstract void trigger(Monster monster, Monster attacker, ArrayList<Monster> monsters);
}