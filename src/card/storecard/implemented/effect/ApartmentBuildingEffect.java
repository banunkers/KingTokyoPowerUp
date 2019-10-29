package card.storecard.implemented.effect;

import card.Effect;
import game.Phase;
import monster.Monster;

/**
 * Apartment Building: Discard, + 3[Star]
 */
public class ApartmentBuildingEffect extends Effect {

	@Override
	public void checkTrigger(Monster monster, Phase phase, Monster attacker) {
		if (phase == Phase.DISCARDING) {
			trigger(monster, null);
		}

	}

	@Override
	protected void trigger(Monster monster, Monster attacker) {
		monster.incStars(3);
	}

}