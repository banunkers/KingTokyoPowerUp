package tests.game.evolutioncard;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import card.evolutioncard.EvolCard;
import card.evolutioncard.implemented.gigazaur.DefenderOfTokyo;
import card.evolutioncard.implemented.gigazaur.RadioactiveWaste;
import game.GamePhase;
import game.Phase;
import monster.Monster;
import monster.implemented.Gigazaur;
import monster.implemented.Kong;
import player.Player;

public class GigazaurTests {

	@Test
	public void defenderOfTokyo() {
		Player gigazaurPlayer = new Player(new Gigazaur());
		Player otherPlayer = new Player(new Kong());
		Monster gigazaur = gigazaurPlayer.getMonster();
		Monster otherMon = otherPlayer.getMonster();

		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(gigazaur);
		monsters.add(otherMon);

		ArrayList<EvolCard> evolCards = new ArrayList<EvolCard>();
		evolCards.add(new DefenderOfTokyo(monsters));
		gigazaur.setEvolCards(evolCards);
		gigazaur.activateEvolCard();

		GamePhase gamePhase = new GamePhase(monsters);

		gigazaur.incStars(5);
		otherMon.incStars(5);
		int gigazaurPreStars = gigazaur.getStars();
		int otherPreStars = otherMon.getStars();
		gigazaur.setInTokyo(gamePhase, true);	// Put gigazaur in Tokyo
		gamePhase.setPhase(Phase.START, gigazaur, null);	// Start of turn

		assertEquals(otherPreStars - 1, otherMon.getStars());
		assertEquals(gigazaurPreStars, gigazaur.getStars());

		// Gigazaur not in Tokyo start of turn
		gigazaurPreStars = gigazaur.getStars();
		otherPreStars = otherMon.getStars();

		gigazaur.setInTokyo(gamePhase, false);
		gamePhase.setPhase(Phase.START, gigazaur, null);

		assertEquals(otherPreStars, otherMon.getStars());
		assertEquals(gigazaurPreStars, gigazaur.getStars());
	}

	@Test
	public void radioActiveWaste() {
		Player gigazaurPlayer = new Player(new Gigazaur());
		Player otherPlayer = new Player(new Kong());
		Monster gigazaur = gigazaurPlayer.getMonster();
		Monster otherMon = otherPlayer.getMonster();

		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(gigazaur);
		monsters.add(otherMon);

		ArrayList<EvolCard> evolCards = new ArrayList<EvolCard>();
		evolCards.add(new RadioactiveWaste(monsters));
		gigazaur.setEvolCards(evolCards);
		
		int preEnergy = gigazaur.getEnergy();
		gigazaur.decHealth(5);
		int preHealth = gigazaur.getHealth();

		// Activate and assert
		gigazaur.activateEvolCard();
		assertEquals(preEnergy + 2, gigazaur.getEnergy());
		assertEquals(preHealth + 1, gigazaur.getHealth());
	}
}