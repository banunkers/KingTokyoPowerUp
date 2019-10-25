package card;

public abstract class Card {
	private String name;
	private int cost;
	private boolean discard;
	private Effect effect;
	private String description;

	public Card(String name, int cost, boolean discard, Effect effect, String description) {
		this.name = name;
		this.cost = cost;
		this.discard = discard;
		this.effect = effect;
		this.description = description;
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