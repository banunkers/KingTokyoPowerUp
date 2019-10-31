package card.storecard.implemented.effect;

import java.util.ArrayList;

import card.storecard.StoreEffect;
import game.Phase;
import monster.Monster;

/**
 * Comuter Train: Discard, + 2[Star]
 */
public class ComuterTrainEffect extends StoreEffect {

	@Override
	public void checkTrigger(Monster monster, Phase phase, Monster attacker, ArrayList<Monster> monsters) {
		if (phase == Phase.DISCARDING) {
			trigger(monster, null, monsters);
		}
	}

	@Override
	protected void trigger(Monster monster, Monster attacker, ArrayList<Monster> monsters) {
		monster.incStars(2);
	}

}