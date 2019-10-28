package monster;

import java.util.ArrayList;

import card.storecard.StoreCard;

/**
 * The super class of all monsters in the game
 */
public abstract class Monster {
	private String name;
	private int maxHealth = 10;
	private int currentHealth = 10;
	private int energy = 0;
	private int stars = 0;
	private boolean inTokyo = false;
	private ArrayList<StoreCard> cards = new ArrayList<StoreCard>();
	private int totalDamage = 0;	// = num claws + more damage modifiers from cards

	// Card effect modifiers
	private int moreDamage = 0;
	private int costReduction = 0;
	private int armor;

	public Monster(String name) {
		this.name = name;
	}

	public int getTotalDamage() {
		return totalDamage;
	}

	public void setTotalDamage(int numClaws) {
		this.totalDamage = numClaws + moreDamage;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public void incArmor(int armor) {
		this.armor += armor;
	}

	public ArrayList<StoreCard> getCards() {
		return cards;
	}

	public void addCard(StoreCard card) {
		cards.add(card);
	}

	public int getCostReduction() {
		return costReduction;
	}

	public void setCostReduction(int costReduction) {
		this.costReduction = costReduction;
	}

	public void incCostReduction(int costReduction) {
		this.costReduction += costReduction;
	}

	public int getMoreDamage() {
		return moreDamage;
	}

	public void setMoreDamage(int moreDamage) {
		this.moreDamage = moreDamage;
	}

	public void incMoreDagame(int moreDamage) {
		this.moreDamage += moreDamage;
	}

	public int getStars() {
		return stars;
	}

	public void incStars(int stars) {
		this.stars += stars;
	}

	public void decStars(int stars) {
		this.stars -= stars;
	}

	public int getEnergy() {
		return energy;
	}

	public void incEnergy(int energy) {
		this.energy += energy;
	}

	public void decEnergy(int energy) {
		this.energy -= energy;
	}

	public boolean isInTokyo() {
		return inTokyo;
	}

	public void setInTokyo(boolean inTokyo) {
		this.inTokyo = inTokyo;
	}

	public int getHealth() {
		return currentHealth;
	}

	public boolean isAlive() {
		return currentHealth > 0 ? true : false;
	}

	public void incHealth(int health) {
		if (currentHealth + health > maxHealth) {
			currentHealth = maxHealth;
		} else {
			currentHealth += health;
		}
	}

	public void decHealth(int health) {
		if (currentHealth - health < 0) {
			currentHealth = 0;
		} else {
			currentHealth -= health;
		}
	}

	public void setMaxHealth(int health) {
		maxHealth = health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public String getName() {
		return name;
	}

	public String cardsToString() {
		String returnString = "";
		if (cards.size() == 0)
			return "[NO CARDS]:";
		for (int i = 0; i < cards.size(); i++) {
			returnString += "\t[" + i + "] " + cards.get(i) + ":";
		}
		return returnString;
	}
}