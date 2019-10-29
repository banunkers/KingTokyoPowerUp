package card.storecard.implemented.effect;

import java.util.ArrayList;

import card.storecard.StoreEffect;
import game.Phase;
import monster.Monster;

/**
 * Armor Plating: Keep, Ignore damage of 1
 */
public class ArmorPlatingEffect extends StoreEffect {

	public ArmorPlatingEffect(ArrayList<Monster> monsters) {
		super(monsters);
	}

	@Override
	public void checkTrigger(Monster monster, Phase phase, Monster attacker) {
		if (!done && phase == Phase.TAKING_DAMAGE) {
			trigger(monster, attacker);
			done = true;
		}
	}

	@Override
	protected void trigger(Monster monster, Monster attacker) {
		// Inc the armor of monster to "ignore" damage of 1
		attacker.incArmor(1);
	}
}