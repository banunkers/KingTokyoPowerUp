package card.evolutioncard.implemented.alienoid;

import java.util.ArrayList;

import card.evolutioncard.EvolCard;
import monster.Monster;

/**
 * Alien Scourge: Temporary, Gain 2 stars
 */
public class AlienScourge extends EvolCard {

	public AlienScourge(ArrayList<Monster> monsters) {
		super("Alien Scourge", true, new AlienScourgeEffect(monsters), "Gain 2 ENERGY and 1 HEART.");
	}

}