package card.storecard.effect.implemented;

import card.storecard.effect.Effect;
import game.Phase;
import monster.Monster;

/**
 * Acid attack: Deal 1 extra damage each turn (even when you don't otherwise
 * attack).
 */
public class AcidAttackEffect extends Effect {

	@Override
	public void checkTrigger(Monster monster, Phase phase, Monster attacker) {
		if (!done) {
			if (phase == Phase.ATTACKING) {
				trigger(monster, null);
				done = true;
			}
		}
	}

	@Override
	protected void trigger(Monster monster, Monster attacker) {
		monster.incMoreDagame(1);
	}
}