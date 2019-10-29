package card;

import java.util.ArrayList;

import card.evolutioncard.EvolutionCard;
import card.storecard.StoreCard;
import card.storecard.implemented.*;
import monster.Monster;

/**
 * Creates all of the implemented store cards
 */
public class CardFactory {
	private ArrayList<Monster> monsters;

	public CardFactory(ArrayList<Monster> monsters) {
		this.monsters = monsters;
	}

	/**
	 * Creates an array list containing all of the currently implemented store cards
	 * @return the store cards
	 */
	public ArrayList<StoreCard> getStoreCards() {
		ArrayList<StoreCard> cards = new ArrayList<StoreCard>();
		cards.add(new AcidAttack(monsters));
		cards.add(new AlienMetabolism(monsters));
		cards.add(new AlphaMonster(monsters));
		cards.add(new ApartmentBuilding(monsters));
		cards.add(new ArmorPlating(monsters));
		cards.add(new ComuterTrain(monsters));
		cards.add(new CornerStore(monsters));
		return cards;
	}

	/**
	 * Creates an array list containing all of the currently implemented evolution cards
	 * for the specified monster
	 * @return the array list of cards
	 */
	public ArrayList<EvolutionCard> getEvolCards(Monster monster) {
		ArrayList<EvolutionCard> cards = new ArrayList<EvolutionCard>();
		return cards;
	}
}