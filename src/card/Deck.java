package card;

import java.util.ArrayList;
import java.util.Collections;

import card.storecard.StoreCard;
import monster.Monster;

/**
 * Handles all of the decks
 */
public class Deck {
	private ArrayList<StoreCard> deck;
	private ArrayList<StoreCard> store = new ArrayList<StoreCard>();
	
	/**
	 * Creates a deck containing the store cards and gives each monster their shuffled evolution cards
	 * @param monsters the monsters playing
	 */
	public Deck(ArrayList<Monster> monsters) {
		CardFactory cardFactory = new CardFactory(monsters);
		deck = cardFactory.getStoreCards();

		// Create the evolution cards for each monster
		for (Monster monster : monsters) {
			monster.setEvolCards(cardFactory.getEvolCards(monster));
		}
		
		// Start the game with 3 cards face up in the store
		for (int i = 0; i < 3; i++) {
			store.add(deck.remove(0));
		}
	}

	public void shuffle() {
		Collections.shuffle(deck);
	}

	/**
	 * Resets the three cards in the store by replacing them with new ones
	 */
	public void resetStore() {
		store.clear();
		for (int i = 0; i < 3; i++) {
			store.add(deck.get(0));
		}
	}

	// Print the store
	public String toString() {
		String returnString = "";
		for (int i = 0; i < 3; i++) {
			returnString += "\t[" + i + "] " + store.get(i) + ":";
		}
		return returnString;
	}

	public ArrayList<StoreCard> getDeck() {
		return this.deck;
	}

	public ArrayList<StoreCard> getStore() {
		return this.store;
	}

	/**
	 * Buys a card from the store and replaces it with a new one
	 * @param index the index of the card
	 * @return the card bought or null if no card in the store
	 */
	public StoreCard buyFromStore(int index) {
		if (this.store.size() > 0) {
			this.store.add(this.deck.get(0));
			return this.store.remove(index);
		}
		return null;
	}
}