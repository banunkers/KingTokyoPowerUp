package card.evolutioncard.implemented.kraken;

import card.evolutioncard.EvolCard;

/**
 * Eater of Souls: Permanent, Gain 1 HEART each time you take control of Tokyo.
 * You can have up to 12 HEART as long as you own this card.
 */
public class EaterOfSouls extends EvolCard {

	public EaterOfSouls() {
		super("Eater of Souls", false, new EaterOfSoulsEffect(), "Gain 1 HEART each time you take control of Tokyo. You can have up to 12 HEART as long as you own this card.");
	}

}