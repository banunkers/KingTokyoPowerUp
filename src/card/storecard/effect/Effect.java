package card.storecard.effect;

import monster.Monster;
import game.Phase;

public abstract class Effect {
	protected boolean done = false;	// Used to make effects non-repeatable

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