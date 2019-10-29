package card.storecard.implemented.effect;

import java.util.ArrayList;

import card.storecard.Effect;
import game.Phase;
import monster.Monster;

/**
 * Alpha Monster: Gain 1[Star] when you attack
 */
public class AlphaMonsterEffect extends Effect {

	public AlphaMonsterEffect(ArrayList<Monster> monsters) {
		super(monsters);
	}

	@Override
	public void checkTrigger(Monster monster, Phase phase, Monster attacker) {
		if (phase == Phase.ATTACKING) {
			trigger(monster, null);
		}
	}

	@Override
	protected void trigger(Monster monster, Monster attacker) {
		monster.incStars(1);
	}
}