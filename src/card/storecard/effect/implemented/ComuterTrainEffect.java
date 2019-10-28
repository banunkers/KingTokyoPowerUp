package card.storecard.effect.implemented;

import card.storecard.effect.Effect;
import game.Phase;
import monster.Monster;

/**
 * Comuter Train: Discard,  + 2[Star]
 */
public class ComuterTrainEffect extends Effect {

	@Override
	public void checkTrigger(Monster monster, Phase phase, Monster attacker) {
		if (phase == Phase.DISCARDING) {
			trigger(monster, null);
		}
	}

	@Override
	protected void trigger(Monster monster, Monster attacker) {
		monster.incStars(2);
	}

}