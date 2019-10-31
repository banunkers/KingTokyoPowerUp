package card.evolutioncard.implemented.alienoid;

import java.util.ArrayList;

import card.evolutioncard.EvolEffect;
import game.Phase;
import monster.Monster;

/**
 * Alien Scourge: Temporary, Gain 2 stars
 */
public class AlienScourgeEffect extends EvolEffect {

	@Override
	public void trigger(Monster monster, Monster curMonster, Phase phase, ArrayList<Monster> monsters) {
		monster.incStars(2);
	}

}