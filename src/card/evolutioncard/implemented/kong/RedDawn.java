package card.evolutioncard.implemented.kong;

import card.evolutioncard.EvolCard;

/**
 * Red Dawn: Temporary, All other monsters lose 2 health
 */
public class RedDawn extends EvolCard {

	public RedDawn() {
		super("Red Dawn", true, new RedDawnEffect(), "All other Monsters lose 2 HEART.");
	}

}