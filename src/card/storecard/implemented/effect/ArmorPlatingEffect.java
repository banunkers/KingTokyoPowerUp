package card.storecard.implemented.effect;

import java.util.ArrayList;

import card.storecard.StoreEffect;
import game.Phase;
import monster.Monster;

/**
 * Armor Plating: Keep, Ignore damage of 1
 */
public class ArmorPlatingEffect extends StoreEffect {

	@Override
	public void checkTrigger(Monster monster, Phase phase, Monster attacker, ArrayList<Monster> monsters) {
		if (!done) {
			trigger(monster, attacker, monsters);
			done = true;
		}
	}

	@Override
	protected void trigger(Monster monster, Monster attacker, ArrayList<Monster> monsters) {
		// Inc the armor of monster to "ignore" damage of 1
		monster.incArmor(1);
	}
}