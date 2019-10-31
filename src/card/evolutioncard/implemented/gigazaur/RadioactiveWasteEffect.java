package card.evolutioncard.implemented.gigazaur;

import java.util.ArrayList;

import card.evolutioncard.EvolEffect;
import game.Phase;
import monster.Monster;

/**
 * Radioactive Waster: Temporary, Gain 2 energy and 1 health
 */
public class RadioactiveWasteEffect extends EvolEffect {

	@Override
	public void trigger(Monster monster, Monster currMonster, Phase phase, ArrayList<Monster> monsters) {
		monster.incEnergy(2);
		monster.incHealth(1);
	}
}