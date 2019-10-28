package card.storecard.implemented;

import card.storecard.StoreCard;
import card.storecard.effect.implemented.ArmorPlatingEffect;

public class ArmorPlating extends StoreCard {

	public ArmorPlating() {
		super("Armor Plating", 4, false, new ArmorPlatingEffect(), "Ignore damage of 1");
	}

}