package game;

import card.storecard.StoreCard;

import java.util.ArrayList;

import card.evolutioncard.EvolCard;
import monster.Monster;

public class GamePhase {
	private Phase currPhase;
	private ArrayList<Monster> monsters;

	public GamePhase(ArrayList<Monster> monsters) {
		this.monsters = monsters;
	}

	public Phase getPhase() {
		return currPhase;
	}
	
	/**
	 * Changes the phase of the game and checks if any of the monsters card should take effect
	 * @param phase the game phase
	 * @param monster the current monster
	 * @param attacker optional parameter containing the attacking monster
	 */
	public void setPhase(Phase phase, Monster monster, Monster attacker) {
		currPhase = phase;
		for (StoreCard card : monster.getStoreCards()) {
			card.getEffect().checkTrigger(monster, currPhase, attacker);
		}

		// Go through all the active permanent evolution cards and check if they should trigger
		for (Monster monWithEvo : monsters) {
			for (EvolCard card : monWithEvo.getActiveEvolCards()) {
				card.getEffect().trigger(monWithEvo, monster, currPhase);
			}
		}
	}
}