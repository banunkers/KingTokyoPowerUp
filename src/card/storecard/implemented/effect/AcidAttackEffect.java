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

	public AcidAttackEffect(ArrayList<Monster> monsters) {
		super(monsters);
	}

	@Override
	public void checkTrigger(Monster monster, Phase phase, Monster attacker) {
		if (!done && phase == Phase.ATTACKING) {
			trigger(monster, null);
			done = true;
		} else if (phase == Phase.ATTACKING_NO_CLAW) {
			triggerNoClaw(monster);
		}
	}

	private void triggerNoClaw(Monster monster) {
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
	public void trigger(Monster monster, Monster attacker) {
		monster.incMoreDagame(1);
	}
}