package card.evolutioncard.implemented.kong;

import card.evolutioncard.EvolCard;

/**
 * Iron Curtain: Permanent, Other Monsters who yield Tokyo lose 1 HEART.
 */
public class IronCurtain extends EvolCard {

	public IronCurtain() {
		super("Iron Curtain", false, new IronCurtainEffect(), "Other Monsters who yield Tokyo lose 1 HEART.");
	}

}