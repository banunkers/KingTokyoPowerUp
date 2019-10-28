package game;

import card.storecard.StoreCard;
import monster.Monster;

public class GamePhase {
	private Phase currPhase;

	public GamePhase() {
		currPhase = Phase.START;
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
		for (StoreCard card : monster.getCards()) {
			card.getEffect().checkTrigger(monster, currPhase, attacker);
		}
	}
}