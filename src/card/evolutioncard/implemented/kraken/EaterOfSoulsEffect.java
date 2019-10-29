package card.evolutioncard.implemented.kraken;

import java.util.ArrayList;

import card.evolutioncard.EvolEffect;
import game.Phase;
import monster.Monster;

/**
 * Eater of Souls: Permanent, Gain 1 HEART each time you take control of Tokyo.
 * You can have up to 12 HEART as long as you own this card.
 */
public class EaterOfSoulsEffect extends EvolEffect {

	public EaterOfSoulsEffect(ArrayList<Monster> monsters) {
		super(monsters);
	}

	@Override
	public void trigger(Monster monster, Monster currMonster, Phase phase) {
		if (monster.equals(currMonster)) { // If its krakens turn
			if (!done) {
				monster.setMaxHealth(12);
				done = true;
			}

			if (phase == Phase.TAKING_TOKYO) {
				monster.incHealth(1);
			}
		}
	}

}