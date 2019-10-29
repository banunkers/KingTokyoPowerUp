package card.storecard.implemented;

import java.util.ArrayList;

import card.storecard.StoreCard;
import card.storecard.implemented.effect.ArmorPlatingEffect;
import monster.Monster;

public class ArmorPlating extends StoreCard {

	public ArmorPlating(ArrayList<Monster> monsters) {
		super("Armor Plating", 4, false, new ArmorPlatingEffect(monsters), "Ignore damage of 1");
	}

}