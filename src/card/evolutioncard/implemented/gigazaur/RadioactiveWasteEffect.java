package card.evolutioncard.implemented.gigazaur;

import java.util.ArrayList;

import card.evolutioncard.EvolEffect;
import game.Phase;
import monster.Monster;

/**
 * Radioactive Waster: Temporary, Gain 2 energy and 1 health
 */
public class RadioactiveWasteEffect extends EvolEffect {

	public RadioactiveWasteEffect(ArrayList<Monster> monsters) {
		super(monsters);
	}

	@Override
	protected void trigger(Monster monster, Monster attacker, Phase phase) {
		monster.incEnergy(2);
		monster.incHealth(1);
	}
}