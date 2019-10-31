package card.evolutioncard.implemented.gigazaur;

import java.util.ArrayList;

import card.evolutioncard.EvolEffect;
import game.Phase;
import monster.Monster;

/**
 * Defender of Tokyo: Permanent, If you start your turn in Tokyo all other Monsters lose 1 STAR.
 */
public class DefenderOfTokyoEffect extends EvolEffect {

	@Override
	public void trigger(Monster monster, Monster currMonster, Phase phase, ArrayList<Monster> monsters) {
		if (phase == Phase.START && monster.equals(currMonster)) {
			if (monster.isInTokyo()) {
				for (Monster mon : monsters) {
					if (!mon.equals(monster))
						mon.decStars(1);
				}
			}
		}
	}

}