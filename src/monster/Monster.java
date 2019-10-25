package monster;

/**
 * The super class of all monsters in the game
 */
public abstract class Monster {
	private int maxHealth = 10;
	private int currentHealth = 10;
	private String name;
	private int energy = 0;
	private int stars = 0;
	private boolean inTokyo = false;

	public Monster(String name) {
		this.name = name;
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
}