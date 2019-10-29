package card.evolutioncard.implemented.effect.alienoid;

import java.util.ArrayList;

import card.evolutioncard.EvolEffect;
import game.Phase;
import monster.Monster;

/**
 * Alien Scourge: Temporary, Gain 2 stars
 */
public class AlienScourgeEffect extends EvolEffect {

	public AlienScourgeEffect(ArrayList<Monster> monsters) {
		super(monsters);
	}

	@Override
	protected void trigger(Monster monster, Monster attacker, Phase phase) {
		monster.incStars(2);
	}

}