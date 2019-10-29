package tests.game.evolutioncard;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import card.evolutioncard.EvolCard;
import card.evolutioncard.implemented.alienoid.AlienScourge;
import card.evolutioncard.implemented.alienoid.FunnyLookingButDangerous;
import dice.Dice;
import game.GamePhase;
import game.Phase;
import monster.Monster;
import monster.implemented.Alienoid;
import monster.implemented.Kong;
import player.Player;

public class AlienoidTests {

	@Test
	public void AlienScourge() {
		Player alienoidPlayer = new Player(new Alienoid());
		Player otherPlayer = new Player(new Kong());
		Monster alienoid = alienoidPlayer.getMonster();
		Monster otherMon = otherPlayer.getMonster();

		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(alienoid);
		monsters.add(otherMon);

		ArrayList<EvolCard> evolCards = new ArrayList<EvolCard>();
		evolCards.add(new AlienScourge(monsters));
		alienoid.setEvolCards(evolCards);

		// Activate and assert
		int preStarsAlien = alienoid.getStars();
		int preStarsOther = otherMon.getStars();
		alienoid.activateEvolCard();
		assertEquals(preStarsAlien + 2, alienoid.getStars());

		// Check other monster no extra stars
		assertEquals(preStarsOther, otherMon.getStars());
	}

	@Test
	public void funnyLookingButDangerous() {
		Player alienoidPlayer = new Player(new Alienoid());
		Player otherPlayer = new Player(new Alienoid());
		Monster alienoid = alienoidPlayer.getMonster();
		Monster otherMon = otherPlayer.getMonster();

		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(alienoid);
		monsters.add(otherMon);

		// Give Alienoid Funny Looking but Dangerous 
		ArrayList<EvolCard> evolCards = new ArrayList<EvolCard>();
		evolCards.add(new FunnyLookingButDangerous(monsters));
		alienoid.setEvolCards(evolCards);
		alienoid.activateEvolCard();

		GamePhase gamePhase = new GamePhase(monsters);

		// Alienoid rolls exactly 3 TWO's
		HashMap<Dice, Integer> rolled = new HashMap<Dice, Integer>();
		rolled.put(new Dice(2), 3);
		rolled.put(new Dice(1), 2);
		rolled.put(new Dice(3), 1);
		alienoid.setRolledDice(rolled);

		int otherPreHealth = otherMon.getHealth();
		int alienoidPreHealth = alienoid.getHealth();

		// Resolving dice phase which should trigger evol card
		gamePhase.setPhase(Phase.RESOLVING, alienoid, null);
		assertEquals(otherPreHealth - 1, otherMon.getHealth());
		assertEquals(alienoidPreHealth, alienoid.getHealth());

		// Less than 3 TWO's
		rolled.clear();
		rolled.put(new Dice(2), 2);
		rolled.put(new Dice(1), 2);
		rolled.put(new Dice(3), 3);
		alienoid.setRolledDice(rolled);

		// Should be same as before since not 3 TWO's
		gamePhase.setPhase(Phase.RESOLVING, alienoid, null);
		assertEquals(otherPreHealth - 1, otherMon.getHealth());
		assertEquals(alienoidPreHealth, alienoid.getHealth());
	}
}