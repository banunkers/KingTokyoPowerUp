package card.evolutioncard.implemented.kong;

import java.util.ArrayList;

import card.evolutioncard.EvolEffect;
import game.Phase;
import monster.Monster;

/**
 * Red Dawn: Temporary, All other monsters lose 2 health
 */
public class RedDawnEffect extends EvolEffect {

	@Override
	public void trigger(Monster monster, Monster currMonster, Phase phase, ArrayList<Monster> monsters) {
		for (Monster mon : monsters) {
			if (!mon.equals(monster))
				mon.decHealth(2);
		}
	}

}