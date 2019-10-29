package card.evolutioncard;

import java.util.ArrayList;

import game.Phase;
import monster.Monster;

public abstract class EvolEffect {
	protected boolean done = false;	// Used to make effects non-repeatable
	protected ArrayList<Monster> monsters;

	public EvolEffect(ArrayList<Monster> monsters) {
		this.monsters = monsters;
	}

	/**
	 * Checks if the evolution card should trigger when the game phase changes.
	 * If the conditions are met the effect will trigger
	 * @param monster the monster whos card is being checked
	 * @param currMonster the monster who might have triggered the card
	 * @param phase the current game phase
	 */
	public abstract void trigger(Monster monster, Monster currMonster, Phase phase);
}