package card.storecard.implemented.effect;

import java.util.ArrayList;

import card.storecard.StoreEffect;
import game.Phase;
import monster.Monster;

/**
 * Comuter Train: Discard, + 2[Star]
 */
public class ComuterTrainEffect extends StoreEffect {

	public ComuterTrainEffect(ArrayList<Monster> monsters) {
		super(monsters);
	}

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