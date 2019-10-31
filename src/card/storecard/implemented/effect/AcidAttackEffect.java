package card.storecard.implemented.effect;

import java.util.ArrayList;

import card.storecard.StoreEffect;
import game.Phase;
import monster.Monster;

/**
 * Acid attack: Deal 1 extra damage each turn (even when you don't otherwise
 * attack).
 */
public class AcidAttackEffect extends StoreEffect {

	@Override
	public void checkTrigger(Monster monster, Phase phase, Monster attacker, ArrayList<Monster> monsters) {
		if (!done && phase == Phase.ATTACKING) {
			trigger(monster, null, monsters);
			done = true;
		} else if (phase == Phase.ATTACKING_NO_CLAW) {
			triggerNoClaw(monster, monsters);
		}
	}

	private void triggerNoClaw(Monster monster, ArrayList<Monster> monsters) {
		if (monster.isInTokyo()) {
			for (Monster mon : monsters) {
				if (!mon.equals(monster)) {
					mon.decHealth(1);
				}
			}
		} else {
			for (Monster mon : monsters) {
				if (mon.isInTokyo()) {
					mon.decHealth(1);
				}
			}
		}
	}

	@Override
	public void trigger(Monster monster, Monster attacker, ArrayList<Monster> monsters) {
		monster.incMoreDagame(1);
	}
}