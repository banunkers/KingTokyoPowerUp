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
	 * Creates a deck containing the store cards and gives each monster their shuffled evolution cards.
	 * Also places three of the store cards in the store so they can be bought by players.
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
	 * from the deck. Places the old store cards in the back of the deck.
	 */
	public void resetStore() {
		for (int i = 0; i < 3; i++) {
			this.deck.add(store.remove(0));
		}

		for (int i = 0; i < 3; i++) {
			if (this.deck.size() > 0) {
				this.store.add(deck.remove(0));
			}
		}
	}

	// Print the store
	public String toString() {
		String returnString = "";
		for (int i = 0; i < store.size(); i++) {
			returnString += "\t[" + i + "] " + store.get(i) + ":";
		}
		returnString += "\t[3] " + "Reset the store, Cost 2" + ":";
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
	 * @return the card bought or null if store is empty
	 */
	public StoreCard buyFromStore(int index) {
		if (this.store.size() > 0) {
			StoreCard boughtCard = this.store.remove(index);
			this.store.add(this.deck.remove(0));
			return boughtCard;
		}
		return null;
	}
}