package card.evolutioncard;

import java.util.ArrayList;

import game.Phase;
import monster.Monster;

public abstract class Effect {
	protected boolean done = false;	// Used to make effects non-repeatable
	protected ArrayList<Monster> monsters;

	public Effect(ArrayList<Monster> monsters) {
		this.monsters = monsters;
	}

	/**
	 * Triggers and applies the effect of the card
	 * @param monster the monster whos card is being triggered
	 * @param attacker optional parameter of attacking monster
	 * @param phase the current game phase
	 */
	protected abstract void trigger(Monster monster, Monster attacker, Phase phase);
}