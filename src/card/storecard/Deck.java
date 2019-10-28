package card.storecard;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	public ArrayList<StoreCard> deck;
	public StoreCard[] store = new StoreCard[3];

	public Deck() {
		StoreCardFactory cardFactory = new StoreCardFactory();
		deck = cardFactory.getStoreCards();
		Collections.shuffle(deck);
		
		// Start the game with 3 cards face up in the store
		for (int i = 0; i < 3; i++) {
			store[i] = deck.remove(0);
		}
	}

	public void shuffle() {
		Collections.shuffle(deck);
	}

	/**
	 * Resets the three cards in the store
	 */
	public void resetStore() {
		for (int i = 0; i < store.length; i++) {
			store[i] = deck.remove(0);
		}
	}

	// Print the store
	public String toString() {
		String returnString = "";
		for (int i = 0; i < 3; i++) {
			returnString += "\t[" + i + "] " + store[i] + ":";
		}
		return returnString;
	}
}