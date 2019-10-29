package card.storecard;

import card.Card;
import card.storecard.StoreEffect;

public abstract class StoreCard implements Card<StoreEffect> {
	private String name;
	private int cost;
	private boolean discard;
	private StoreEffect effect;
	private String description;

	public StoreCard(String name, int cost, boolean discard, StoreEffect effect, String description) {
		this.name = name;
		this.cost = cost;
		this.discard = discard;
		this.effect = effect;
		this.description = description;
	}

	public StoreEffect getEffect() {
		return effect;
	}

	public String toString() {
		return name + ", Cost " + cost + ", " + (discard ? "DISCARD" : "KEEP") + ", Effect " + description;
	}

	/**
	 * Returns the type of a card. There are two types of cards, discard and keep.
	 * @return true if the card is discard and false if keep
	 */
	public boolean isDiscard() {
		return discard;
	}

	public int getCost() {
		return cost;
	}

	public String getName() {
		return name;
	}
}