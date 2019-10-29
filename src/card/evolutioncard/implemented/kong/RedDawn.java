package card.evolutioncard.implemented.kong;

import java.util.ArrayList;

import card.evolutioncard.EvolCard;
import monster.Monster;

/**
 * Red Dawn: Temporary, All other monsters lose 2 health
 */
public class RedDawn extends EvolCard {

	public RedDawn(ArrayList<Monster> monsters) {
		super("Red Dawn", true, new RedDawnEffect(monsters), "All other Monsters lose 2 HEART.");
	}

}