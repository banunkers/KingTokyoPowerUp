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
		Player gigazaurPlayer = new Player();
		gigazaurPlayer.setMonster(new Gigazaur());
		Player otherPlayer = new Player();
		otherPlayer.setMonster(new Kong());
		Monster gigazaur = gigazaurPlayer.getMonster();
		Monster otherMon = otherPlayer.getMonster();

		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(gigazaur);
		monsters.add(otherMon);

		ArrayList<EvolCard> evolCards = new ArrayList<EvolCard>();
		evolCards.add(new DefenderOfTokyo());
		gigazaur.setEvolCards(evolCards);
		gigazaur.activateEvolCard(monsters);

		GamePhase gamePhase = new GamePhase();

		gigazaur.incStars(5);
		otherMon.incStars(5);
		int gigazaurPreStars = gigazaur.getStars();
		int otherPreStars = otherMon.getStars();
		gigazaur.setInTokyo(gamePhase, true, monsters);	// Put gigazaur in Tokyo
		gamePhase.setPhase(Phase.START, gigazaur, null, monsters);	// Start of turn

		assertEquals(otherPreStars - 1, otherMon.getStars());
		assertEquals(gigazaurPreStars, gigazaur.getStars());

		// Gigazaur not in Tokyo start of turn
		gigazaurPreStars = gigazaur.getStars();
		otherPreStars = otherMon.getStars();

		gigazaur.setInTokyo(gamePhase, false, monsters);
		gamePhase.setPhase(Phase.START, gigazaur, null, monsters);

		assertEquals(otherPreStars, otherMon.getStars());
		assertEquals(gigazaurPreStars, gigazaur.getStars());
	}

	@Test
	public void radioActiveWaste() {
		Player gigazaurPlayer = new Player();
		gigazaurPlayer.setMonster(new Gigazaur());
		Player otherPlayer = new Player();
		otherPlayer.setMonster(new Kong());
		Monster gigazaur = gigazaurPlayer.getMonster();
		Monster otherMon = otherPlayer.getMonster();

		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(gigazaur);
		monsters.add(otherMon);

		ArrayList<EvolCard> evolCards = new ArrayList<EvolCard>();
		evolCards.add(new RadioactiveWaste());
		gigazaur.setEvolCards(evolCards);
		
		int preEnergy = gigazaur.getEnergy();
		gigazaur.decHealth(5);
		int preHealth = gigazaur.getHealth();

		// Activate and assert
		gigazaur.activateEvolCard(monsters);
		assertEquals(preEnergy + 2, gigazaur.getEnergy());
		assertEquals(preHealth + 1, gigazaur.getHealth());
	}
}