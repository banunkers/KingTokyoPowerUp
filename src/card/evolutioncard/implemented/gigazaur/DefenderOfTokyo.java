package card.evolutioncard.implemented.gigazaur;

import card.evolutioncard.EvolCard;

/**
 * Defender of Tokyo: Permanent, If you start your turn in Tokyo all other Monsters lose 1 STAR.
 */
public class DefenderOfTokyo extends EvolCard {

	public DefenderOfTokyo() {
		super("Defender of Tokyo", false, new DefenderOfTokyoEffect(), "If you start your turn in Tokyo, all other Monsters lose 1 STAR.");
	}

}