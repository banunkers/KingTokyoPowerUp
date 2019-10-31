package card.storecard.implemented;

import card.storecard.StoreCard;
import card.storecard.implemented.effect.AlienMetabolismEffect;

public class AlienMetabolism extends StoreCard {

	public AlienMetabolism() {
		super("Alien Metabolism", 3, false, new AlienMetabolismEffect(), "Buying cards costs you 1 less");
	}
}
