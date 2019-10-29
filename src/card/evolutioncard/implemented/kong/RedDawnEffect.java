package card.evolutioncard.implemented.kong;

import java.util.ArrayList;

import card.evolutioncard.EvolEffect;
import game.Phase;
import monster.Monster;

/**
 * Red Dawn: Temporary, All other monsters lose 2 health
 */
public class RedDawnEffect extends EvolEffect {

	public RedDawnEffect(ArrayList<Monster> monsters) {
		super(monsters);
	}

	@Override
	protected void trigger(Monster monster, Monster attacker, Phase phase) {
		for (Monster mon : monsters) {
			if (!mon.equals(monster))
				mon.decHealth(2);
		}
	}

}