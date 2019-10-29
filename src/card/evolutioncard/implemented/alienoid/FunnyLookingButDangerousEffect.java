package card.evolutioncard.implemented.alienoid;

import java.util.ArrayList;

import card.evolutioncard.EvolEffect;
import dice.Dice;
import game.Phase;
import monster.Monster;

/**
 * Funny Looking But Dangerous: Permanent, If you roll at least TWO TWO TWO each other Monster loses 1 HEART.
 */
public class FunnyLookingButDangerousEffect extends EvolEffect {

	public FunnyLookingButDangerousEffect(ArrayList<Monster> monsters) {
		super(monsters);
	}

	@Override
	public void trigger(Monster monster, Monster currMonster, Phase phase) {
		if (phase == Phase.RESOLVING && monster.equals(currMonster)) {
			if (monster.getRolledDice().containsKey(new Dice(2))) {
				int numTWO = monster.getRolledDice().get(new Dice(2)).intValue();
				if (numTWO >= 3) {
					for (Monster mon : monsters) {
						if (!mon.equals(monster))
							mon.decHealth(1);
					}
				}
			}
		}

	}

}