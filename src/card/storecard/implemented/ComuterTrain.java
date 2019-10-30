package card.storecard.implemented;

import java.util.ArrayList;

import card.storecard.StoreCard;
import card.storecard.implemented.effect.ComuterTrainEffect;
import monster.Monster;

public class ComuterTrain extends StoreCard {
	public ComuterTrain(ArrayList<Monster> monsters) {
		super("Commuter Train", 4, true, new ComuterTrainEffect(monsters), "+2 stars");
	}
}