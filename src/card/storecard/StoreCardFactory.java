package card.storecard;

import java.util.ArrayList;

import card.storecard.implemented.*;
import monster.Monster;

/**
 * Creates all of the implemented store cards
 */
public class StoreCardFactory {

	/**
	 * Creates an array list containing all of the currently implemented store cards
	 * @return the store cards
	 */
	public ArrayList<StoreCard> getStoreCards() {
		ArrayList<StoreCard> cards = new ArrayList<StoreCard>();
		cards.add(new AcidAttack());
		cards.add(new AlienMetabolism());
		cards.add(new AlphaMonster());
		cards.add(new ApartmentBuilding());
		cards.add(new ArmorPlating());
		cards.add(new ComuterTrain());
		cards.add(new CornerStore());
		return cards;
	}

	/**
	 * Creates an array list containing all of the currently implemented evolution cards
	 * for the specified monster
	 * @return the array list of cards
	 */
	public ArrayList<StoreCard> getEvolCards(Monster monster) {
		ArrayList<StoreCard> cards = new ArrayList<StoreCard>();
		return cards;
	}
}