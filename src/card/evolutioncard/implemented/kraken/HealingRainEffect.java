package card.evolutioncard.implemented.kraken;

import java.util.ArrayList;

import card.evolutioncard.EvolEffect;
import game.Phase;
import monster.Monster;

/**
 * Healing Rain: Temporary, Gain 2 health
 */
public class HealingRainEffect extends EvolEffect {

	@Override
	public void trigger(Monster monster, Monster currMonster, Phase phase, ArrayList<Monster> monsters) {
		monster.incHealth(2);
	}

}