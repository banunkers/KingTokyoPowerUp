package card.storecard.effect.implemented;

import card.storecard.effect.Effect;
import game.Phase;
import monster.Monster;

/**
 * Alien Metabolism: Buying cards costs you 1 less [Energy].
 */
public class AlienMetabolismEffect extends Effect {

	@Override
	public void checkTrigger(Monster monster, Phase phase, Monster attacker) {
		if (!done && (phase == Phase.BUYING)) {
			trigger(monster, null);
			done = true;
		}
	}

	@Override
	protected void trigger(Monster monster, Monster attacker) {
		monster.incCostReduction(1);
	}
}