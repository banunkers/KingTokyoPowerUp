package card.evolutioncard.implemented.gigazaur;

import java.util.ArrayList;

import card.evolutioncard.EvolCard;
import monster.Monster;

/**
 * Defender of Tokyo: Permanent, If you start your turn in Tokyo all other Monsters lose 1 STAR.
 */
public class DefenderOfTokyo extends EvolCard {

	public DefenderOfTokyo(ArrayList<Monster> monsters) {
		super("Defender of Tokyo", false, new DefenderOfTokyoEffect(monsters), "If you start your turn in Tokyo, all other Monsters lose 1 STAR.");
	}

}