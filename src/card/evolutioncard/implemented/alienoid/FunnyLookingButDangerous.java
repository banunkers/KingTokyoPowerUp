package card.evolutioncard.implemented.alienoid;

import java.util.ArrayList;

import card.evolutioncard.EvolCard;
import monster.Monster;

/**
 * Alien Scourge: Temporary, Gain 2 stars
 */
public class FunnyLookingButDangerous extends EvolCard {

	public FunnyLookingButDangerous(ArrayList<Monster> monsters) {
		super("Funny Looking But Dangerous", false, new FunnyLookingButDangerousEffect(monsters), "If you roll at least TWO TWO TWO each other Monster loses 1 HEART.");
	}

}