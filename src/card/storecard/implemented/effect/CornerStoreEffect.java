package card.storecard.implemented.effect;

import card.Effect;
import game.Phase;
import monster.Monster;

/**
 * Corner Stone: Discard, + 1[Star]
 */
public class CornerStoreEffect extends Effect {

	@Override
	public void checkTrigger(Monster monster, Phase phase, Monster attacker) {
		if (phase == Phase.DISCARDING) {
			trigger(monster, null);
		}
	}

	@Override
	protected void trigger(Monster monster, Monster attacker) {
		monster.incStars(1);
	}

}