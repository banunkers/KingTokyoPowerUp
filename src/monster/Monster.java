package monster;

import java.util.ArrayList;
import java.util.HashMap;

import card.evolutioncard.EvolCard;
import card.storecard.StoreCard;
import dice.Dice;
import game.GamePhase;
import game.Phase;

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
	private ArrayList<StoreCard> storeCards = new ArrayList<StoreCard>();
	private ArrayList<EvolCard> evolCards = new ArrayList<EvolCard>();
	private ArrayList<EvolCard> activeEvolCards = new ArrayList<EvolCard>();	// The active evol cards (activated by rolling 3 HEARTS)
	private int totalDamage = 0;	// = num claws + more damage modifiers from cards
	private HashMap<Dice, Integer> rolledDice;

	// Card effect modifiers
	private int moreDamage = 0;
	private int costReduction = 0;
	private int armor = 0;

	public Monster(String name) {
		this.name = name;
	}

	/**
	 * @return the monsters total damage
	 */
	public int getTotalDamage() {
		return totalDamage;
	}

	/**
	 * Sets the monsters total damage
	 * @param numClaws the number of claws
	 */
	public void setTotalDamage(int numClaws) {
		this.totalDamage = numClaws + moreDamage;
	}

	/**
	 * @return the monsters armor
	 */
	public int getArmor() {
		return armor;
	}

	/**
	 * Sets the monsters armor
	 * @param armor the amount of armor
	 */
	public void setArmor(int armor) {
		this.armor = armor;
	}

	/**
	 * Increases the monsters armor
	 * @param armor the amount to increase by
	 */
	public void incArmor(int armor) {
		this.armor += armor;
	}

	/**
	 * @return the monsters current store cards
	 */
	public ArrayList<StoreCard> getStoreCards() {
		return storeCards;
	}

	/**
	 * @return the monsters current evolution cards
	 */
	public ArrayList<EvolCard> getEvolCards() {
		return evolCards;
	}

	/**
	 * Adds a store card to the monsters current cards
	 * @param card the store card to add
	 */
	public void addStoreCard(StoreCard card) {
		storeCards.add(card);
	}

	/**
	 * Sets the monsters available evolution cards
	 * @param card the evolution cards to add
	 */
	public void setEvolCards(ArrayList<EvolCard> cards) {
		this.evolCards = cards;
	}

	/**
	 * @return the monsters active evolution cards
	 */
	public ArrayList<EvolCard> getActiveEvolCards() {
		return this.activeEvolCards;
	}

	/**
	 * Adds an evolution card to the monsters active evolutions and checks if it should be triggered
	 * @param activatedEvol the evolution card to add
	 * @param monsters the monsters in the game
	 */
	public void addActiveEvolCard(EvolCard activatedEvol, ArrayList<Monster> monsters) {
		this.activeEvolCards.add(activatedEvol);
		activatedEvol.getEffect().trigger(this, this, null, monsters);
	}

	/**
	 * Activates one of the monsters evolution cards, if its a temporary evolution card
	 * it will be played directly.
	 * @param monsters the monsters in the game
	 * @return the activated evolution card or null if the monster didn't
	 * have any evolution card to activate
	 */
	public EvolCard activateEvolCard(ArrayList<Monster> monsters) {
		EvolCard activated = null;
		if (evolCards.size() > 0) {
			activated = evolCards.remove(0);
			if (activated.isTemporary()) {
				activated.getEffect().trigger(this, null, null, monsters);
			} else {
				this.addActiveEvolCard(activated, monsters);
			}
			return activated;
		}
		return null;
	}

	/**
	 * @return the monsters current cost reduction of buying cards
	 */
	public int getCostReduction() {
		return costReduction;
	}

	/**
	 * Sets the monsters cost reduction
	 * @param costReduction the amount of reduced costs
	 */
	public void setCostReduction(int costReduction) {
		this.costReduction = costReduction;
	}

	/**
	 * Increases the monsters current cost reduction
	 * @param costReduction the amount to increase with
	 */
	public void incCostReduction(int costReduction) {
		this.costReduction += costReduction;
	}

	/**
	 * @return the monsters current damage modifier
	 */
	public int getMoreDamage() {
		return moreDamage;
	}

	/**
	 * Sets the monsters damage modifier
	 * @param moreDamage the amount of increased damage
	 */
	public void setMoreDamage(int moreDamage) {
		this.moreDamage = moreDamage;
	}

	/**
	 * Increases the monsters current damage modifier
	 * @param moreDamage the amount to increase with
	 */
	public void incMoreDagame(int moreDamage) {
		this.moreDamage += moreDamage;
	}

	/**
	 * @return the monsters total stars
	 */
	public int getStars() {
		return stars;
	}

	/**
	 * Sets the monsters stars
	 * @param stars amount of stars to set to
	 */
	public void setStars(int stars) {
		this.stars = stars;
	}

	/**
	 * Increases the monsters total stars
	 * @param stars amount to increase with
	 */
	public void incStars(int stars) {
		this.stars += stars;
	}

	/**
	 * Decreases the monsters total stars to a minimum of 0
	 * @param stars amount to decrease with
	 */
	public void decStars(int stars) {
		if (this.stars - stars > 0) {
			this.stars -= stars;
		} else {
			this.stars = 0;
		}
	}

	/**
	 * @return the monsters total energy
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * Increases the monsters energy
	 * @param energy amount to increase with
	 */
	public void incEnergy(int energy) {
		this.energy += energy;
	}

	/**
	 * Decreases the monsters energy to a minimum of 0
	 * @param energy amount to decrease with
	 */
	public void decEnergy(int energy) {
		if (this.energy - energy > 0) {
			this.energy -= energy;
		} else {
			this.energy = 0;
		}
	}

	/**
	 * @return if the monster is inside Tokyo or not
	 */
	public boolean isInTokyo() {
		return inTokyo;
	}

	/**
	 * Changes the monsters position
	 * @param inTokyo if the monster should move to or leave Tokyo
	 * @param gamePhase the game phase
	 * @param monsters the monsters in the game
	 */
	public void setInTokyo(GamePhase gamePhase, boolean inTokyo, ArrayList<Monster> monsters) {
		if (!inTokyo) { // Monster is yielding tokyo
			gamePhase.setPhase(Phase.YIELDING_TOKYO, this, null, monsters);
		} else {
			gamePhase.setPhase(Phase.TAKING_TOKYO, this, null, monsters);
		}
		this.inTokyo = inTokyo;
	}

	/**
	 * @return the monsters health
	 */
	public int getHealth() {
		return currentHealth;
	}

	/**
	 * Sets the monsters current health
	 * @param health the health to set it to1
	 */
	public void setHealth(int health) {
		this.currentHealth = health;
	}

	/**
	 * @return if the monster is alive or not
	 */
	public boolean isAlive() {
		return currentHealth > 0 ? true : false;
	}

	/**
	 * Increases the monsters health to a maximum of its maximum health
	 * @param health amount to increase with
	 */
	public void incHealth(int health) {
		if (currentHealth + health > maxHealth) {
			currentHealth = maxHealth;
		} else {
			currentHealth += health;
		}
	}

	/**
	 * Decreases the health of a monser, if their armor is not enough to entierly block the attack,
	 * to a minimum of 0
	 * @param health amount to decrease with
	 */
	public void decHealth(int health) {
		if (health > armor) {
			if (currentHealth - health < 0) {
				currentHealth = 0;
			} else {
				currentHealth -= health;
			}
		}
	}
	/**
	 * Sets the monsters maximum health
	 * @param maxHealth the monsters new maximum health
	 */
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	/**
 	 * @return the monsters maximum health
	 */
	public int getMaxHealth() {
		return maxHealth;
	}

	/**
	 * @return the monsters name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Stringifies the monsters current cards (includes both store cards and evolution cards)
	 * @return a string containing the monsters cards
	 */
	public String cardsToString() {
		String returnString = "";
		if (storeCards.size() == 0 && activeEvolCards.size() == 0)
			return "[NO CARDS]:";
		for (int i = 0; i < storeCards.size(); i++) {
			returnString += "\t[" + i + "] " + storeCards.get(i) + ":";
		}
		for (int i = storeCards.size(); i < storeCards.size() + activeEvolCards.size(); i++) {
			returnString += "\t[" + i + "] " + activeEvolCards.get(i) + ":";
		}
		return returnString;
	}

	/**
	 * Sets the monsters rolled dice
	 * @param result the rolled dice
	 */
	public void setRolledDice(HashMap<Dice, Integer> result) {
		rolledDice = result;
	}

	/**
	 * @return the monsters last rolled dice
	 */
	public HashMap<Dice, Integer> getRolledDice() {
		return rolledDice;
	}
}