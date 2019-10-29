package card.evolutioncard.implemented.gigazaur;

import java.util.ArrayList;

import card.evolutioncard.EvolEffect;
import game.Phase;
import monster.Monster;

/**
 * Defender of Tokyo: Permanent, If you start your turn in Tokyo all other Monsters lose 1 STAR.
 */
public class DefenderOfTokyoEffect extends EvolEffect {

	public DefenderOfTokyoEffect(ArrayList<Monster> monsters) {
		super(monsters);
	}

	@Override
	protected void trigger(Monster monster, Monster attacker, Phase phase) {
		if (phase == Phase.ROLLING && monster.isInTokyo()) {
			for (Monster mon : monsters) {
				mon.decStars(1);
			}
		}
	}

}