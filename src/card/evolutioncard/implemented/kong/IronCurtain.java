package card.evolutioncard.implemented.kong;

import java.util.ArrayList;

import card.evolutioncard.EvolCard;
import monster.Monster;

/**
 * Iron Curtain: Permanent, Other Monsters who yield Tokyo lose 1 HEART.
 */
public class IronCurtain extends EvolCard {

	public IronCurtain(ArrayList<Monster> monsters) {
		super("Iron Curtain", false, new IronCurtainEffect(monsters), "Other Monsters who yield Tokyo lose 1 HEART.");
	}

}