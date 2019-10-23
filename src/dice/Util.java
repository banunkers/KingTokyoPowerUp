package dice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Util {
	private Random ran = new Random();

	public void sort(ArrayList<Dice> dice) {
		Collections.sort(dice);
	}
	
	/**
	 * Rolls a specified amount of dice with values 1-5
	 * @param numDice the number of dice to roll
	 * @return	an array list containing the rolled dice
	 */
	public ArrayList<Dice> roll(int numDice) {
		ArrayList<Dice> dice = new ArrayList<Dice>();
		for (int i = 0; i < numDice; i++) {
			dice.add(new Dice(ran.nextInt(6)));
		}
		return dice;
	}
	
	/**
	 * Rerolls the specified dice
	 * @param dice the dice list 
	 * @param reroll the index (starting at 1) of dice to reroll
	 */
	public void reroll(ArrayList<Dice> dice, String[] reroll) {
		// Remove the specified dice
		for (int i = 0; i < reroll.length; i++) {
			dice.remove(Integer.parseInt(reroll[i]) - 1);
		}
		// Append newly rolled dice
		dice.addAll(roll(6 - dice.size()));
	}
}