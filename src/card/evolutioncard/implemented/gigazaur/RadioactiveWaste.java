package card.evolutioncard.implemented.gigazaur;

import java.util.ArrayList;

import card.evolutioncard.EvolCard;
import monster.Monster;

/**
 * Radioactive Waster: Temporary, Gain 2 energy and 1 health
 */
public class RadioactiveWaste extends EvolCard {

	public RadioactiveWaste(ArrayList<Monster> monsters) {
		super("Radioactive Waste", true, new RadioactiveWasteEffect(monsters), "Gain 2 ENERGY and 1 HEART.");
	}

}