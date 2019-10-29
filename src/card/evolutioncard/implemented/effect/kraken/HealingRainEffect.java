package card.evolutioncard.implemented.effect.kraken;

import card.Effect;
import game.Phase;
import monster.Monster;

public class HealingRainEffect extends Effect {

	@Override
	public void checkTrigger(Monster monster, Phase phase, Monster attacker) {
		if (phase == Phase.PLAYING_CARD) {
			
		}
	}

	@Override
	protected void trigger(Monster monster, Monster attacker) {
		// TODO Auto-generated method stub

	}

}