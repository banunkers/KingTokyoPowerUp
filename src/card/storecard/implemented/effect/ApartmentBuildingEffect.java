package card.storecard.implemented.effect;

import java.util.ArrayList;

import card.storecard.StoreEffect;
import game.Phase;
import monster.Monster;

/**
 * Apartment Building: Discard, + 3[Star]
 */
public class ApartmentBuildingEffect extends StoreEffect {

	public ApartmentBuildingEffect(ArrayList<Monster> monsters) {
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
		monster.incStars(3);
	}

}