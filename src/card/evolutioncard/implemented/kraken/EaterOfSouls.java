package card.evolutioncard.implemented.kraken;

import java.util.ArrayList;

import card.evolutioncard.EvolCard;
import monster.Monster;

/**
 * Eater of Souls: Permanent, Gain 1 HEART each time you take control of Tokyo.
 * You can have up to 12 HEART as long as you own this card.
 */
public class EaterOfSouls extends EvolCard {

	public EaterOfSouls(ArrayList<Monster> monsters) {
		super("Eater of Souls", false, new EaterOfSoulsEffect(monsters), "Gain 1 HEART each time you take control of Tokyo. You can have up to 12 HEART as long as you own this card.");
	}

}