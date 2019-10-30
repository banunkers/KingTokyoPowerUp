package monster;

import java.util.ArrayList;
import java.util.Collections;

import monster.implemented.*;

public class MonsterFactory {

	/**
	 * Creates a randomly generated list of monsters
	 * 
	 * @param numMonsters the number of monsters to generate
	 * @return an array list of monsters
	 * @throws Exception the number of monsters requested is greater than the number of monsters in the game
	 */
	public ArrayList<Monster> getMonsters(int numMonsters) throws Exception {
		ArrayList<Monster> allMonsters = createMonsters();
		
		if (numMonsters > allMonsters.size()) {
			throw new Exception(
				"The game doesn't support enough monsters for the number of current players. Currently there are a total of "
				+ allMonsters.size() + " monsters available. \n"
			);
		}

		ArrayList<Monster> monsters = new ArrayList<Monster>();
		for (int i = 0; i < numMonsters; i++) {
			Collections.shuffle(allMonsters);
			monsters.add(allMonsters.remove(0));
		}

		return monsters;
	}
	
	/**
	 * Creates all of the implemented monsters
	 * 
	 * @return an array list containing all the monsters
	 */
	private ArrayList<Monster> createMonsters() {
		ArrayList<Monster> allMonsters = new ArrayList<Monster>();

		// Add newly implemented monsters to this array list
		allMonsters.add(new Alienoid());
		allMonsters.add(new Kong());
		allMonsters.add(new Kraken());
		allMonsters.add(new Gigazaur());

		return allMonsters;
	}
}