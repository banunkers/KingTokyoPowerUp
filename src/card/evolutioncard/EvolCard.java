package card.evolutioncard;

import card.Card;

/**
 * Super class of all the evolution cards
 */
public abstract class EvolCard implements Card<EvolEffect> {
	private String name;
	private boolean temporary;
	private EvolEffect effect;
	private String description;

	public EvolCard(String name, boolean temporary, EvolEffect effect, String description) {
		this.name = name;
		this.temporary = temporary;
		this.effect = effect;
		this.description = description;
	}

	public EvolEffect getEffect() {
		return effect;
	}

	public String toString() {
		return name + ", " + (temporary ? "TEMPORARY" : "PERMANENT") + ", Effect " + description;
	}

	/**
	 * Returns the type of the evolution card. There are two types of cards, temporary and permanent.
	 * @return if the card is temporary or not
	 */
	public boolean isTemporary() {
		return temporary;
	}

	public String getName() {
		return name;
	}
}
