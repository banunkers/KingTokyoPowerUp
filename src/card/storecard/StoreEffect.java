package card.storecard;

import java.util.ArrayList;

import game.Phase;
import monster.Monster;

public abstract class StoreEffect {
	protected boolean done = false;	// Used to make effects non-repeatable
	protected ArrayList<Monster> monsters;

	public StoreEffect(ArrayList<Monster> monsters) {
		this.monsters = monsters;
	}

	/**
	 * Checks if the effect should be triggered in the current phase of the game
	 * @param monster the monsers whos card is being checked
	 * @param gamePhase the game phase
	 * @param attacker optional parameter of attacking monster
	 */
	public abstract void checkTrigger(Monster monster, Phase phase, Monster attacker);

	/**
	 * Triggers and applies the effect of the card
	 * @param monster the monster whos card is being triggered
	 * @param attacker optional parameter of attacking monster
	 */
	protected abstract void trigger(Monster monster, Monster attacker);
}