package card.evolutioncard.implemented.kraken;

import card.evolutioncard.EvolCard;

/**
 * Healing Rain: Temporary, Gain 2 health
 */
public class HealingRain extends EvolCard {

	public HealingRain() {
		super("Healing Rain", true, new HealingRainEffect(), "Gain 2 HEART.");
	}

}