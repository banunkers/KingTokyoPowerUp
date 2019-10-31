package card.storecard.implemented.effect;

import java.util.ArrayList;

import card.storecard.StoreEffect;
import game.Phase;
import monster.Monster;

/**
 * Alien Metabolism: Buying cards costs you 1 less [Energy].
 */
public class AlienMetabolismEffect extends StoreEffect {

	@Override
	public void checkTrigger(Monster monster, Phase phase, Monster attacker, ArrayList<Monster> monsters) {
		if (!done && (phase == Phase.BUYING)) {
			trigger(monster, null, monsters);
			done = true;
		}
	}

	@Override
	protected void trigger(Monster monster, Monster attacker, ArrayList<Monster> monsters) {
		monster.incCostReduction(1);
	}
}