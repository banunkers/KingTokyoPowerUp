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
	protected void trigger(Monster monster, Monster attacker, Phase phase) {
		if (phase == Phase.RESOLVING) {
			if (monster.getRolledDice().get(new Dice(2)).intValue() >= 3)
			for (Monster mon : monsters) {
				if (!mon.equals(monster))
					mon.decHealth(1);
			}
		}

	}

}