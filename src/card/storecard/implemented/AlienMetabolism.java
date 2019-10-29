package card.storecard.implemented;

import java.util.ArrayList;

import card.storecard.StoreCard;
import card.storecard.implemented.effect.AlienMetabolismEffect;
import monster.Monster;

public class AlienMetabolism extends StoreCard {

	public AlienMetabolism(ArrayList<Monster> monsters) {
		super("Alien Metabolism", 3, false, new AlienMetabolismEffect(monsters), "Buying cards costs you 1 less");
	}
}
