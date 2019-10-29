package card.evolutioncard.implemented.kong;

import java.util.ArrayList;

import card.evolutioncard.EvolEffect;
import game.Phase;
import monster.Monster;

/**
 * Iron Curtain: Permanent, Other Monsters who yield Tokyo lose 1 HEART.
 */
public class IronCurtainEffect extends EvolEffect {

	public IronCurtainEffect(ArrayList<Monster> monsters) {
		super(monsters);
	}

	@Override
	protected void trigger(Monster monster, Monster attacker, Phase phase) {
		if (phase == Phase.YIELDING_TOKYO && monster.getName() != "Kong" ) {
			monster.decHealth(1);
		}
	}

}