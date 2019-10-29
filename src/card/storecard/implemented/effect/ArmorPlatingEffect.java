package card.storecard.implemented.effect;

import card.Effect;
import game.Phase;
import monster.Monster;

/**
 * Armor Plating: Keep, Ignore damage of 1
 */
public class ArmorPlatingEffect extends Effect {

	@Override
	public void checkTrigger(Monster monster, Phase phase, Monster attacker) {
		if (phase == Phase.TAKING_DAMAGE) {
			trigger(monster, attacker);   
		}
	}

	@Override
	protected void trigger(Monster monster, Monster attacker) {
		// Inc the health of monster to "ignore" damage of 1
		if (attacker.getTotalDamage() == 1) {
			monster.incHealth(1);
		}
	}
}