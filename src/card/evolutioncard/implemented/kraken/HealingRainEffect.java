package card.evolutioncard.implemented.kraken;

import java.util.ArrayList;

import card.evolutioncard.EvolEffect;
import game.Phase;
import monster.Monster;

/**
 * Healing Rain: Temporary, Gain 2 health
 */
public class HealingRainEffect extends EvolEffect {

	public HealingRainEffect(ArrayList<Monster> monsters) {
		super(monsters);
	}

	@Override
	protected void trigger(Monster monster, Monster attacker, Phase phase) {
		monster.incHealth(2);
	}

}