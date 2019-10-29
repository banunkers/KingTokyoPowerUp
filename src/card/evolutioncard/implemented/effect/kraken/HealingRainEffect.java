package card.evolutioncard.implemented.effect.kraken;

import card.evolutioncard.Effect;
import game.Phase;
import monster.Monster;

/**
 * Healing Rain: Temporary, Gain 2 health
 */
public class HealingRainEffect extends Effect {

	@Override
	protected void trigger(Monster monster, Monster attacker, Phase phase) {
		monster.incHealth(2);
	}

}