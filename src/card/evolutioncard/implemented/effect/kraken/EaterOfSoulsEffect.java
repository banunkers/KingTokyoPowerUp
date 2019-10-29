package card.evolutioncard.implemented.effect.kraken;

import java.util.ArrayList;

import card.evolutioncard.Effect;
import game.Phase;
import monster.Monster;

/**
 * Healing Rain: Permanent, Gain 1 HEART each time you take control of Tokyo.
 * You can have up to 12 HEART as long as you own this card.
 */
public class EaterOfSoulsEffect extends Effect {

	public EaterOfSoulsEffect(ArrayList<Monster> monsters) {
		super(monsters);
	}

	@Override
	protected void trigger(Monster monster, Monster attacker, Phase phase) {
		if (!done) {
			monster.setMaxHealth(12);
			done = true;
		}

		if (phase == Phase.TAKING_TOKYO) {
			monster.incHealth(1);
		}

	}

}