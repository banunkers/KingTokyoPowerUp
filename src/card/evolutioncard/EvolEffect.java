package card.evolutioncard;

import java.util.ArrayList;

import game.Phase;
import monster.Monster;

public abstract class EvolEffect {
	protected boolean done = false;	// Used to make effects non-repeatable

	/**
	 * Checks if the evolution card should trigger when the game phase changes.
	 * If the conditions are met the effect will trigger
	 * @param monster the monster whos card is being checked
	 * @param currMonster the monster who might have triggered the card
	 * @param phase the current game phase
	 * @param monsters the monsters in the game
	 */
	public abstract void trigger(Monster monster, Monster currMonster, Phase phase, ArrayList<Monster> monsters);
}