package card.storecard.implemented;

import java.util.ArrayList;

import card.storecard.StoreCard;
import card.storecard.implemented.effect.AlphaMonsterEffect;
import monster.Monster;

public class AlphaMonster extends StoreCard {
	public AlphaMonster(ArrayList<Monster> monsters) {
		super("Alpha Monster", 5, false, new AlphaMonsterEffect(monsters), "Gain 1 star when you attack");
	}
}