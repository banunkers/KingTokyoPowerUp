package card.evolutioncard.implemented.kong;

import java.util.ArrayList;

import card.evolutioncard.EvolEffect;
import game.Phase;
import monster.Monster;

/**
 * Iron Curtain: Permanent, Other Monsters who yield Tokyo lose 1 HEART.
 */
public class IronCurtainEffect extends EvolEffect {

	@Override
	public void trigger(Monster monster, Monster currMonster, Phase phase, ArrayList<Monster> monsters) {
		if (phase == Phase.YIELDING_TOKYO && !currMonster.equals(monster)) {
			currMonster.decHealth(1);
		}
	}

}