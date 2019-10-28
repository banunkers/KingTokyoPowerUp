package card.storecard.implemented;

import card.storecard.StoreCard;
import card.storecard.effect.implemented.ComuterTrainEffect;

public class ComuterTrain extends StoreCard {
	public ComuterTrain() {
		super("Commuter Train", 4, true, new ComuterTrainEffect(), "+2 stars");
	}
}