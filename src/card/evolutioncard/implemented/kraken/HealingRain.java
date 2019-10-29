package card.evolutioncard.implemented.kraken;

import java.util.ArrayList;

import card.evolutioncard.EvolCard;
import monster.Monster;

/**
 * Healing Rain: Temporary, Gain 2 health
 */
public class HealingRain extends EvolCard {

	public HealingRain(ArrayList<Monster> monsters) {
		super("Healing Rain", true, new HealingRainEffect(monsters), "Gain 2 HEART.");
	}

}