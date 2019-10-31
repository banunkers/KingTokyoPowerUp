package card.evolutioncard.implemented.gigazaur;

import card.evolutioncard.EvolCard;

/**
 * Radioactive Waster: Temporary, Gain 2 energy and 1 health
 */
public class RadioactiveWaste extends EvolCard {

	public RadioactiveWaste() {
		super("Radioactive Waste", true, new RadioactiveWasteEffect(), "Gain 2 ENERGY and 1 HEART.");
	}

}