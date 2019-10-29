package card.evolutioncard;

public abstract class EvolutionCard {
	private String name;
	private int cost;
	private boolean temporary;
	private Effect effect;
	private String description;

	public EvolutionCard(String name, int cost, boolean temporary, Effect effect, String description) {
		this.name = name;
		this.cost = cost;
		this.temporary = temporary;
		this.effect = effect;
		this.description = description;
	}

	public Effect getEffect() {
		return effect;
	}

	public String toString() {
		return name + ", Cost " + cost + ", " + (temporary ? "TEMPORARY" : "PERMANENT") + ", Effect " + description;
	}

	/**
	 * Returns the type of the evolution card. There are two types of cards, temporary and permanent.
	 * @return if the card is temporary or not
	 */
	public boolean isTeporary() {
		return temporary;
	}

	public int getCost() {
		return cost;
	}

	public String getName() {
		return name;
	}
}
