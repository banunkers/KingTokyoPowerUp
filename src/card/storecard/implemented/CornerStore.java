package card.storecard.implemented;

import java.util.ArrayList;

import card.storecard.StoreCard;
import card.storecard.implemented.effect.CornerStoreEffect;
import monster.Monster;

public class CornerStore extends StoreCard {
	public CornerStore(ArrayList<Monster> monsters) {
		super("Corner Store", 3, true, new CornerStoreEffect(monsters), "+1 stars");
	}
}