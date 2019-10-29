package card;

import java.util.ArrayList;
import java.util.Collections;

import card.storecard.StoreCard;
import monster.Monster;

/**
 * Handles all of the decks
 */
public class Deck {
	public ArrayList<StoreCard> deck;
	public StoreCard[] store = new StoreCard[3];
	
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