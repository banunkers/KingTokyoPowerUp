package card.storecard.implemented;

import card.storecard.StoreCard;
import card.storecard.effect.implemented.CornerStoreEffect;

public class CornerStore extends StoreCard {
	public CornerStore() {
		super("Corner Store", 3, true, new CornerStoreEffect(), "+1 stars");
	}
}