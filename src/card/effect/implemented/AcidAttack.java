package card.effect.implemented;

import java.util.ArrayList;

import card.effect.Effect;
import monster.Monster;

/**
 * Acid attack
 * Deal 1 extra damage each turn (even when you don't otherwise attack).
 */
public class AcidAttack implements Effect {

	@Override
	public void execute(Monster self, ArrayList<Monster> monsters) {
		for (Monster monster : monsters) {
			if (!monster.equals(self)) {
				monster.decHealth(1);
			}
		}
	}
}