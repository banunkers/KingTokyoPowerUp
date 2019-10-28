package card.storecard.implemented;

import card.storecard.StoreCard;
import card.storecard.effect.implemented.AlphaMonsterEffect;

public class AlphaMonster extends StoreCard {
	public AlphaMonster() {
		super("Alpha Monster", 5, false, new AlphaMonsterEffect(), "Gain 1 star when you attack");
	}
}