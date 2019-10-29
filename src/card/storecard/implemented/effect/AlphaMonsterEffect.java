package card.storecard.implemented.effect;

import card.Effect;
import game.Phase;
import monster.Monster;

/**
 * Alpha Monster: Gain 1[Star] when you attack
 */
public class AlphaMonsterEffect extends Effect {

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